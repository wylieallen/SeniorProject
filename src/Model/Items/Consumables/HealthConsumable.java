package Model.Items.Consumables;

import Model.Ship.*;

import java.util.ArrayList;
import java.util.List;

public class HealthConsumable extends Consumable{

    public HealthConsumable(int cv, int uv) {
        super("Health Pack", cv, uv);
        List<String> attributes = new ArrayList<>();
        attributes.add("Currency Value: " + cv);
        attributes.add("Health Value: " + uv);
        super.setAttributes(attributes);
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
