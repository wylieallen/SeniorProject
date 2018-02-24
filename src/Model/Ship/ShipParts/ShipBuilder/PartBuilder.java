package Model.Ship.ShipParts.ShipBuilder;

import Utility.RandomNumberGenerator;
import Utility.Rarity;

public class PartBuilder {

    private RandomNumberGenerator rng;

    protected PartBuilder(){
        rng = new RandomNumberGenerator();
    }

    public int generateRandom(int baseValue, int range, Rarity rarity){
        return rng.getRarityRandomInRange(baseValue, range, rarity);
    }


}
