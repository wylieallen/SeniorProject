package Model.Ship.ShipParts;

import Model.Ship.Ship;
import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ShipEngine extends ShipPart {

    private int maxSpeed;

    public ShipEngine(int currencyValue, int maxSpeed, Rarity rarity){
        super(rarity + " Engine", currencyValue, rarity);
        this.maxSpeed = maxSpeed;
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("Currency Value: " + currencyValue);
        attributes.add("Max Speed: " + maxSpeed);
        super.setAttributes(attributes);
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonEngineImage();}

    @Override
    public void equip(Ship ship) {
        ship.equipEngine(this);
    }
}
