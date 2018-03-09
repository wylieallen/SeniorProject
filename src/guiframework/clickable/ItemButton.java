package guiframework.clickable;

import Model.Items.Item;
import guiframework.util.AbstractFunction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ItemButton extends Button
{
    private Item item;

    public ItemButton(Item item, Point origin, BufferedImage baseImage, BufferedImage hoverImage, BufferedImage pressImage,
                  AbstractFunction pressFunction, AbstractFunction releaseFunction, AbstractFunction enterFunction, AbstractFunction exitFunction)
    {
        super(origin, baseImage, hoverImage, pressImage, pressFunction, releaseFunction, enterFunction, exitFunction);
        this.item = item;
    }

    public ItemButton(Item item, Point origin, BufferedImage baseImage, BufferedImage hoverImage, BufferedImage pressImage,
                  AbstractFunction pressFunction)
    {
        this(item, origin, baseImage, hoverImage, pressImage, pressFunction, AbstractFunction.NULL, AbstractFunction.NULL, AbstractFunction.NULL);
    }

    public Item getItem() {
        return item;
    }
}
