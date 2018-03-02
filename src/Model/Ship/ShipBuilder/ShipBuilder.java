package Model.Ship.ShipBuilder;

import Model.Pilot.Pilot;
import Model.Ship.Ship;
import Model.Ship.ShipParts.*;
import Utility.Rarity;

public class ShipBuilder {
    private EngineBuilder engineBuilder;
    private HullBuilder hullBuilder;
    private ShieldBuilder shieldBuilder;
    private SpecialBuilder specialBuilder;
    private WeaponBuilder weaponBuilder;

    public ShipBuilder(){
        engineBuilder = new EngineBuilder();
        hullBuilder = new HullBuilder();
        shieldBuilder = new ShieldBuilder();
        specialBuilder = new SpecialBuilder();
        weaponBuilder = new WeaponBuilder();
    }

    public ShipEngine buildEngine(int baseValue, int baseSpeed, Rarity rarity){
       return engineBuilder.buildEngine(baseValue, baseSpeed, rarity);
    }

    public ShipHull buildHull(int baseValue, int baseHealth, int inventorySize, Rarity rarity){
        return hullBuilder.buildHull(baseValue, baseHealth, inventorySize, rarity);
    }

    public ShipShield buildShield(int baseValue, int baseShield, Rarity rarity){
        return shieldBuilder.buildShield(baseValue, baseShield, rarity);
    }

    //TODO implement Specials
    public ShipSpecial buildSpecial(){
        return specialBuilder.buildSpecial();
    }

    //TODO implement Weapons
    public ShipWeapon buildWeapon(){
        return weaponBuilder.buildWeapon();
    }


    public Ship buildShip(Pilot owner, ShipEngine myEngine, ShipHull myHull, ShipShield myShield, ShipSpecial mySpecial, ShipWeapon myWeapon1, ShipWeapon myWeapon2){

        Ship newShip = new Ship(owner, myHull);
        newShip.equipEngine(myEngine);
        newShip.equipShield(myShield);
        newShip.equipSpecial(mySpecial);
        newShip.equipWeapon1(myWeapon1);
        newShip.equipWeapon2(myWeapon2);
        return newShip;
    }

    public Ship buildRandomShip(Pilot owner, Rarity rarity){

        Ship newShip = new Ship(owner, buildHull(1000,100,8,rarity));
        newShip.equipEngine(buildEngine(1000,50,rarity));
        newShip.equipShield(buildShield(1000,50,rarity));
        newShip.equipSpecial(buildSpecial());
        newShip.equipWeapon1(buildWeapon());
        newShip.equipWeapon2(buildWeapon());
        return newShip;
    }

}
