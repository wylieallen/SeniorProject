package Model.Ship.ShipParts.SpecialType;

import Model.Pilot.Pilot;
import Model.Ship.Ship;
import Model.Ship.ShipParts.ShipSpecial;
import Model.Ship.ShipStats;
import Utility.Rarity;

public class BoostSpecial extends ShipSpecial {

    double speedFactor;

    public BoostSpecial(int currencyValue, int maxFuel, double speedFactor, int fuelCost, Rarity rarity) {
        super(currencyValue, maxFuel, fuelCost, rarity);
        this.speedFactor = speedFactor;
        super.setName(rarity + "Boost Ship Special");
        super.setAttributes("Currency Value: " + currencyValue + "\nBoost Factor: " + speedFactor);
    }

    @Override
    public void activate(Pilot pilot) {
        if (!activated){
            ShipStats shipStats = pilot.getActiveShipStats();
            shipStats.setMaxSpeed(speedFactor);
            activated = true;
        }
        super.consumeFuel(pilot);
    }

    @Override
    public void deactivate(Pilot pilot) {
        if (activated){
            ShipStats shipStats = pilot.getActiveShipStats();
            shipStats.setMaxSpeed(1/speedFactor);
            activated = false;
        }
    }
}
