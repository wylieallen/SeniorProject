package Model.Items;

public class Item {
    private int currencyValue;
    private String name;
    private String attributes;

    public Item(String name, int value, String attributes)
    {
        this.currencyValue = value;
        this.name = name;
        this.attributes = attributes;
    }

    public int getCurrencyValue(){
        return currencyValue;
    }

    public void setCurrencyValue(int cv) {
        currencyValue = cv;
    }

    public void setUseValue(){}
    public int getUseValue(){return 0;}

    public void Use(){}

    public void setAttributes(String attributes) { this.attributes = attributes; }

    public String getAttributes() { return attributes; }

    public String getName(){return name; }

    public void setName(String name){
        this.name = name;
    }
}
