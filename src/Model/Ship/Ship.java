package Model.Ship;

import Model.Items.Inventory;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.*;

public class Ship {

    final private Pilot myPilot;
    final private ShipStats myStats;
    final private ShipHull hullSlot;

    private ShipWeapon weaponSlot1;
    private ShipWeapon weaponSlot2;
    private ShipEngine engineSlot;
    private ShipShield shieldSlot;
    private ShipSpecial specialSlot;
    private Inventory inventory;

    public Ship(Pilot owner, ShipHull myShip){
        this.hullSlot = myShip;
        myStats = new ShipStats(hullSlot.getmaxHealth());
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
        if (engineSlot != null) myStats.setMaxSpeed(engineSlot.getMaxSpeed());
        if (specialSlot != null) myStats.setMaxFuel(specialSlot.getmaxFuel());
        if (shieldSlot != null) myStats.setMaxShield(shieldSlot.getmaxShield());
    }

    public Inventory getInventory(){
        return inventory;
    }


    public ShipStats getMyStats() {
        return myStats;
    }

    public boolean isAlive(){
        return myStats.isAlive();
    }

    public void takeDamage(int amount){
        myStats.modifyCurrentHealth(amount);
    }

}
