package Model.Ship.ShipParts;

import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

public class ShipHull extends ShipPart {

    private int maxHealth;
    private int inventorySize;

    public ShipHull(int currencyValue, Rarity rarity, int maxHealth, int inventorySize){
        super("" + rarity + " Ship Hull",
                currencyValue,
                // todo: why are we storing all of this as a String in the Item? Why don't we just query this data and make the string in the view?
                "Currency Value: " + currencyValue + "\nMax Health: " + maxHealth + "\nInventory Size: " + inventorySize, rarity);
        this.maxHealth = maxHealth;
        this.inventorySize = inventorySize;
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
}

