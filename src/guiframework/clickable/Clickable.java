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

    Clickable NULL = new Clickable()
    {
        public Point getOrigin() { return new Point(); }
        public Dimension getSize() { return new Dimension(); }
        public boolean pointIsOn(Point point) { return false; }
        public void enter() {}
        public void exit() {}
        public void press() {}
        public void release() {}
    };
}
