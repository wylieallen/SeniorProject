package Model.Ship.ShipParts.SpecialType;

import Model.Pilot.Pilot;
import Model.Ship.ShipParts.ShipSpecial;
import Model.Ship.ShipStats;
import Utility.Rarity;

public class BoostSpecial extends ShipSpecial {

    private double speedFactor;

    public BoostSpecial(int currencyValue, int maxFuel, double speedFactor, int fuelCost, Rarity rarity) {
        super(rarity + "Boost Ship Special", currencyValue, "Currency Value: " + currencyValue + "\nBoost Factor: " + speedFactor, rarity, maxFuel, fuelCost);
        this.speedFactor = speedFactor;
    }

    @Override
    public void activate(Pilot pilot) {
        if (!activated){
            ShipStats shipStats = pilot.getActiveShipStats();
            shipStats.scaleMaxSpeed(speedFactor);
            activated = true;
        }
        super.consumeFuel(pilot);
    }

    @Override
    public void deactivate(Pilot pilot) {
        if (activated){
            ShipStats shipStats = pilot.getActiveShipStats();
            shipStats.scaleMaxSpeed(1/speedFactor);
            activated = false;
        }
    }
}
