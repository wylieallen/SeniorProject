package guiframework.control;

import guiframework.control.clickable.Clickable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class ClickableControlstate extends Controlstate
{
    private Set<Clickable> clickables;
    private Clickable selectedClickable = Clickable.NULL;

    public ClickableControlstate()
    {
        super();
        clickables = new HashSet<>();
        super.bindMousePress(MouseEvent.BUTTON1, this::press);
        super.bindMouseRelease(MouseEvent.BUTTON1, this::release);
    }

    public void add(Clickable c) { clickables.add(c); }
    public void remove(Clickable c) { clickables.remove(c); }

    private void press()
    {
        Point mousePt = getMousePt();

        if(selectedClickable.pointIsOn(mousePt))
        {
            selectedClickable.press();
            return;
        }

        for(Clickable clickable : clickables)
        {
            if(clickable.pointIsOn(mousePt))
            {
                selectedClickable.exit();
                selectedClickable = clickable;
                selectedClickable.enter();
                selectedClickable.press();
                return;
            }
        }
    }

    private void release() { selectedClickable.release(); }

    @Override
    public void parseMouseMove(Point point)
    {
        super.parseMouseMove(point);
        Point mousePt = super.getMousePt();

        if(selectedClickable.pointIsOn(mousePt))
        {
            return;
        }
        else
        {
            selectedClickable.exit();
        }

        for(Clickable clickable : clickables)
        {
            if(clickable.pointIsOn(mousePt))
            {
                selectedClickable = clickable;
                selectedClickable.enter();
                return;
            }
        }

        selectedClickable = Clickable.NULL;
    }
}
