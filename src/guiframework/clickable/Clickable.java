package guiframework.clickable;

import java.awt.*;

public interface Clickable
{
    Point getOrigin();
    Dimension getSize();

    boolean pointIsOn(Point point);

    void enter();
    void exit();

    void press();
    void release();
}
