package Model.Ship.ShipParts.WeaponType;

import Model.Ship.ShipParts.Projectile.Projectile;
import Model.Ship.ShipParts.ShipWeapon;
import Utility.*;

public class EnergyWeapon extends ShipWeapon {

    public EnergyWeapon(int currencyValue, Projectile projectile, Rarity rarity, int cooldown) {
        super("" + rarity + " " + projectile.getName() + " Ship Energy Weapon", currencyValue,
                "Currency Value: " + currencyValue + "\nFiring Rate: " + cooldown + "\nDamage: " + projectile.getDamage() + "\nSpeed: " + projectile.getSpeed()
        , rarity, projectile, cooldown);
    }
}
