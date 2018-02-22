package Model.Ship;

import Model.Items.Inventory;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.*;

public class Ship {

    final private Pilot myPilot;
    final private ShipStats shipStats;
    final private ShipHull hullSlot;

    private ShipWeapon weaponSlot1;
    private ShipWeapon weaponSlot2;
    private ShipEngine engineSlot;
    private ShipShield shieldSlot;
    private ShipSpecial specialSlot;
    private Inventory inventory;

    public Ship(Pilot owner, ShipHull myShip){
        this.hullSlot = myShip;
        shipStats = new ShipStats(hullSlot.getmaxHealth());
        inventory = new Inventory(hullSlot.getInventorySize());
        myPilot = owner;
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

    public void equipSpeical(ShipSpecial special){
        specialSlot = special;
        updateMaxStats();
    }

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

    public void takeDamage(int amount){
        shipStats.modifyCurrentHealth(amount);
        if (isAlive() == false)
        {
            myPilot.pilotDied();
        }
    }

}
