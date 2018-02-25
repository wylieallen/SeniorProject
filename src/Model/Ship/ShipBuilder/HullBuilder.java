package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.ShipHull;
import Utility.Rarity;
import static Utility.Config.*;

public class HullBuilder extends PartBuilder {


    public HullBuilder(){
        super();
    }

    public ShipHull buildHull(int baseValue, int baseHealth, int inventorySize, Rarity rarity){

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*CURRENCY_OFFSET), rarity);
        int maxHealth =  super.generateRandom(baseHealth, (int) (baseHealth*HEALTH_OFFSET), rarity);
        return new ShipHull(currencyValue, maxHealth, inventorySize, rarity);
    }
}
