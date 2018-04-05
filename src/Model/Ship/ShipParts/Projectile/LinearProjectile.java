package Model.Ship.ShipParts.Projectile;

import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

import static Utility.Config.FRAMERATE;

public class LinearProjectile extends Projectile{

    public LinearProjectile(Pilot pilot, Vector3D trajectory){
        super("Linear", 2.0f, 100, trajectory, pilot);
    }

    @Override
    public Projectile cloneProjectile(Pilot pilot, Vector3D trajectory) {

        return new LinearProjectile(pilot, trajectory);
    }

    @Override
    public void move(Point3D curPosition) {
/*
        Vector3D curTrajectory = super.getTrajectory();
        float curSpeed = super.getSpeed();
*/

    }
}
