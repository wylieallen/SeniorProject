package Model.Pilot;
import Model.Ship.Ship;
import Model.Ship.ShipParts.ShipHull;
import Model.Ship.ShipStats;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import Utility.Rarity;

import static Utility.Config.*;

public abstract class Pilot {
    private Ship activeShip;
    private PilotStats pilotStats;
    private int maxLevel;
    private Faction faction;

    public static Pilot NULL = new Pilot(Faction.NEUTRAL)
    {
        public void pilotDied() {}

        @Override
        public boolean move(Point3D curPosition)
        {
            return false;
        }
    };

    public Pilot(Faction faction, PilotStats pilotStats)
    {
        this.faction = faction;
        this.pilotStats = pilotStats;
    }

    public Pilot(Faction faction)
    {
        this(faction, new PilotStats(1));
    }

    public void setActiveShip(Ship ship){
        this.activeShip = ship;
    }

    public Ship getActiveShip(){
        return this.activeShip;
    }

    public ShipStats getActiveShipStats() { return activeShip.getShipStats(); }

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
/*
    public void fireWeapon1(){
        activeShip.useWeapon1();
    }

    public void fireWeapon2(){
        activeShip.useWeapon2();
    }*/

    public void activateSpecial() { activeShip.activateSpecial(); }

    public void deactivateSpecial() { activeShip.deactivateSpecial(); }

    public Vector3D getShipDirection(){
        return activeShip.getFacingDirection();
    }

    public double getDetectRange() {
        return activeShip.getDetectRange();
    }

    public void toggleShield(){
        activeShip.toggleShieldActivated();
    }

    public void gainExperience(int amount) {
        pilotStats.modifyExperience(amount);
    }

    public abstract void pilotDied();

    public abstract boolean move(Point3D curPosition);

}
