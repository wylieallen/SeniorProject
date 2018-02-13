package Model.Items.Consumables;

import Model.Ship.ShipStats;

public class ShieldConsumable  extends Consumable{
    private int currencyValue;
    private int useValue;

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

    public void Use(ShipStats s) {
        int cs = s.getCurrentShield();
        int ms = s.getMaxShield();
        int dif = ms - cs;

        if( cs + useValue > ms)
            s.modifyCurrentShield(dif);
        else
            s.modifyCurrentShield(useValue);
    }
}
