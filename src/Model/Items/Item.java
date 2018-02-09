package Model.Items;

public class Item {
    private int currencyValue;

    public Item(){}

    public Item(int cv){
        currencyValue = cv;
    }

    public int getCurrencyValue(){
        return currencyValue;
    }

    public void setCurrencyValue(int cv) {
        currencyValue = cv;
    }

    public void Use(){}
}
