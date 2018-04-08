package Model.Items.Consumables;

import Model.Ship.*;

public class FuelConsumable extends Consumable{

    public FuelConsumable(int value, int uv) {
        super("Fuel Pack", value, "Currency Value: " + value + "\nFuel Value: " + uv, uv);
    }

    public void Use(Ship s) {
        ShipStats stats = s.getShipStats();
        int cf = stats.getCurrentFuel();
        int mf = stats.getMaxFuel();
        int dif = mf - cf;

        if( cf + getUseValue() > mf)
            stats.modifyCurrentFuel(dif);
        else
            stats.modifyCurrentFuel(getUseValue());
    }

}
