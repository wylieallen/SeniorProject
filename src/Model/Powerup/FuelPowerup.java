package Model.Powerup;

import Model.Ship.*;

public class FuelPowerup extends Powerup{
    private int useValue;

    public FuelPowerup(int uv){useValue = uv;}

    public int getUseValue(){return useValue;}

    public void setUseValue(int uv){useValue = uv;}

    public void Use(Ship s){
        ShipStats stats = s.getShipStats();
        int cf = stats.getCurrentFuel();
        int mf = stats.getMaxFuel();
        int dif = mf - cf;

        if( cf + useValue > mf)
            stats.modifyCurrentFuel(dif);
        else
            stats.modifyCurrentFuel(useValue);
    }
}
