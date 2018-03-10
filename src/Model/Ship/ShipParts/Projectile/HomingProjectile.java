package Model.Ship.ShipParts.Projectile;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Pilot.Pilot;
import Utility.Point3D;
import Utility.Vector3D;

import static Utility.Config.FRAMERATE;

public class HomingProjectile extends Projectile{

    private Pilot target;

    public HomingProjectile(){
        super.setSpeed(50);
        super.setDamage(100);
    }

    @Override
    public Projectile cloneProjectile(Pilot pilot, Vector3D trajectory) {

        LinearProjectile clonedProjectile = new LinearProjectile();

        clonedProjectile.setProjectileSource(pilot);
        clonedProjectile.setTrajectory(trajectory);
        return clonedProjectile;
    }

    public void setTarget(Pilot target){
        this.target = target;
    }

    public Pilot getTarget(){
        return target;
    }

    @Override
    public Point3D move(Point3D curPosition) {
        BattleZone currentZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();

        //TODO Find nearest target to lock-on
        if (target == null){

        }
        Point3D targetPosition = currentZone.getPositionOf(target);

        float newI = targetPosition.getX() - curPosition.getX();
        float newJ = targetPosition.getY() - curPosition.getY();
        float newK = targetPosition.getZ() - curPosition.getZ();

        Vector3D curTrajectory = new Vector3D(newI, newJ, newK);
        curTrajectory.makeUnitVector();
        super.setTrajectory(curTrajectory);

        int curSpeed = super.getSpeed();

        float newX = curPosition.getX() + curTrajectory.getI()*(float)(curSpeed/FRAMERATE);
        float newY = curPosition.getY() + curTrajectory.getJ()*(float)(curSpeed/FRAMERATE);
        float newZ = curPosition.getZ() + curTrajectory.getK()*(float)(curSpeed/FRAMERATE);

        Point3D newPosition = new Point3D(newX, newY, newZ);

        return newPosition;
    }
}