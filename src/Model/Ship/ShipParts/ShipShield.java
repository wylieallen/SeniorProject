package Model.Ship.ShipParts;

import Utility.Rarity;

public class ShipShield extends ShipPart {

    private int maxShield;

    public ShipShield(int currencyValue, int maxShield, Rarity rarity){
        super(currencyValue, rarity);
        this.maxShield = maxShield;
        super.setName(rarity + " Ship Shield");
        super.setAttributes("Currency Value: " + currencyValue + "\nMax Sheild: " + maxShield);
    }

    public int getmaxShield() {
        return maxShield;
    }

    public void setmaxShield(int maxShield) {
        this.maxShield = maxShield;
    }
}
