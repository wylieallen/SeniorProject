package Model.Ship.ShipBuilder;

import Model.Pilot.Enemy;
import Model.Ship.ShipParts.Projectile.LinearProjectile;
import Model.Ship.ShipParts.Projectile.Projectile;
import Utility.Geom3D.Vector3D;
import Utility.Rarity;

import static Utility.Config.*;


public class ProjectileBuilder extends PartBuilder {
    public ProjectileBuilder(){
        super();
    }


    public Projectile buildRandomProjectile(int baseSpeed, int baseDamage, Rarity rarity){

        //TODO random between linear/homing...
        return buildRandomLinearProjectile(baseSpeed, baseDamage, rarity);
    }

    public LinearProjectile buildRandomLinearProjectile(int baseSpeed, int baseDamage, Rarity rarity){

        int projectileDamage = super.generateRandom(baseDamage, (int) (baseDamage*PROJECTILE_DAMAGE_OFFSET), rarity);
        int projectileSpeed = super.generateRandom(baseSpeed, (int) (baseSpeed*PROJECTILE_SPEED_OFFSET), rarity);

        return new LinearProjectile(null, null, projectileSpeed, projectileDamage);
    }


}
