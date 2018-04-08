package Model.Items.Consumables;

import Model.Ship.*;

public class HealthConsumable extends Consumable{

    public HealthConsumable(int cv, int uv) {
        super("Health Pack", cv, "Currency Value: " + cv + "\nHealth Value: " + uv, uv);
    }

    public void Use(Ship s) {
        ShipStats stats = s.getShipStats();
        int ch = stats.getCurrentHealth();
        int mh = stats.getMaxHealth();
        int dif = mh - ch;

        if( ch + getUseValue() > mh)
            stats.modifyCurrentHealth(dif);
        else
            stats.modifyCurrentHealth(getUseValue());
    }
}
