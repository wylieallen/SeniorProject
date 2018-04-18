package Model.Items.Consumables;

import Model.Items.Item;
import Model.Ship.*;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

abstract class Consumable extends Item
{
    private int useValue;

    protected Consumable(String name, int value, int useValue)
    {
        super(name, value);
    }

    public int getUseValue() { return useValue; }
    public void setUseValue(int uv) { useValue = uv; }

    public abstract void Use(Ship s);

    public abstract BufferedImage getImage();
}
