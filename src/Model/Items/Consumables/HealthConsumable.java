package Model.Items.Consumables;

import Model.Ship.*;

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

    public void Use(Ship s) {
        ShipStats stats = s.getShipStats();
        int ch = stats.getCurrentHealth();
        int mh = stats.getMaxHealth();
        int dif = mh - ch;

        if( ch + useValue > mh)
            stats.modifyCurrentHealth(dif);
        else
            stats.modifyCurrentHealth(useValue);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
