package Model.Items;

import Model.Ship.ShipBuilder.ShipBuilder;
import Utility.RandomNumberGenerator;
import Utility.Rarity;

public class RandomItemGenerator {

    RandomNumberGenerator rng;
    ShipBuilder shipBuilder;

    public RandomItemGenerator(){
        rng = new RandomNumberGenerator();
        shipBuilder = new ShipBuilder();
    }

    public Item getRandomItem(){

        Item itemToReturn;

        //Determine Rarity
        Rarity rarity;

        int rarityDetermine = rng.getRandomInBetween(1,100);
        //5% chance Legendary
        if (rarityDetermine > 95){
            rarity = Rarity.LEGENDARY;
        }
        //15% chance Epic
        else if (rarityDetermine > 80){
            rarity = Rarity.EPIC;
        }
        //30% chance Rare
        else if (rarityDetermine > 50){
            rarity = Rarity.RARE;
        }
        //50% chance Common
        else {
            rarity = Rarity.COMMON;
        }

        //Determine type of Item

        int itemDetermine = rng.getRandomInBetween(1,100);
        //10% chance random ship part
        if (rarityDetermine > 90){
            itemToReturn = shipBuilder.buildRandomPart(rarity.value(), rarity);
        }
        //90% chance trash loot
        else{
            itemToReturn = new Item("trashLoot", rarity.value(), "Currency Value: " + rarity.value());
        }

        return itemToReturn;
    }



}
