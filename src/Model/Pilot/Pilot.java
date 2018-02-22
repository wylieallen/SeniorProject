package Model.Pilot;
import Model.Map.Zones.BattleZone;
import Model.Ship.Ship;
import Model.Ship.ShipStats;
import Utility.Vector3D;

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

    public void accelerate(){
        activeShip.getShipStats().modifyCurrentSpeed(1);
    }

    public void decelerate(){
        activeShip.getShipStats().modifyCurrentSpeed(-1);
    }

    public void brake(){
        activeShip.getShipStats().modifyCurrentSpeed(-2);
    }


    public abstract void pilotDied();

    public abstract void Move(Vector3D unitVector);

}
