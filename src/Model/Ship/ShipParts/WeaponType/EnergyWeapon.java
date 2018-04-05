package Model.Ship.ShipParts.WeaponType;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.Ship.ShipParts.ShipWeapon;
import Utility.*;
import Utility.Geom3D.Vector3D;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static Utility.Config.*;

public class EnergyWeapon extends ShipWeapon {

    int weaponCooldown = ENERGY_WEAPON_CD;

    public EnergyWeapon(int currencyValue, Projectile projectile, Rarity rarity) {
        super(currencyValue, projectile, rarity);
        super.setName(rarity + " " + super.getProjectile().getName() + " Ship Energy Weapon");
        super.setAttributes("Currency Value: " + currencyValue + "\nFiring Rate: " + weaponCooldown + "\nDamage: " + super.getProjectile().getDamage() + "\nSpeed: " + super.getProjectile().getSpeed());
    }

    @Override
    public Collection<Projectile> fireWeapon(Pilot projectileSource) {

        Set<Projectile> projectiles = new HashSet<>();

        if (super.getCooldown() > weaponCooldown){


            super.resetCooldown();
        }

        Projectile projectile = super.getProjectile();
        Projectile firedProjectile = projectile.cloneProjectile(projectileSource, projectileSource.getShipDirection());

        projectiles.add(firedProjectile);
        return projectiles;
    }
}
