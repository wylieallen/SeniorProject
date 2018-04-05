package Model.Ship.ShipParts;

import Utility.Rarity;

public class ShipHull extends ShipPart {

    private int maxHealth;
    private int inventorySize;

    public ShipHull(int currencyValue, int maxHealth, int inventorySize, Rarity rarity){
        super(currencyValue, rarity);
        this.maxHealth = maxHealth;
        this.inventorySize = inventorySize;
        super.setName(rarity + " Ship Hull");
        super.setAttributes("Currency Value: " + currencyValue + "\nMax Health: " + maxHealth + "\nInventory Size: " + inventorySize);
    }

    public int getmaxHealth() {
        return maxHealth;
    }

    public int getInventorySize() { return inventorySize; }

    public void setmaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}

