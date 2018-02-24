package Model.Ship.ShipParts;
import Model.Items.Item;
import Utility.Rarity;

public abstract class ShipPart extends Item{

    private int currencyValue;
    private Rarity rarity;

    protected ShipPart(int currency, Rarity rarity){
        setCurrencyValue(currency);
        setRarity(rarity);
    }

    public int getCurrencyValue(){
        return currencyValue;
    }
    public void setCurrencyValue(int value){
        this.currencyValue = value;
    }
    public Rarity getRarity() { return rarity; }
    public void setRarity(Rarity rarity) {this.rarity = rarity; }

}
