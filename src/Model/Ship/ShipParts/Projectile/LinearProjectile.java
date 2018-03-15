package Model.Ship.ShipParts.Projectile;

import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

import static Utility.Config.FRAMERATE;

public class LinearProjectile extends Projectile{

    public LinearProjectile(){
        super.setSpeed(50);
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
    public Point3D move(Point3D curPosition) {

        Vector3D curTrajectory = super.getTrajectory();
        int curSpeed = super.getSpeed();

        float newX = curPosition.getX() + curTrajectory.getI()*(float)(curSpeed/FRAMERATE);
        float newY = curPosition.getY() + curTrajectory.getJ()*(float)(curSpeed/FRAMERATE);
        float newZ = curPosition.getZ() + curTrajectory.getK()*(float)(curSpeed/FRAMERATE);

        Point3D newPosition = new Point3D(newX, newY, newZ);

        return newPosition;
    }
}
