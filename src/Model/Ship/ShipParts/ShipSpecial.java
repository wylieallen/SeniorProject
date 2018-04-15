package Model.Ship.ShipParts;

import Model.Pilot.Pilot;
import Utility.Rarity;
import Utility.SystemTimer;

public abstract class ShipSpecial extends ShipPart{

    private int maxFuel;
    protected double fuelCost;
    protected boolean activated;
    private SystemTimer fuelCD;


    public ShipSpecial(String name, int value, String attributes, Rarity rarity, int maxFuel, double fuelCost){
        super(name, value, attributes, rarity);
        activated = false;
        this.maxFuel = maxFuel;
        this.fuelCost = fuelCost;
        this.fuelCD = new SystemTimer();
    }

    public abstract void activate(Pilot pilot);

    public abstract void deactivate(Pilot pilot);

    public int getmaxFuel() {
        return maxFuel;
    }

    public void setmaxFuel(int maxFuel) {
        this.maxFuel = maxFuel;
    }

    protected void consumeFuel(Pilot pilot){
        if (fuelCD.getElapsedTime() >= .2){
            pilot.getActiveShipStats().modifyCurrentFuel(-fuelCost);
            fuelCD.reset();
        }
    }
}
