package Model.Ship.ShipParts;

import Utility.Rarity;

public class ShipSpecial extends ShipPart{

    private int maxFuel;

    public ShipSpecial(int currencyValue, int maxFuel, Rarity rarity){
        super(currencyValue, rarity);
        this.maxFuel = maxFuel;
        super.setName(rarity + " Ship Special");
    }

    public int getmaxFuel() {
        return maxFuel;
    }

    public void setmaxFuel(int maxFuel) {
        this.maxFuel = maxFuel;
    }
}
