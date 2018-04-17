package Model.Ship.ShipParts;

import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

public class ShipEngine extends ShipPart {

    private int maxSpeed;

    public ShipEngine(int currencyValue, int maxSpeed, Rarity rarity){
        super(rarity + " Ship Engine", currencyValue, "Currency Value: " + currencyValue + "\nMax Speed: " + maxSpeed, rarity);
        this.maxSpeed = maxSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonEngineImage();}
}
