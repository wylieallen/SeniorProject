package Model.Items.Consumables;

import Model.Ship.*;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FuelConsumable extends Consumable{

    public FuelConsumable(int value, int uv) {
        super("Fuel Pack", value, uv);
        List<String> attributes = new ArrayList<>();
        attributes.add("Currency Value: " + value);
        attributes.add("Fuel Value: " + uv);
        super.setAttributes(attributes);
    }

    public void Use(Ship s) {
        ShipStats stats = s.getShipStats();
        double cf = stats.getCurrentFuel();
        double mf = stats.getMaxFuel();
        double dif = mf - cf;

        if( cf + getUseValue() > mf)
            stats.modifyCurrentFuel(dif);
        else
            stats.modifyCurrentFuel(getUseValue());
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonFuelPackImage();}

}
