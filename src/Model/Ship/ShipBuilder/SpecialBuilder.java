package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.ShipSpecial;
import Utility.Rarity;

public class SpecialBuilder {

    public SpecialBuilder(){
        super();
    }

    public ShipSpecial buildRandomSpecial(){
        return new ShipSpecial(0, 0, Rarity.COMMON);
    }


}
