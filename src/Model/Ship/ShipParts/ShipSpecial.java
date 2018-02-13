package Model.Ship.ShipParts;

public class ShipSpecial extends ShipPart{

    private int maxFuel;

    public ShipSpecial(int currencyValue, int maxFuel){
        this.maxFuel = maxFuel;
        this.setCurrencyValue(currencyValue);
    }

    public int getmaxFuel() {
        return maxFuel;
    }

    public void setmaxFuel(int maxFuel) {
        this.maxFuel = maxFuel;
    }
}
