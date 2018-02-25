package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.ShipShield;
import Utility.*;
import static Utility.Config.*;

public class ShieldBuilder extends PartBuilder {

    public ShieldBuilder(){
        super();
    }

    public ShipShield buildShield(int baseValue, int baseShield, Rarity rarity){

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*CURRENCY_OFFSET), rarity);
        int maxShield =  super.generateRandom(baseShield, (int) (baseShield*SHIELD_OFFSET), rarity);
        return new ShipShield(currencyValue, maxShield, rarity);
    }


}
