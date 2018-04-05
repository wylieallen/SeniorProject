package Model.Ship.ShipParts.Projectile;

import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

import static Utility.Config.FRAMERATE;

public class LinearProjectile extends Projectile{

    public LinearProjectile(){
        super.setSpeed(3.0f);
        super.setDamage(100);
        super.setName("Linear");
    }

    @Override
    public Projectile cloneProjectile(Pilot pilot, Vector3D trajectory) {

        LinearProjectile clonedProjectile = new LinearProjectile();

        clonedProjectile.setProjectileSource(pilot);
        clonedProjectile.setTrajectory(trajectory);
        return clonedProjectile;
    }

    @Override
    public void move(Point3D curPosition) {
/*
        Vector3D curTrajectory = super.getTrajectory();
        float curSpeed = super.getSpeed();
*/

    }
}
