package Model.Items.Consumables;

import Model.Ship.*;

public class FuelConsumable extends Consumable{
    private int currencyValue;
    private int useValue;
    private String name = "Fuel Pack";

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

    public void Use(Ship s) {
        ShipStats stats = s.getShipStats();
        int cf = stats.getCurrentFuel();
        int mf = stats.getMaxFuel();
        int dif = mf - cf;

        if( cf + useValue > mf)
            stats.modifyCurrentFuel(dif);
        else
            stats.modifyCurrentFuel(useValue);
    }


    public String getName() {
        return name;
    }
}
