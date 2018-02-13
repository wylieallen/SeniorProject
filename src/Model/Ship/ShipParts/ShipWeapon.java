package Model.Ship.ShipParts;

public class ShipWeapon extends ShipPart{

    // TODO Change to Projectile
    private int useValue;

    public ShipWeapon(int currencyValue, int useValue){
        this.useValue = useValue;
        this.setCurrencyValue(currencyValue);
    }

    public int getUseValue() {
        return useValue;
    }

    public void setUseValue(int value) {
        this.useValue = value;
    }
}
