package Model.Ship.ShipParts.WeaponType;

import Model.Ship.ShipParts.Projectile.Projectile;
import Model.Ship.ShipParts.ShipWeapon;
import Utility.*;

import java.util.ArrayList;
import java.util.List;

public class EnergyWeapon extends ShipWeapon {

    public EnergyWeapon(int currencyValue, Projectile projectile, Rarity rarity, int cooldown) {
        super(rarity + " " + projectile.getName() + " Energy Weapon", currencyValue, rarity, projectile, cooldown);
        List<String> attributes = new ArrayList<>();
        attributes.add("Currency Value: " + currencyValue);
        attributes.add("Firing Rate: " + cooldown);
        attributes.add("Projectile Damage: " + projectile.getDamage());
        attributes.add("Projectile Speed: " + projectile.getSpeed());
        super.setAttributes(attributes);
    }
}
