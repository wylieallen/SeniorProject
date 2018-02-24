package guiframework.displayable;

import guiframework.util.TypedAbstractFunction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StringDisplayable implements Displayable
{
    private Point origin;
    private TypedAbstractFunction<String> stringGenerator;
    private Color color;
    private Font font;
    private FontMetrics fontMetrics;

    public StringDisplayable(Point origin, TypedAbstractFunction<String> stringGenerator)
    {
        this(origin, stringGenerator, Color.BLACK, new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics().getFont());
    }

    public StringDisplayable(Point origin, TypedAbstractFunction<String> stringGenerator, Color color, Font font)
    {
        this.origin = origin;
        this.stringGenerator = stringGenerator;
        this.color = color;
        this.font = font;
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(font);
        this.fontMetrics = g2d.getFontMetrics();
    }

    public Point getOrigin() { return origin; }
    public Dimension getSize()
    {
        return new Dimension(fontMetrics.stringWidth(stringGenerator.execute()), fontMetrics.getHeight());
    }

    public void drawAt(Graphics2D g2d, Point drawPt)
    {
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(stringGenerator.execute(), drawPt.x, drawPt.y + fontMetrics.getHeight());
    }

}
