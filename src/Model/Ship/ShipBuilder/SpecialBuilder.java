package Model.Ship.ShipBuilder;

import Model.Ship.ShipParts.ShipSpecial;
import Model.Ship.ShipParts.SpecialType.BoostSpecial;
import Model.Ship.ShipParts.SpecialType.HealSpecial;
import Model.Ship.ShipParts.SpecialType.StealthSpecial;
import Utility.Rarity;

import static Utility.Config.*;

public class SpecialBuilder extends PartBuilder{

    public SpecialBuilder(){
        super();
    }


    public ShipSpecial buildRandomSpecial(int baseValue, int baseMaxFuel, int baseUseAmount, int baseFuelCost, Rarity rarity) {

        int currencyValue = super.generateRandom(baseValue, (int) (baseValue * CURRENCY_OFFSET), rarity);

        int maxFuel = super.generateRandom(baseMaxFuel, (int) (baseMaxFuel * MAX_FUEL_OFFSET), rarity);
        double useAmount = super.generateRandom(baseUseAmount, (int) (baseUseAmount * SPECIAL_USE_OFFSET), rarity);
        double fuelCost = super.generateRandom(baseFuelCost, (int) (baseFuelCost * FUEL_COST_OFFSET), rarity);

        double percent = ((double) baseFuelCost / (double) fuelCost);
        fuelCost = (percent * baseFuelCost);

        int chance = super.generateRandomBetween(1, 3);
        switch (chance) {
            case 1:
                return new BoostSpecial(currencyValue, maxFuel, useAmount * .05, fuelCost * .20, rarity);
            case 2:
                return new HealSpecial(currencyValue, maxFuel, useAmount, fuelCost * 1.2, rarity);
            case 3:
                return new StealthSpecial(currencyValue, maxFuel, 1, fuelCost * .30, rarity);
            default:
                return buildRandomSpecial(baseValue, baseMaxFuel, baseUseAmount, baseFuelCost, rarity);
        }
    }
}
