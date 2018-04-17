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
        Rarity rarity = rng.getRandomRarity();

        //Determine type of Item
        int itemDetermine = rng.getRandomInBetween(1,100);
        //10% chance random ship part
        if (itemDetermine > 90){
            itemToReturn = shipBuilder.buildRandomPart(rarity.value(), rarity);
        }
        //90% chance trash loot
        else{
            itemToReturn = new Item("trashLoot", rarity.value(), "Currency Value: " + rarity.value());
        }

        return itemToReturn;
    }



}
