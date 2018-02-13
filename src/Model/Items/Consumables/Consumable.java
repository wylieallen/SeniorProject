package Model.Items.Consumables;

import Model.Items.Item;
import Model.Ship.*;

abstract class Consumable extends Item{
    private int currencyValue;
    private int useValue;

    public abstract int getCurrencyValue();
    public abstract int getUseValue();

    public abstract void setCurrencyValue(int cv);
    public abstract void setUseValue(int uv);

    public abstract void Use(Ship s);
}
