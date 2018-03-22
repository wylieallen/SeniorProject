package Model.Ship.ShipParts;

import Model.Pilot.Pilot;
import Utility.Rarity;

public abstract class ShipSpecial extends ShipPart{

    private int maxFuel;
    private int fuelCost;
    protected boolean activated;


    public ShipSpecial(int currencyValue, int maxFuel, int fuelCost, Rarity rarity){
        super(currencyValue, rarity);
        activated = false;
        this.maxFuel = maxFuel;
        this.fuelCost = fuelCost;
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
        pilot.getActiveShipStats().modifyCurrentFuel(fuelCost);
    }
}
