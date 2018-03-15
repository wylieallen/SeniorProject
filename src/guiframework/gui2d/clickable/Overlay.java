package guiframework.gui2d.clickable;

import guiframework.gui2d.displayable.CompositeDisplayable;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Overlay extends CompositeDisplayable implements Clickable
{
    private Set<Clickable> clickables;

    protected Clickable lastMousedButton = Clickable.NULL;
    private Clickable hoveredButton = Clickable.NULL;

    public Overlay(Point origin)
    {
        super(origin);
        this.clickables = new LinkedHashSet<>();
    }

    public void addClickable(Clickable clickable) { clickables.add(clickable); }
    public void addAllClickables(Collection<? extends Clickable> newClickables) { clickables.addAll(newClickables); }
    public void removeClickable(Clickable clickable) { clickables.remove(clickable); }
    public void removeAllClickables(Collection<? extends Clickable> oldClickables) { clickables.removeAll(oldClickables); }

    public boolean expired() {return false;}

    public boolean pointIsOn(Point point)
    {
        Point origin = getOrigin();
        Point offset = new Point(point.x - origin.x, point.y - origin.y);
        for (Clickable clickable : clickables)
        {
            if (clickable.pointIsOn(offset))
            {
                lastMousedButton = clickable;
                this.enter();
                return true;
            }
        }
        return false;
    }

    public void press()
    {
        lastMousedButton.press();
    }

    public void enter()
    {
        hoveredButton.exit();
        hoveredButton = lastMousedButton;
        hoveredButton.enter();
    }

    public void exit()
    {
        hoveredButton.exit();
    }

    public void release() { lastMousedButton.release(); }

}