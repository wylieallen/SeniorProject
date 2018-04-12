package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.Projectile.Projectile;
import Model.Ship.ShipParts.ShipWeapon;
import Model.Ship.ShipParts.WeaponType.EnergyWeapon;
import Utility.Rarity;

import static Utility.Config.*;

public class WeaponBuilder extends PartBuilder{

    private ProjectileBuilder projectileBuilder;

    public WeaponBuilder(){
        super();
        projectileBuilder = new ProjectileBuilder();

    }

    public ShipWeapon buildRandomEnergyWeapon(int baseValue, int baseSpeed, int baseDamage,  Rarity rarity){

        int chance = super.generateRandomBetween(1,2);
        Projectile projectile;
        int cooldown;

        switch(chance){
            case 1:
                projectile = buildFastProjectile(baseSpeed, baseDamage, rarity);
                cooldown = 10;
                break;
            case 2:
                projectile = buildStrongProjectile(baseSpeed, baseDamage, rarity);
                cooldown = 20;
                break;
            default:
                projectile = buildNormalProjectile(baseSpeed, baseDamage, rarity);
                cooldown = 15;
                break;
        }

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*CURRENCY_OFFSET), rarity);

        return new EnergyWeapon(currencyValue, projectile, rarity, cooldown);
    }

    public Projectile buildStrongProjectile(int baseSpeed, int baseDamage, Rarity rarity){
        return projectileBuilder.buildRandomProjectile((int) (baseSpeed*.70),baseDamage*2, rarity);
    }

    public Projectile buildFastProjectile(int baseSpeed, int baseDamage, Rarity rarity){
        return projectileBuilder.buildRandomProjectile((int) (baseSpeed*1.5),baseDamage/2, rarity);
    }

    public Projectile buildNormalProjectile(int baseSpeed, int baseDamage, Rarity rarity){
        return projectileBuilder.buildRandomProjectile(baseSpeed, baseDamage, rarity);
    }

}
