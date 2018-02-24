package Model.Ship.ShipParts.ShipBuilder;

import Model.Ship.ShipParts.ShipShield;
import Utility.Rarity;

public class ShieldBuilder extends PartBuilder {

    public ShieldBuilder(){
        super();
    }

    public ShipShield buildShield(int baseValue, int baseShield, Rarity rarity){

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*.5), rarity);
        int maxShield =  super.generateRandom(baseShield, (int) (baseShield), rarity);
        return new ShipShield(currencyValue, maxShield, rarity);
    }


}
