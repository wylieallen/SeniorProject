package Model.Powerup;

import Model.Ship.*;

public class HealthPowerup extends Powerup{
    private int useValue;

    public HealthPowerup(int uv){useValue = uv;}

    public int getUseValue(){return useValue;}

    public void setUseValue(int uv){useValue = uv;}

    public void Use(Ship s){
        ShipStats stats = s.getShipStats();
        int ch = stats.getCurrentHealth();
        int mh = stats.getMaxHealth();
        int dif = mh - ch;

        if( ch + useValue > mh)
            stats.modifyCurrentHealth(dif);
        else
            stats.modifyCurrentHealth(useValue);
    }
}
