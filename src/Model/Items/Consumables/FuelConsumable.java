package Model.Items.Consumables;

import Model.Ship.ShipStats;

public class FuelConsumable extends Consumable{
    private int currencyValue;
    private int useValue;

    public FuelConsumable(int cv, int uv) {
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
        int cf = s.getCurrentFuel();
        int mf = s.getMaxFuel();
        int dif = mf - cf;

        if( cf + useValue > mf)
            s.modifyCurrentFuel(dif);
        else
            s.modifyCurrentFuel(useValue);
    }
}
