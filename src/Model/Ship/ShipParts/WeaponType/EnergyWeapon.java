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

    public EnergyWeapon(int currencyValue, Projectile projectile, Rarity rarity) {
        super(currencyValue, projectile, rarity);
    }

    @Override
    public Collection<Projectile> fireWeapon(Pilot projectileSource) {

        Set<Projectile> projectiles = new HashSet<>();
        //TODO STORE vector inside pilot & add that

        if (super.getCooldown() > ENERGY_WEAPON_CD){

            Projectile projectile = super.getProjectile();
            Projectile firedProjectile = projectile.cloneProjectile(projectileSource, new Vector3D(0,1,0));

            firedProjectile.setProjectileSource(projectileSource);
            firedProjectile.setTrajectory(new Vector3D(0,1,0));

            projectiles.add(firedProjectile);

            super.resetCooldown();
        }

        return projectiles;
    }
}
