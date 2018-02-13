package Model.Ship.ShipParts;
import Model.Items.Item;

public abstract class ShipPart extends Item{

    private int currencyValue;

    public int getCurrencyValue(){
        return currencyValue;
    }
    public void setCurrencyValue(int value){
        this.currencyValue = value;
    }

}
