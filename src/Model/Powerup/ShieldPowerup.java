package Model.Powerup;

import Model.Ship.*;

public class ShieldPowerup extends Powerup{
    private int useValue;

    public ShieldPowerup(int uv){useValue = uv;}

    public int getUseValue(){return useValue;}

    public void setUseValue(int uv){useValue = uv;}

    public void Use(Ship s){
        ShipStats stats = s.getShipStats();
        int cs = stats.getCurrentShield();
        int ms = stats.getMaxShield();
        int dif = ms - cs;

        if( cs + useValue > ms)
            stats.modifyCurrentShield(dif);
        else
            stats.modifyCurrentShield(useValue);
    }
}
