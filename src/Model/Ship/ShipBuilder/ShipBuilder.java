package Model.Ship.ShipBuilder;

import Model.Items.Item;
import Model.Pilot.Pilot;
import Model.Ship.Ship;
import Model.Ship.ShipParts.*;
import Utility.Rarity;

import static Utility.Config.*;

public class ShipBuilder extends PartBuilder{
    private EngineBuilder engineBuilder;
    private HullBuilder hullBuilder;
    private ShieldBuilder shieldBuilder;
    private SpecialBuilder specialBuilder;
    private WeaponBuilder weaponBuilder;

    public ShipBuilder(){
        super();
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

    public ShipSpecial buildRandomSpecial(int baseValue, int baseMaxFuel, int baseUseAmount, int baseFuelCost, Rarity rarity){
        return specialBuilder.buildRandomSpecial(baseValue, baseMaxFuel, baseUseAmount, baseFuelCost, rarity);
    }

    public ShipWeapon buildRandomWeapon(int baseValue, int baseSpeed, int baseDamage, Rarity rarity){
        return weaponBuilder.buildRandomEnergyWeapon(baseValue, baseSpeed, baseDamage, rarity);
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

        int baseValue = rarity.value();
        Ship newShip = new Ship(owner, buildRandomHull(baseValue, BASE_HULL_HEALTH, BASE_HULL_INVENTORY, rarity));
        newShip.equipEngine(buildRandomEngine(baseValue, BASE_ENGINE_SPEED, rarity));
        newShip.equipShield(buildRandomShield(baseValue, BASE_SHIELD_VALUE, rarity));
        newShip.equipSpecial(buildRandomSpecial(baseValue, BASE_MAX_FUEL, BASE_SPECIAL_USE, BASE_FUEL_COST, rarity));
        newShip.equipWeapon1(buildRandomWeapon(baseValue, BASE_WEAPON_SPEED, BASE_WEAPON_DAMAGE, rarity));
        newShip.equipWeapon2(buildRandomWeapon(baseValue, BASE_WEAPON_SPEED, BASE_WEAPON_DAMAGE, rarity));
        return newShip;
    }

    public ShipPart buildRandomPart(int baseValue, Rarity rarity){

        //Equally determine type of part to build
        int chance = super.generateRandomBetween(1,5);
        switch (chance){
            case 1:
                return buildRandomEngine(baseValue, BASE_ENGINE_SPEED, rarity);
            case 2:
                return buildRandomHull(baseValue, BASE_HULL_HEALTH, BASE_HULL_INVENTORY, rarity);
            case 3:
                return buildRandomShield(baseValue, BASE_SHIELD_VALUE, rarity);
            case 4:
                return buildRandomSpecial(baseValue, BASE_MAX_FUEL, BASE_SPECIAL_USE, BASE_FUEL_COST, rarity);
            case 5:
                return buildRandomWeapon(baseValue, BASE_WEAPON_SPEED, BASE_WEAPON_DAMAGE, rarity);
            default:
                return buildRandomEngine(baseValue, BASE_ENGINE_SPEED, rarity);

        }
    }

}
