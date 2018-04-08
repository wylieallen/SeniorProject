package Model.Ship.ShipParts;

import Utility.Rarity;

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

    public int getmaxHealth() {
        return maxHealth;
    }

    public int getInventorySize() { return inventorySize; }

    public void setmaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}

