package Model.Ship.ShipBuilder;

import Utility.RandomNumberGenerator;
import Utility.Rarity;
import static Utility.Config.*;

public class PartBuilder {

    private RandomNumberGenerator rng;

    protected PartBuilder(){
        rng = new RandomNumberGenerator();
    }

    public int generateRandom(int baseValue, int range, Rarity rarity){
        return rng.getRarityRandomInRange(baseValue, range, rarity);
    }


}
