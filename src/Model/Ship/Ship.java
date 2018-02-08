package Model.Ship;

import Model.Items.Inventory;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.*;

public class Ship {

    private Pilot myPilot;
    private ShipStats myStats;
    private ShipWeapon weaponSlot1;
    private ShipWeapon weaponSlot2;
    private ShipEngine engineSlot;
    private ShipShield shieldSlot;
    private ShipSpecial specialSlot;
    private Inventory cargobay;
    private int inventorySize;

    public void equipWeapon1(ShipWeapon weapon){
        weaponSlot1 = weapon;
        updateStats();
    }

    public void equipWeapon2(ShipWeapon weapon){
        weaponSlot2 = weapon;
        updateStats();
    }

    public void equipEngine(ShipEngine engine){
        engineSlot = engine;
        updateStats();
    }

    public void equipShield(ShipShield shield){
        shieldSlot = shield;
        updateStats();
    }

    public void equipSpeical(ShipSpecial special){
        specialSlot = special;
        updateStats();
    }

    public void updateStats(){

    }

    public boolean isAlive(){
        return myStats.isAlive();
    }

    public void takeDamage(int amount){
        myStats.modifyCurrentHealth(amount);
    }

}
