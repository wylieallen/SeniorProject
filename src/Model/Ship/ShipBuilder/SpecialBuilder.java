package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.ShipSpecial;
import Model.Ship.ShipParts.SpecialType.BoostSpecial;
import Utility.Rarity;

public class SpecialBuilder extends PartBuilder{

    public SpecialBuilder(){
        super();
    }

    public ShipSpecial buildRandomBoostSpecial(){
        return new BoostSpecial(0, 0, 0, 0, Rarity.COMMON);
    }


}
