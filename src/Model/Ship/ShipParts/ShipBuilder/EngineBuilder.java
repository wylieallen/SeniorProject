package Model.Ship.ShipParts.ShipBuilder;

import Model.Ship.ShipParts.ShipEngine;
import Utility.Rarity;

public class EngineBuilder extends PartBuilder{

    public EngineBuilder(){
        super();
    }

    public ShipEngine buildEngine(int baseValue, int baseSpeed, Rarity rarity){

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*.5), rarity);
        int maxSpeed =  super.generateRandom(baseSpeed, (int) (baseSpeed*.20), rarity);
        return new ShipEngine(currencyValue, maxSpeed, rarity);
    }

}
