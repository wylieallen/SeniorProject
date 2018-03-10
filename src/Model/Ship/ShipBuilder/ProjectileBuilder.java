package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.Projectile.LinearProjectile;
import Utility.Rarity;

import static Utility.Config.*;


public class ProjectileBuilder extends PartBuilder {
    public ProjectileBuilder(){
        super();
    }

    public LinearProjectile buildRandomLinearProjectile(int baseDamage, int baseSpeed, Rarity rarity){

        int projectileDamage = super.generateRandom(baseDamage, (int) (baseDamage*PROJECTILE_DAMAGE_OFFSET), rarity);
        int projectileSpeed = super.generateRandom(baseSpeed, (int) (baseSpeed*PROJECTILE_SPEED_OFFSET), rarity);

        return new LinearProjectile();
    }
}