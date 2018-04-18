package Model.Ship.ShipParts.Projectile;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

import static Utility.Config.FRAMERATE;

public class HomingProjectile extends Projectile{

    private Pilot target;

    public HomingProjectile(Pilot pilot, Vector3D trajectory, float speed, int damage){
        super("Homing", speed, damage, trajectory, pilot);
    }

    @Override
    public Projectile cloneProjectile(Pilot pilot, Vector3D trajectory) {

        return new HomingProjectile(pilot, trajectory, super.getSpeed(), super.getDamage());
    }

    public void setTarget(Pilot target){
        this.target = target;
    }

    public Pilot getTarget(){
        return target;
    }

    @Override
    public void move(Point3D currentPosition) {
       BattleZone currentZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();

        //Find nearest target to lock-on
        if (target == null){
            target = currentZone.getNearestHostileTo(getProjectileSource());
        }
        else {
            Point3D targetPosition = currentZone.getPositionOf(target);
            Vector3D direction = new Vector3D(currentPosition, targetPosition);
            direction.makeUnitVector();
            setTrajectory(direction);
        }

    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonHomingWeaponImage();}
}
