package guiframework.displayable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplayable implements Displayable
{
    private BufferedImage image;
    private Point origin;
    private Dimension size;

    public ImageDisplayable(Point origin, BufferedImage image)
    {
        this.origin = origin;
        this.image = image;
        this.size = new Dimension(image.getWidth(), image.getHeight());
    }

    public Point getOrigin() { return origin; }
    public Dimension getSize() { return size; }

    public BufferedImage getImage() { return image; }
    public void setImage(BufferedImage image)
    {
        this.image = image;
        this.size.setSize(image.getWidth(), image.getHeight());
    }

    public void drawAt(Graphics2D g2d, Point drawPt)
    {
        g2d.drawImage(image, drawPt.x, drawPt.y, null);
    }
}
