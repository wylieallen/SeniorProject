package guiframework.clickable;

import guiframework.displayable.ImageDisplayable;
import guiframework.util.AbstractFunction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends ImageDisplayable implements Clickable
{
    private BufferedImage baseImage, hoverImage, pressImage;

    private AbstractFunction pressFunction, releaseFunction, enterFunction, exitFunction;

    public Button(Point origin, BufferedImage baseImage, BufferedImage hoverImage, BufferedImage pressImage,
                  AbstractFunction pressFunction, AbstractFunction releaseFunction, AbstractFunction enterFunction, AbstractFunction exitFunction)
    {
        super(origin, baseImage);

        System.out.println("Instantiated at " + origin);

        this.baseImage = baseImage;
        this.hoverImage = hoverImage;
        this.pressImage = pressImage;

        this.pressFunction = pressFunction;
        this.releaseFunction = releaseFunction;
        this.enterFunction = enterFunction;
        this.exitFunction = exitFunction;
    }

    public Button(Point origin, BufferedImage baseImage, BufferedImage hoverImage, BufferedImage pressImage,
                  AbstractFunction pressFunction)
    {
        this(origin, baseImage, hoverImage, pressImage, pressFunction, AbstractFunction.NULL, AbstractFunction.NULL, AbstractFunction.NULL);
    }

    public boolean pointIsOn(Point point)
    {
        Point origin = super.getOrigin();
        Dimension dimension = super.getSize();

        return(point.x >= origin.x && point.x <= origin.x + dimension.width && point.y >= origin.y && point.y <= origin.y + dimension.height);
    }

    public void enter()
    {
        super.setImage(hoverImage);
        enterFunction.execute();
    }

    public void exit()
    {
        super.setImage(baseImage);
        exitFunction.execute();
    }

    public void press()
    {
        super.setImage(pressImage);
        pressFunction.execute();
    }

    public void release()
    {
        super.setImage(hoverImage);
        releaseFunction.execute();
    }
}
