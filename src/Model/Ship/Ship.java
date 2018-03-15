package Model.Ship;

import Model.Items.Inventory;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.*;
import Utility.SystemTimer;
import Utility.Vector3D;

import static Utility.Config.*;

public class Ship {

    final private Pilot myPilot;
    final private ShipStats shipStats;
    final private ShipHull hullSlot;

    //Rendering info
    Vector3D facingDirection;

    //


    private ShipWeapon weaponSlot1;
    private ShipWeapon weaponSlot2;
    private ShipEngine engineSlot;
    private ShipShield shieldSlot;
    private ShipSpecial specialSlot;
    private Inventory inventory;

    private boolean shieldActivated;
    private SystemTimer shieldCooldown;

    public Ship(Pilot owner, ShipHull myShip){
        this.hullSlot = myShip;
        shipStats = new ShipStats(hullSlot.getmaxHealth());
        inventory = new Inventory(hullSlot.getInventorySize());
        myPilot = owner;
        shieldActivated = false;
        shieldCooldown = new SystemTimer();
    }

    public void equipWeapon1(ShipWeapon weapon){
        weaponSlot1 = weapon;
        updateMaxStats();
    }

    public void equipWeapon2(ShipWeapon weapon){
        weaponSlot2 = weapon;
        updateMaxStats();
    }

    public void equipEngine(ShipEngine engine){
        engineSlot = engine;
        updateMaxStats();
    }

    public void equipShield(ShipShield shield){
        shieldSlot = shield;
        updateMaxStats();
    }

    public void equipSpecial(ShipSpecial special){
        specialSlot = special;
        updateMaxStats();
    }

    //TODO add unequip methods

    public void updateMaxStats(){
        if (engineSlot != null) shipStats.setMaxSpeed(engineSlot.getMaxSpeed());
        if (specialSlot != null) shipStats.setMaxFuel(specialSlot.getmaxFuel());
        if (shieldSlot != null) shipStats.setMaxShield(shieldSlot.getmaxShield());
    }

    public Inventory getInventory(){
        return inventory;
    }

    public ShipStats getShipStats() {
        return shipStats;
    }

    public boolean isAlive(){
        return shipStats.isAlive();
    }

    public void useWeapon1(){
        if (!shieldActivated) {
            weaponSlot1.fireWeapon(myPilot);
        }
        else{
            System.out.println("Shield is active, CANNOT fire");
        }
    }

    public void useWeapon2(){
        if (!shieldActivated) {
            weaponSlot2.fireWeapon(myPilot);
        }
        else{
            System.out.println("Shield is active, CANNOT fire");
        }
    }

    public void toggleShieldActivated(){

        if (!shieldActivated && shieldCooldown.getElapsedTime() >= SHIELD_CD){
            shieldActivated = true;
            shipStats.modifyCurrentShield(shipStats.getMaxShield());
        }
        else if (shieldActivated){
            shieldCooldown.reset();
            shieldActivated = false;
            shipStats.modifyCurrentShield(-shipStats.getCurrentShield());
        }
    }

    public void setFacingDirection(Vector3D facingDirection) {
        this.facingDirection = facingDirection;
    }

    public Vector3D getFacingDirection() {
        return facingDirection;
    }

    public void accelerate(){
        shipStats.modifyCurrentSpeed(ACCELERATE_RATE);
    }

    public void decelerate(){
        shipStats.modifyCurrentSpeed(-ACCELERATE_RATE);
    }

    public void applyFriction(){
        if (shipStats.getCurrentSpeed() > 0){
            shipStats.modifyCurrentSpeed(-FRICTION_RATE);
        }
        else if (shipStats.getCurrentSpeed() < 0) {
            shipStats.modifyCurrentSpeed(FRICTION_RATE);
        }
    }


    public boolean shieldActivated(){
        return shieldActivated;
    }

    public void takeDamage(int amount){
        shipStats.modifyCurrentHealth(amount);
        if (!isAlive())
        {
            myPilot.pilotDied();
        }
    }

}
