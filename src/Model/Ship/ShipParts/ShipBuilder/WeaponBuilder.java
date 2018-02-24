package Model.Ship.ShipParts.ShipBuilder;

import Model.Ship.ShipParts.ShipWeapon;
import Utility.Rarity;

public class WeaponBuilder {

    public WeaponBuilder(){
        super();
    }

    public ShipWeapon buildWeapon(){

        return new ShipWeapon(0, 0, Rarity.COMMON);
    }
}
