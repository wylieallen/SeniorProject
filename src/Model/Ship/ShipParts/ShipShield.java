package Model.Ship.ShipParts;

import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

public class ShipShield extends ShipPart {

    private int maxShield;

    public ShipShield(int currencyValue, int maxShield, Rarity rarity){
        super(rarity + " Ship Shield", currencyValue, "Currency Value: " + currencyValue + "\nMax Sheild: " + maxShield, rarity);
        this.maxShield = maxShield;
    }

    public int getmaxShield() {
        return maxShield;
    }

    public void setmaxShield(int maxShield) {
        this.maxShield = maxShield;
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonShieldImage();}
}
