package Model.Pilot;
import Model.Ship.Ship;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import static Utility.Config.*;

public abstract class Pilot {
    private Ship activeShip;
    private PilotStats pilotStats;
    private int maxLevel;
    private Faction faction;

    public void setActiveShip(Ship ship){
        this.activeShip = ship;
    }

    public Ship getActiveShip(){
        return this.activeShip;
    }

    public double getCurrentShipSpeed() {
        return getActiveShip().getShipStats().getCurrentSpeed();
    };

    public double getMaxShipSpeed() {
        return getActiveShip().getShipStats().getMaxSpeed();
    };

    public void setPilotStats(PilotStats pilotStats){
        this.pilotStats = pilotStats;
    }

    public PilotStats getPilotStats(){
        return pilotStats;
    }

    public int getMaxLevel(){
        return maxLevel;
    }

    public void setMaxLevel(){
        this.maxLevel = maxLevel;
    }

    public Faction getFaction() { return faction; }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public void increaseShipSpeed(){ activeShip.accelerate(); }

    public void applyFriction(){
        activeShip.applyFriction();
    }

    public void decreaseShipSpeed(){ activeShip.decelerate(); }

    public void fireWeapon1(){
        activeShip.useWeapon1();
    }

    public Vector3D getShipDirection(){
        return activeShip.getFacingDirection();
    }

    public void toggleShield(){
        activeShip.toggleShieldActivated();
    }

    public abstract void pilotDied();

    public Point3D move(Point3D curPosition){
        Vector3D curTrajectory = getShipDirection();
        double curSpeed = getCurrentShipSpeed();

        float newX = curPosition.getX() + curTrajectory.getI()*(float)(curSpeed/FRAMERATE);
        float newY = curPosition.getY() + curTrajectory.getJ()*(float)(curSpeed/FRAMERATE);
        float newZ = curPosition.getZ() + curTrajectory.getK()*(float)(curSpeed/FRAMERATE);

        Point3D newPosition = new Point3D(newX, newY, newZ);

        return newPosition;


    }

}
