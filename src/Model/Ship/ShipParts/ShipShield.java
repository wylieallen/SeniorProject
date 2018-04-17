package Model.Ship.ShipParts;

import Model.Ship.Ship;
import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ShipShield extends ShipPart {

    private int maxShield;

    public ShipShield(int currencyValue, int maxShield, Rarity rarity){
        super(rarity + " Shield", currencyValue, rarity);
        this.maxShield = maxShield;
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("Currency Value: " + currencyValue);
        attributes.add("Max Shield: " + maxShield);
        super.setAttributes(attributes);
    }

    public int getmaxShield() {
        return maxShield;
    }

    public void setmaxShield(int maxShield) {
        this.maxShield = maxShield;
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonShieldImage();}

    @Override
    public void equip(Ship ship) {
        ship.equipShield(this);
    }
}
