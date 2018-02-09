package Model.Items.Consumables;

import Model.Items.Item;

abstract class Consumable extends Item{
    private int currencyValue;
    private int useValue;

    public abstract int getCurrencyValue();

    public abstract void setCurrencyValue(int cv);
}
