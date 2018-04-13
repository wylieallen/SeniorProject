package Utility;

import Model.Items.Item;
import Model.Ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
