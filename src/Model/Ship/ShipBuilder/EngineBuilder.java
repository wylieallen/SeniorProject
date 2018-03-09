package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.ShipEngine;
import Utility.Rarity;
import static Utility.Config.*;

public class EngineBuilder extends PartBuilder{

    public EngineBuilder(){
        super();
    }

    public ShipEngine buildRandomEngine(int baseValue, int baseSpeed, Rarity rarity){

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*CURRENCY_OFFSET), rarity);
        int maxSpeed =  super.generateRandom(baseSpeed, (int) (baseSpeed*SPEED_OFFSET), rarity);
        return new ShipEngine(currencyValue, maxSpeed, rarity);
    }

}
