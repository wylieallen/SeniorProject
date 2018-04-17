package Model.Items;

import Model.Items.Consumables.FuelConsumable;
import Model.Items.Consumables.HealthConsumable;
import Model.Items.Consumables.ShieldConsumable;
import Model.Ship.ShipBuilder.ShipBuilder;
import Utility.RandomNumberGenerator;
import Utility.Rarity;

import static Utility.Config.*;

public class RandomItemGenerator {

    RandomNumberGenerator rng;
    ShipBuilder shipBuilder;

    public RandomItemGenerator(){
        rng = new RandomNumberGenerator();
        shipBuilder = new ShipBuilder();
    }

    public Item getRandomItem(){
        //Determine Rarity
        Rarity rarity = rng.getRandomRarity();

        //Determine type of Item
        int itemDetermine = rng.getRandomInBetween(1,100);
        //10% chance random ship part
        if (itemDetermine > 90){
            return shipBuilder.buildRandomPart(rarity.value(), rarity);
        }
        //20% chance for consumable
        else if (itemDetermine > 70){
            return getRandomConsumable(rarity);
        }
        //70% chance trash loot
        else{
            return new Item("trashLoot", rarity.value(), "Currency Value: " + rarity.value());
        }
    }

    public Item getRandomConsumable(Rarity rarity){

        int consumeValue = rng.getRarityRandomInRange(BASE_CONSUMABLE_USE, (int) (BASE_CONSUMABLE_USE*CONSUMABLE_USE_OFFSET), rarity);

        int chance = rng.getRandomInBetween(1,100);
        //10% chance shield
        if (chance > 90){
            return new ShieldConsumable(rarity.value(), consumeValue);
        }
        //30% chance fuel
        else if (chance > 60){
            return new FuelConsumable(rarity.value(), consumeValue);
        }
        //60% chance health
        else {
            return new HealthConsumable(rarity.value(), consumeValue);
        }
    }

}
