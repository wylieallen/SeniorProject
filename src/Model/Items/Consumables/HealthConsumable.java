package Model.Items.Consumables;

import Model.Ship.ShipStats;

public class HealthConsumable extends Consumable{
    private int currencyValue;
    private int useValue;

    public HealthConsumable(int cv, int uv) {
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
        int ch = s.getCurrentHealth();
        int mh = s.getMaxHealth();
        int dif = mh - ch;

        if( ch + useValue > mh)
            s.modifyCurrentHealth(dif);
        else
            s.modifyCurrentHealth(useValue);
    }
}
