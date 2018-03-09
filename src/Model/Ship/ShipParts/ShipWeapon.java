package Model.Ship.ShipParts;

import Model.Pilot.Pilot;
import Model.Ship.ShipParts.Projectile.Projectile;
import Utility.*;

public abstract class ShipWeapon extends ShipPart{

    private Projectile projectile;
    private SystemTimer weaponCooldown;


    public ShipWeapon(int currencyValue, Projectile projectile, Rarity rarity){
        super(currencyValue, rarity);
        this.projectile = projectile;
        weaponCooldown = new SystemTimer();
    }

    public long getCooldown(){
        return weaponCooldown.getElapsedTime();
    }

    public void resetCooldown(){
        weaponCooldown.reset();
    }

    public abstract void fireWeapon(Pilot projectileSource);

    public Projectile getProjectile(){
        return projectile;
    }

    public void setProjectileSource(Pilot projectileSource){
        projectile.setProjectileSource(projectileSource);
    }

    public Pilot getProjectileSource(){
        return projectile.getProjectileSource();
    }

}
