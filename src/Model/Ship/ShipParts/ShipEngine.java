package Model.Ship.ShipParts;

import Utility.Rarity;

public class ShipEngine extends ShipPart {

    private int maxSpeed;

    public ShipEngine(int currencyValue, int maxSpeed, Rarity rarity){
        super(currencyValue, rarity);
        this.maxSpeed = maxSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
