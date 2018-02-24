package guiframework;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFactory
{
    public static BufferedImage makeBorderedRect(int width, int height, Color bodyColor, Color borderColor)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(bodyColor);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(borderColor);
        g2d.drawRect(0, 0, width - 1, height - 1);
        return image;
    }

    public static BufferedImage makeCenterLabeledRect(int width, int height, Color bodyColor, Color borderColor, Color textColor, String text)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        //Added Font line. Can be moved to constructor
        g2d.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.setColor(bodyColor);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(borderColor);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setColor(textColor);
        g2d.drawString(text, (width / 2) - (metrics.stringWidth(text) / 2), (height/2) + (metrics.getHeight()/4));
        return image;
    }

    public static BufferedImage makeLeftLabeledRect(int width, int height, Color bodyColor, Color borderColor, Color textColor, String text)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.setColor(bodyColor);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(borderColor);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setColor(textColor);
        g2d.drawString(text, 2, (height/2) + (metrics.getHeight()/4));
        return image;
    }

}
