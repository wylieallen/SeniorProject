package Model.Ship.ShipParts;

import Model.Pilot.Player;
import Model.Ship.Ship;
import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ShipHull extends ShipPart {

    private int maxHealth;
    private int inventorySize;

    public ShipHull(int currencyValue, Rarity rarity, int maxHealth, int inventorySize){
        super("" + rarity + " Hull", currencyValue, rarity);
        this.maxHealth = maxHealth;
        this.inventorySize = inventorySize;
        List<String> attributes = new ArrayList<>();
        attributes.add("Currency Value: " + currencyValue);
        attributes.add("Max Health: " + maxHealth);
        attributes.add("Inventory Size: " + inventorySize);
        super.setAttributes(attributes);
    }

    public BufferedImage getShipImage() {
        switch (getRarity()) {
            case COMMON:
                return ImageFactory.getCommonShipImageBlackLarge();
            case RARE:
                return ImageFactory.getRareShipImageBlackLarge();
            case EPIC:
                return ImageFactory.getEpicShipImageBlackLarge();
            case LEGENDARY:
                return ImageFactory.getLegendaryShipImageBlackLarge();
            default:
                return ImageFactory.getCommonShipImageBlackLarge();
        }
    }

    public int getmaxHealth() {
        return maxHealth;
    }

    public int getInventorySize() { return inventorySize; }

    public void setmaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public void equip(Ship ship) {
        Player owner = (Player) ship.getMyPilot();
        owner.getShipHangar().addShip(new Ship(owner, this));
    }
}

