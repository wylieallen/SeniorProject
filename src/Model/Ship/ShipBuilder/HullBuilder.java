package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.ShipHull;
import Utility.Rarity;
import static Utility.Config.*;

public class HullBuilder extends PartBuilder {


    public HullBuilder(){
        super();
    }

    public ShipHull buildRandomHull(int baseValue, int baseHealth, int inventorySize, Rarity rarity){

        int health = baseHealth;
        int invenSize = inventorySize;

        int chance = super.generateRandomBetween(1,2);
        switch(chance){
            //Build fighter hull
            case 1:
                health*=2;
                break;
            //Build freighter hull
            case 2:
                invenSize*=2;
                break;
            default:
                break;
        }

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*CURRENCY_OFFSET), rarity);
        int maxHealth =  super.generateRandom(health, (int) (health*HEALTH_OFFSET), rarity);
        int maxInventory = super.generateRandom(invenSize, (int) (invenSize*INVENTORY_OFFSET), rarity);
        return new ShipHull(currencyValue, rarity, maxHealth, maxInventory);
    }


}
