package Model.Ship.ShipParts.Projectile;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

import static Utility.Config.FRAMERATE;

public class HomingProjectile extends Projectile{

    private Pilot target;

    public HomingProjectile(Pilot pilot, Vector3D trajectory)
    {
        super("Homing", 50, 100, trajectory, pilot);
    }

    @Override
    public Projectile cloneProjectile(Pilot pilot, Vector3D trajectory) {

        return new HomingProjectile(pilot, trajectory);
    }

    public void setTarget(Pilot target){
        this.target = target;
    }

    public Pilot getTarget(){
        return target;
    }

    @Override
    public void move(Point3D curPosition) {
/*        BattleZone currentZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();

        //TODO Find nearest target to lock-on
        if (target == null){

        }
        Point3D targetPosition = currentZone.getPositionOf(target);

        Vector3D curTrajectory = new Vector3D(curPosition, targetPosition);
        curTrajectory.makeUnitVector();
        super.setTrajectory(curTrajectory);

        float curSpeed = super.getSpeed();

        float newX = curPosition.getX() + curTrajectory.getI()*(float)(curSpeed/FRAMERATE);
        float newY = curPosition.getY() + curTrajectory.getJ()*(float)(curSpeed/FRAMERATE);
        float newZ = curPosition.getZ() + curTrajectory.getK()*(float)(curSpeed/FRAMERATE);

        Point3D newPosition = new Point3D(newX, newY, newZ);

        return newPosition;*/
    }
}
