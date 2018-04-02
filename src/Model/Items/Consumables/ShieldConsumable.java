package Model.Items.Consumables;

import Model.Ship.*;

public class ShieldConsumable extends Consumable{
    private int currencyValue;
    private int useValue;
    private String name = "Shield Pack";

    public ShieldConsumable(int cv, int uv) {
        currencyValue = cv;
        useValue = uv;
    }

    public int getCurrencyValue() {
        return currencyValue;
    }

    public int getUseValue() {
        return useValue;
    }

    public void setCurrencyValue(int cv) {
        currencyValue = cv;
    }

    public void setUseValue(int uv) {
        useValue = uv;
    }

    public void Use(Ship s) {
        ShipStats stats = s.getShipStats();
        int cs = stats.getCurrentShield();
        int ms = stats.getMaxShield();
        int dif = ms - cs;

        if( cs + useValue > ms)
            stats.modifyCurrentShield(dif);
        else
            stats.modifyCurrentShield(useValue);
    }

    public String getName() {
        return name;
    }
}
