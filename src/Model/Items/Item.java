package Model.Items;

public class Item {
    private int currencyValue;
    private String name;

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

    public String getName(){return name; }

    public void setName(String name){
        this.name = name;
    }
}
