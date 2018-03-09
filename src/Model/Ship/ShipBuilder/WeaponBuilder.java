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

    public ShipWeapon buildRandomEnergyWeapon(int baseValue, int baseDamage, int baseSpeed, Rarity rarity){

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue*CURRENCY_OFFSET), rarity);
        Projectile projectile = projectileBuilder.buildRandomLinearProjectile(10,10, rarity);

        return new EnergyWeapon(currencyValue, projectile, rarity);
    }
}
