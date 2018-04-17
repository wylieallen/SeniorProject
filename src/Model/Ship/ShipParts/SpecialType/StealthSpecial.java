package Model.Ship.ShipParts.SpecialType;

import Model.Pilot.Pilot;
import Model.Ship.ShipParts.ShipSpecial;
import Model.Ship.ShipStats;
import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

public class StealthSpecial extends ShipSpecial {

    private double stealthFactor;
    private double oldDetectRange;


    public StealthSpecial(int currencyValue, int maxFuel, double stealthFactor, double fuelCost, Rarity rarity) {
        super(rarity + " Stealth Ship Special", currencyValue, "Currency Value: " + currencyValue + "\nStealth Factor: " + stealthFactor  + "\nMax Fuel: " + maxFuel + "\nFuel Cost: " + fuelCost, rarity, maxFuel, fuelCost);
        this.stealthFactor = stealthFactor;
    }

    @Override
    public void activate(Pilot pilot) {
        ShipStats shipStats = pilot.getActiveShipStats();
        if (!activated){
            oldDetectRange = shipStats.getDetectRange();
            double newDetectRange = oldDetectRange - oldDetectRange*stealthFactor;
            shipStats.setDetectRange(newDetectRange);
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
            shipStats.setDetectRange(oldDetectRange);
            activated = false;
        }
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonStealthSpecialImage();}
}

