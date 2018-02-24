package Model.Ship.ShipParts;

import Utility.Rarity;

public class ShipWeapon extends ShipPart{

    // TODO Change to Projectile
    private int useValue;

    public ShipWeapon(int currencyValue, int useValue, Rarity rarity){
        super(currencyValue, rarity);
        this.useValue = useValue;
    }

    public int getUseValue() {
        return useValue;
    }

    public void setUseValue(int value) {
        this.useValue = value;
    }
}
