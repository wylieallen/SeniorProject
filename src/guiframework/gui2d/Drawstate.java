package guiframework.gui2d;

import guiframework.gui2d.clickable.Clickable;
import guiframework.gui2d.displayable.Displayable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class Drawstate
{
    private Set<Displayable> underlays;
    private Set<Displayable> overlays;
    private Set<Collection<? extends Displayable>> displayables;

    private Set<Clickable> clickables;
    private Clickable selectedClickable = Clickable.NULL;

    private OverlayManager overlayManager;
    private UnderlayManager underlayManager;

    private Dimension size = new Dimension(1280, 720);

    public Drawstate()
    {
        underlays = new LinkedHashSet<>();
        displayables = new LinkedHashSet<>();
        overlays = new LinkedHashSet<>();
        clickables = new LinkedHashSet<>();
        overlayManager = new OverlayManager();
        underlayManager = new UnderlayManager();
    }

    public void draw(Graphics2D g2d)
    {
        underlays.forEach((d) -> d.draw(g2d));

        displayables.forEach((collection) -> collection.forEach((d) -> d.draw(g2d)));

        overlays.forEach((d) -> d.draw(g2d));
    }

    public void update()
    {
        underlays.forEach(Displayable::update);
        displayables.forEach((collection) -> collection.forEach(Displayable::update));
        overlays.forEach(Displayable::update);
    }

    public void addClickable(Clickable clickable) { clickables.add(clickable); }
    public void removeClickable(Clickable clickable) { clickables.remove(clickable); }

    public void addLeftOverlay(Displayable overlay)
    {
        overlayManager.addLeft(overlay);
        overlays.add(overlay);
        changeSize(this.size);
    }

    public void addRightOverlay(Displayable overlay)
    {
        overlayManager.addRight(overlay);
        overlays.add(overlay);
        changeSize(this.size);
    }

    public void removeRightOverlay(Displayable overlay)
    {
        overlayManager.removeRight(overlay);
        overlays.remove(overlay);
        changeSize(this.size);
    }

    public void removeAllRightOverlays() {
        overlayManager.removeAllRightOverlays();
        changeSize(this.size);
    }


    public void addCenterOverlay(Displayable overlay)
    {
        overlayManager.addCenter(overlay);
        overlays.add(overlay);
        changeSize(this.size);
    }

    public void removeOverlay(Displayable overlay)
    {
        overlays.remove(overlay);
    }

    public void addUnderlay(Displayable underlay)
    {
        underlays.add(underlay);
        //todo: UnderlayManager is hacked together and jank! It's only really meant for one Underlay, use with caution!
        underlayManager.addCenterUnderlay(underlay);
    }
    public void removeUnderlay(Displayable underlay) { underlays.remove(underlay); }

    public void addDisplayables(Collection<? extends Displayable> displayables) {
        this.displayables.add(displayables);
    }
    public void removeDisplayables(Collection<? extends Displayable> displayables) { this.displayables.remove(displayables); }

    public void parseKeyPress(int keyCode) { }
    public void parseKeyRelease(int keyCode) { }

    public void parseMousePress(MouseEvent e)
    {
        if(selectedClickable.pointIsOn(e.getPoint()))
        {
            selectedClickable.press();
            return;
        }

        for(Clickable clickable : clickables)
        {
            if(clickable.pointIsOn(e.getPoint()))
            {
                selectedClickable.exit();
                selectedClickable = clickable;
                selectedClickable.enter();
                selectedClickable.press();
                return;
            }
        }
    }

    public void parseMouseDrag(MouseEvent e)
    {
        parseMousePress(e);
    }

    public void parseMouseRelease(MouseEvent e)
    {
        selectedClickable.release();
    }

    public void parseMouseMove(MouseEvent e)
    {
        if(selectedClickable.pointIsOn(e.getPoint()))
        {
           return;
        }
        else
        {
            selectedClickable.exit();
        }

        for(Clickable clickable : clickables)
        {
            if(clickable.pointIsOn(e.getPoint()))
            {
                selectedClickable = clickable;
                selectedClickable.enter();
                return;
            }
        }

        selectedClickable = Clickable.NULL;
    }

    public void changeSize(Dimension size)
    {
        this.size.setSize(size);
        overlayManager.resetOverlays(size);
        underlayManager.resetUnderlays(size);
    }

    public class OverlayManager
    {
        private List<Displayable> leftOverlays;
        private List<Displayable> centerOverlays;
        private List<Displayable> rightOverlays;
        private Iterator<Displayable> rightIterator;

        private int edgeBuffer = 12;

        public OverlayManager()
        {
            leftOverlays = new ArrayList<>();
            centerOverlays = new ArrayList<>();
            rightOverlays = new ArrayList<>();
            rightIterator = rightOverlays.iterator();
        }

        public void addRight(Displayable displayable)
        {
            rightOverlays.add(displayable);
        }
        public void removeRight(Displayable displayable) { rightOverlays.remove(displayable);}

        public void addCenter(Displayable displayable)
        {
            centerOverlays.add(displayable);
        }

        public void addLeft(Displayable displayable)
        {
            leftOverlays.add(displayable);
        }

        public void resetOverlays(Dimension size)
        {
            int centerX;

            centerX = size.width - edgeBuffer - (getMaxX(rightOverlays) / 2);
            updateLocations(centerX, rightOverlays, size);

            centerX = (size.width / 2);
            updateLocations(centerX, centerOverlays, size);

            centerX = edgeBuffer + (getMaxX(leftOverlays) / 2);
            updateLocations(centerX, leftOverlays, size);
        }

        private int getMaxX(Collection<Displayable> overlays)
        {
            int maxWidth = 0;

            for(Displayable overlay : overlays)
            {
                int overlayWidth = overlay.getSize().width;
                if(overlayWidth > maxWidth)
                {
                    maxWidth = overlayWidth;
                }
            }

            return maxWidth;
        }

        private void updateLocations(int centerX, Collection<Displayable> overlays, Dimension size)
        {
            double y = edgeBuffer;

            int maxHeight = size.height;
            int usedSpace = edgeBuffer * 2;
            for(Displayable overlay : overlays)
            {
                usedSpace += overlay.getSize().height;
            }
            int freeSpace = maxHeight - usedSpace;
            double elementBuffer =  overlays.size() > 1 ? freeSpace / (overlays.size() - 1) : freeSpace;

            //System.out.println("maxHeight " + maxHeight + ", usedSpace = " + usedSpace + ", bufferSize = " + elementBuffer);

            for(Displayable overlay : overlays)
            {
                Dimension overlaySize = overlay.getSize();

                //System.out.println("Next overlay: " + overlay.getClass() + " at " + (centerX - (size.width / 2)) + ", " + y);

                overlay.getOrigin().setLocation(centerX - (overlaySize.width / 2), y);

                y += elementBuffer + overlaySize.height; //componentBuffer + size.height;
            }
        }

        public void removeAllRightOverlays()
        {
            synchronized (overlays) {
                overlays.removeAll(rightOverlays);
                rightOverlays.clear();
            }
        }
    }

    public class UnderlayManager
    {
        private List<Displayable> centerUnderlays;

        public UnderlayManager() { centerUnderlays = new ArrayList<>(); }

        public void addCenterUnderlay(Displayable underlay)
        {
            centerUnderlays.add(underlay);
        }

        public void resetUnderlays(Dimension size)
        {
            int centerX = (size.width / 2);
            updateLocations(centerX, centerUnderlays, size);
        }

        public void updateLocations(int centerX, List<Displayable> underlayList, Dimension size)
        {
            double y = 0;

            int maxHeight = size.height;
            int usedSpace = 0 * 2;
            for(Displayable overlay : underlayList)
            {
                usedSpace += overlay.getSize().height;
            }
            int freeSpace = maxHeight - usedSpace;
            double elementBuffer =  underlayList.size() > 1 ? freeSpace / (underlayList.size() - 1) : freeSpace;

            //System.out.println("maxHeight " + maxHeight + ", usedSpace = " + usedSpace + ", bufferSize = " + elementBuffer);

            for(Displayable overlay : underlayList)
            {
                Dimension overlaySize = overlay.getSize();

                //System.out.println("Next overlay: " + overlay.getClass() + " at " + (centerX - (size.width / 2)) + ", " + y);

                overlay.getOrigin().setLocation(centerX - (overlaySize.width / 2), y);

                y += elementBuffer + overlaySize.height; //componentBuffer + size.height;
            }
        }
    }
}
