package Model.Items.Consumables;

import Model.Items.Item;
import Model.Ship.*;

abstract class Consumable extends Item
{
    private int useValue;

    protected Consumable(String name, int value, String attributes, int useValue)
    {
        super(name, value, attributes);
    }

    public int getUseValue() { return useValue; }
    public void setUseValue(int uv) { useValue = uv; }

    public abstract void Use(Ship s);
}
