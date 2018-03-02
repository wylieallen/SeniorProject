package Model.Pilot;
import Model.Map.Zones.BattleZone;
import Model.Ship.Ship;
import Model.Ship.ShipStats;
import Utility.Vector3D;
import static Utility.Config.*;

public abstract class Pilot {
    private Ship activeShip;
    private PilotStats pilotStats;
    private int maxLevel;

    public void setActiveShip(Ship ship){
        this.activeShip = ship;
    }

    public Ship getActiveShip(){
        return this.activeShip;
    }

    public int getCurrentShipSpeed() {
        return getActiveShip().getShipStats().getCurrentSpeed();
    };

    public int getMaxShipSpeed() {
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


    public abstract void pilotDied();

    public abstract void move(Vector3D unitVector);

}
