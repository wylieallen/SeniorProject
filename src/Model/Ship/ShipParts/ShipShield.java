package Model.Ship.ShipParts;

import Utility.Rarity;

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
}
