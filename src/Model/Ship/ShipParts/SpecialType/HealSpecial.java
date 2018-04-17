package Model.Ship.ShipParts.SpecialType;

import Model.Pilot.Pilot;
import Model.Ship.ShipParts.ShipSpecial;
import Model.Ship.ShipStats;
import Utility.Rarity;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

public class HealSpecial extends ShipSpecial {

    private double healPercent;

    public HealSpecial(int currencyValue, int maxFuel, double healPercent, double fuelCost, Rarity rarity) {
        super(rarity + " Heal Ship Special", currencyValue, "Currency Value: " + currencyValue + "\nHeal Percent: " + healPercent  + "\nMax Fuel: " + maxFuel + "\nFuel Cost: " + fuelCost, rarity, maxFuel, fuelCost);
        this.healPercent = healPercent;
    }

    @Override
    public void activate(Pilot pilot) {
        ShipStats shipStats = pilot.getActiveShipStats();
        if (!activated && shipStats.getCurrentFuel() >= fuelCost){
            shipStats.modifyCurrentHealth((int) (shipStats.getMaxHealth()*healPercent/100));
            System.out.println("Health:" + shipStats.getCurrentHealth() + "/" + shipStats.getMaxHealth());
            activated = true;
            super.consumeFuel(pilot);
        }
    }

    @Override
    public void deactivate(Pilot pilot) {
        if (activated) {
            activated = false;
        }
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonHealingSpecialImage();}
}
