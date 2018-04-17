package Model.Ship.ShipParts;

import Model.Pilot.Pilot;
import Model.Ship.ShipParts.Projectile.LinearProjectile;
import Model.Ship.ShipParts.Projectile.Projectile;
import Utility.*;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ShipWeapon extends ShipPart{

    private Projectile projectile;
    private int baseCooldown, currentCooldown;


    public ShipWeapon(String name, int value, String attributes, Rarity rarity, Projectile projectile, int cooldown)
    {
        super(name, value, attributes, rarity);
        this.projectile = projectile;
        this.baseCooldown = cooldown;
        currentCooldown = 0;
    }

    public Collection<Projectile> fireWeapon(Pilot projectileSource)
    {
        Set<Projectile> projectiles = new HashSet<>();

        if (currentCooldown <= 0){

            Projectile firedProjectile = projectile.cloneProjectile(projectileSource, projectileSource.getShipDirection());
            projectiles.add(firedProjectile);
            currentCooldown = baseCooldown;
        }

        return projectiles;
    }

    public void update() { if(currentCooldown > 0) --currentCooldown; }

    public Projectile getProjectile(){
        return projectile;
    }

    public float getProjectileSpeed() { return projectile.getSpeed(); }

    public void setProjectileSource(Pilot projectileSource){
        projectile.setProjectileSource(projectileSource);
    }

    public Pilot getProjectileSource(){
        return projectile.getProjectileSource();
    }

    //public static final ShipWeapon NULL = new ShipWeapon("EMPTY", 0, "", Rarity.COMMON, Projectile.NULL, -1);

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return projectile.getImage();}
}
