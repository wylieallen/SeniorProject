package Model.Items.Consumables;

import Model.Ship.*;

import java.util.ArrayList;
import java.util.List;

public class ShieldConsumable extends Consumable{

    public ShieldConsumable(int cv, int uv) {
        super("Shield Pack", cv, uv);
        List<String> attributes = new ArrayList<>();
        attributes.add("Currency Value: " + cv);
        attributes.add("Shield Value: " + uv);
        super.setAttributes(attributes);
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
