package guiframework;

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

    public Uberstate()
    {
        underlays = new LinkedHashSet<>();
        displayables = new LinkedHashSet<>();
        overlays = new LinkedHashSet<>();
    }

    public void draw(Graphics2D g2d)
    {
        underlays.forEach((d) -> d.draw(g2d));

        displayables.forEach((collection) -> collection.forEach((d) -> d.draw(g2d)));

        overlays.forEach((d) -> d.draw(g2d));
    }

    public void addOverlay(Displayable overlay) { overlays.add(overlay); }
    public void removeOverlay(Displayable overlay) { overlays.remove(overlay); }

    public void addUnderlay(Displayable underlay) { underlays.add(underlay); }
    public void removeUnderlay(Displayable underlay) { underlays.remove(underlay); }

    public void addDisplayables(Collection<? extends Displayable> displayables) {
        this.displayables.add(displayables);
    }

    public void removeDisplayables(Collection<? extends Displayable> displayables) {
        this.displayables.remove(displayables);
    }

    public void parseKeyPress(int keyCode) { }
    public void parseKeyRelease(int keyCode) { }

    public void parseMousePress(MouseEvent e)
    {

    }

    public void parseMouseDrag(MouseEvent e)
    {

    }

    public void parseMouseRelease(MouseEvent e)
    {

    }

    public void parseMouseMove(MouseEvent e)
    {

    }
}
