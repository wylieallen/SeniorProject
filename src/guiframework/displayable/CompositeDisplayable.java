package guiframework.displayable;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class CompositeDisplayable implements Displayable
{
    private Set<Displayable> displayables;
    private Dimension size;
    private Point origin;

    public CompositeDisplayable(Point origin)
    {
        this.origin = origin;
        this.displayables = new LinkedHashSet<>();
        this.size = new Dimension(0, 0);
    }

    public CompositeDisplayable(Point origin, Displayable... displayables)
    {
        this(origin);
        this.displayables.addAll(Arrays.asList(displayables));
        recalculateSize();
    }

    public void add(Displayable displayable)
    {
        displayables.add(displayable);
        recalculateSize();
    }

    private void recalculateSize()
    {
        // Todo: this should look for minX and minY too to be accurate for components with negative origins etc
        // (but it works right now as long as you make sure to keep components positive)
        int maxX = 0, maxY = 0;

        for(Displayable component : displayables)
        {
            Point buttonPt = component.getOrigin();
            Dimension buttonSize = component.getSize();
            int pointEndX = buttonPt.x + buttonSize.width;
            if(maxX < pointEndX) maxX = pointEndX;
            int pointEndY = buttonPt.y + buttonSize.height;
            if(maxY < pointEndY) maxY = pointEndY;
        }

        this.size.setSize(maxX, maxY);
    }

    public void remove(Displayable displayable)
    {
        displayables.remove(displayable);
        recalculateSize();
    }

    public void clear()
    {
        displayables.clear();
        recalculateSize();
    }

    // Displayable interface:

    public Point getOrigin() {return origin;}
    public Dimension getSize() {return size;}

    public void drawAt(Graphics2D g2d, Point drawPt)
    {
        //Point offset = new Point(drawPt.x + origin.x, drawPt.y + origin.y);
        for(Displayable displayable : displayables)
        {
            displayable.drawWithOffset(g2d, drawPt);
        }
    }

    @Override
    public void update() { displayables.forEach((Displayable::update)); }
}
