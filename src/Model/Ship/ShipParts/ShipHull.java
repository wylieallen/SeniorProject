package Model.Ship.ShipParts;

public class ShipHull extends ShipPart {

    private int maxHealth;

    public ShipHull(int currencyValue, int maxHealth){
        this.maxHealth = maxHealth;
        this.setCurrencyValue(currencyValue);
    }

    public int getmaxHealth() {
        return maxHealth;
    }

    public void setmaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}

