package Utility;

import Model.Items.Item;
import Model.Pilot.Faction;
import Model.Ship.Ship;
import Utility.Geom3D.Point3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Utility.Config.BATTLEZONE_SIZE;

public class RandomNumberGenerator{

    private Random rng;

    public RandomNumberGenerator(){
        rng = new Random();
    }

    public int getRarityRandomInRange(int baseValue, int offset, Rarity rarity){
        double multiplier;
        switch (rarity){
            case COMMON:
                multiplier = 1;
                break;
            case RARE:
                multiplier = 1.5;
                break;
            case EPIC:
                multiplier = 2;
                break;
            case LEGENDARY:
                multiplier = 3;
                break;
            default:
                multiplier = 1;
                break;
        }
        double random = rng.nextInt(offset)*multiplier + baseValue;
        return (int) random;
    }

    public int getRandomInBetween(int start, int end){
        double random = rng.nextInt(end-start+1) + start;
        return (int) random;
    }

    public Rarity getRandomRarity(){

        int rarityDetermine = getRandomInBetween(1,100);
        //5% chance Legendary
        if (rarityDetermine > 95){
            return Rarity.LEGENDARY;
        }
        //15% chance Epic
        else if (rarityDetermine > 80){
            return Rarity.EPIC;
        }
        //30% chance Rare
        else if (rarityDetermine > 50){
            return Rarity.RARE;
        }
        //50% chance Common
        else {
            return Rarity.COMMON;
        }
    }

    public Faction getRandomFaction(){
        int rarityDetermine = getRandomInBetween(1,100);
        //5% chance ALLY
        if (rarityDetermine > 95){
            return Faction.ALLY;
        }
        //95% chance ENEMY
        else {
            return Faction.REBEL;
        }
    }

    public Point3D getRandomLocation(){
        int x = getRandomInBetween(-BATTLEZONE_SIZE,BATTLEZONE_SIZE);
        int y = getRandomInBetween(-BATTLEZONE_SIZE,BATTLEZONE_SIZE);
        int z = getRandomInBetween(-BATTLEZONE_SIZE,BATTLEZONE_SIZE);
        return new Point3D(x,y,z);
    }

    public List<Item> getRandomEquippedParts(Ship ship){
        List<Item> items = new ArrayList<>();
        int chance = getRandomInBetween(1, 20);
        if (chance == 1){
            items.add(ship.getEngineSlot());
        }
        chance = getRandomInBetween(1, 20);
        if (chance == 2){
            items.add(ship.getHullSlot());
        }
        chance = getRandomInBetween(1, 20);
        if (chance == 3){
            items.add(ship.getShieldSlot());
        }
        chance = getRandomInBetween(1, 20);
        if (chance == 4) {
            items.add(ship.getSpecialSlot());
        }
        chance = getRandomInBetween(1, 20);
        if (chance == 5){
            items.add(ship.getWeaponSlot1());
        }chance = getRandomInBetween(1, 20);
        if (chance == 6){
            items.add(ship.getWeaponSlot2());
        }
        System.out.println(items);
        return items;
    }

    public int getScaling(){

        int scaling;
        int chance = getRandomInBetween(1,100);
        //5% chance 5 items
        if (chance > 95){
            scaling = 5;
        }
        //15% chance 3 items
        else if (chance > 80){
            scaling = 3;
        }
        //30% chance 2 items
        else if (chance > 50){
            scaling = 2;
        }
        //50% chance 1 item
        else {
            scaling = 1;
        }

        return scaling;
    }
}
