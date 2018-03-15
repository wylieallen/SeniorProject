package Model.Pilot;
import Model.Ship.Ship;
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

    public void accelerate(Vector3D unitVector){
        activeShip.getShipStats().modifyCurrentSpeed(ACCELERATE_RATE);
    }

    public void applyFriction(Vector3D unitVector){
        if (activeShip.getShipStats().getCurrentSpeed() > 0){
            activeShip.getShipStats().modifyCurrentSpeed(-FRICTION_RATE);
        }
        else if (activeShip.getShipStats().getCurrentSpeed() < 0) {
            activeShip.getShipStats().modifyCurrentSpeed(FRICTION_RATE);
        }
    }

    public void decelerate(Vector3D unitVector){
        activeShip.getShipStats().modifyCurrentSpeed(-ACCELERATE_RATE);
    }

    public void fireWeapon1(){
        activeShip.useWeapon1();
    }

    public void toggleShield(){
        activeShip.toggleShieldActivated();
    }

    public abstract void pilotDied();

    public abstract void move(Vector3D unitVector);

}
