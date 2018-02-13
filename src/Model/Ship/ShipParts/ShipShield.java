package Model.Ship.ShipParts;

public class ShipShield extends ShipPart {

    private int maxShield;

    public ShipShield(int currencyValue, int maxShield){
        this.maxShield = maxShield;
        this.setCurrencyValue(currencyValue);
    }

    public int getmaxShield() {
        return maxShield;
    }

    public void setmaxShield(int maxShield) {
        this.maxShield = maxShield;
    }
}
