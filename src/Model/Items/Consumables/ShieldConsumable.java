package Model.Items.Consumables;

import Model.Ship.*;

public class ShieldConsumable extends Consumable{

    public ShieldConsumable(int cv, int uv) {
        super("Shield Pack", cv, "Currency Value: " + cv + "\nShield Value: " + uv, uv);
    }


    public void Use(Ship s) {
        ShipStats stats = s.getShipStats();
        int cs = stats.getCurrentShield();
        int ms = stats.getMaxShield();
        int dif = ms - cs;

        if( cs + getUseValue() > ms)
            stats.modifyCurrentShield(dif);
        else
            stats.modifyCurrentShield(getUseValue());
    }
}
