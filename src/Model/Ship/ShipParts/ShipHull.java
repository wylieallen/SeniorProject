package Model.Ship.ShipParts;

public class ShipHull extends ShipPart {

    private int maxHealth;
    private int inventorySize;

    public ShipHull(int currencyValue, int maxHealth, int inventorySize){
        this.maxHealth = maxHealth;
        this.setCurrencyValue(currencyValue);
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

