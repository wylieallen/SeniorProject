package Model.Ship.ShipParts.WeaponType;

import Model.Pilot.Pilot;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.Ship.ShipParts.ShipWeapon;
import Utility.Rarity;

public class AmmoWeapon extends ShipWeapon {


    public AmmoWeapon(int currencyValue, Projectile projectile, Rarity rarity) {
        super(currencyValue, projectile, rarity);
    }

    @Override
    public void fireWeapon(Pilot projectileSource) {

    }
}
