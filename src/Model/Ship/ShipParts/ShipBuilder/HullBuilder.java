package Model.Ship.ShipParts.ShipBuilder;

import Model.Ship.ShipParts.ShipHull;
import Utility.Rarity;

public class HullBuilder extends PartBuilder {


    public HullBuilder(){
        super();
    }

    public ShipHull buildHull(int baseValue, int baseHealth, int inventorySize, Rarity rarity){

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*.5), rarity);
        int maxHealth =  super.generateRandom(baseHealth, (int) (baseHealth*.50), rarity);
        return new ShipHull(currencyValue, maxHealth, inventorySize, rarity);
    }
}
