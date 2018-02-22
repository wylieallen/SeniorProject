package guiframework;

import guiframework.clickable.Clickable;
import guiframework.displayable.Displayable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Uberstate
{
    private Set<Displayable> underlays;
    private Set<Displayable> overlays;
    private Set<Collection<? extends Displayable>> displayables;

    private Set<Clickable> clickables;
    private Clickable selectedClickable = Clickable.NULL;

    public Uberstate()
    {
        underlays = new LinkedHashSet<>();
        displayables = new LinkedHashSet<>();
        overlays = new LinkedHashSet<>();
        clickables = new LinkedHashSet<>();
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

    public void addOverlay(Displayable overlay) { overlays.add(overlay); }
    public void removeOverlay(Displayable overlay) { overlays.remove(overlay); }

    public void addUnderlay(Displayable underlay) { underlays.add(underlay); }
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
}
