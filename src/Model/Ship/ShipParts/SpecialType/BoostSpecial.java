package Model.Ship.ShipParts.SpecialType;

import Model.Pilot.Pilot;
import Model.Ship.ShipParts.ShipSpecial;
import Model.Ship.ShipStats;
import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

public class BoostSpecial extends ShipSpecial {

    private double speedFactor;

    public BoostSpecial(int currencyValue, int maxFuel, double speedFactor, double fuelCost, Rarity rarity) {
        super(rarity + " Boost Ship Special", currencyValue, "Currency Value: " + currencyValue + "\nBoost Factor: " + speedFactor + "\nMax Fuel: " + maxFuel + "\nFuel Cost: " + fuelCost, rarity, maxFuel, fuelCost);
        this.speedFactor = speedFactor;
    }

    @Override
    public void activate(Pilot pilot) {
        ShipStats shipStats = pilot.getActiveShipStats();
        if (!activated){
            shipStats.scaleMaxSpeed(speedFactor);
            activated = true;
        }
        if (shipStats.getCurrentFuel() >= fuelCost){
            super.consumeFuel(pilot);
            System.out.println("Fuel: " + shipStats.getCurrentFuel() + "/" + shipStats.getMaxFuel());
        }
        else {
            deactivate(pilot);
        }
    }

    @Override
    public void deactivate(Pilot pilot) {
        if (activated){
            ShipStats shipStats = pilot.getActiveShipStats();
            shipStats.scaleMaxSpeed(1/speedFactor);
            activated = false;
        }
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonBoostSpecialImage();}
}
