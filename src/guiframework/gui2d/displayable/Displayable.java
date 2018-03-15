package guiframework.gui2d.displayable;

import java.awt.*;

public interface Displayable
{
    Point getOrigin();
    Dimension getSize();

    default void update() {}
    default boolean expired() { return false; }

    default void draw(Graphics2D g2d)
    {
        this.drawAt(g2d, this.getOrigin());
    }

    default void drawWithOffset(Graphics2D g2d, Point offset)
    {
        Point origin = getOrigin();
        this.drawAt(g2d, new Point(origin.x + offset.x, origin.y + offset.y));
    }

    void drawAt(Graphics2D g2d, Point drawPt);

    Displayable NULL = new Displayable()
    {
      public Point getOrigin() { return new Point(); }
      public Dimension getSize() { return new Dimension(0, 0); }
      public void drawAt(Graphics2D g2d, Point drawPt) {}
    };
}
