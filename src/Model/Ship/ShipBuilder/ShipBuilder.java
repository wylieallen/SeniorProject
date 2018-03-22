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

    public ShipEngine buildRandomEngine(int baseValue, int baseSpeed, Rarity rarity){
       return engineBuilder.buildRandomEngine(baseValue, baseSpeed, rarity);
    }

    public ShipHull buildRandomHull(int baseValue, int baseHealth, int inventorySize, Rarity rarity){
        return hullBuilder.buildRandomHull(baseValue, baseHealth, inventorySize, rarity);
    }

    public ShipShield buildRandomShield(int baseValue, int baseShield, Rarity rarity){
        return shieldBuilder.buildRandomShield(baseValue, baseShield, rarity);
    }

    //TODO implement Specials
    public ShipSpecial buildRandomSpecial(){
        return specialBuilder.buildRandomBoostSpecial();
    }

    public ShipWeapon buildRandomWeapon(int baseValue, int baseDamage, int baseSpeed, Rarity rarity){
        return weaponBuilder.buildRandomEnergyWeapon(baseValue, baseDamage, baseSpeed, rarity);
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

        Ship newShip = new Ship(owner, buildRandomHull(1000,100,8,rarity));
        newShip.equipEngine(buildRandomEngine(1000,50,rarity));
        newShip.equipShield(buildRandomShield(1000,50,rarity));
        newShip.equipSpecial(buildRandomSpecial());
        newShip.equipWeapon1(buildRandomWeapon(1000, 25, 40, rarity));
        newShip.equipWeapon2(buildRandomWeapon(1000, 5, 100, rarity));
        return newShip;
    }

}
