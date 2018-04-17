package Model.Items;

import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Item {
    private int currencyValue;
    private String name;
    private ArrayList<String> attributes;

    public Item(String name, int value)
    {
        this.currencyValue = value;
        this.name = name;
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

    public void setAttributes(ArrayList<String> attributes) { this.attributes = attributes; }

    public ArrayList<String> getAttributes() { return attributes; }

    public String getName(){return name; }

    public void setName(String name){
        this.name = name;
    }

    public BufferedImage getImage() {return ImageFactory.getNotFoundImage();}

    public boolean isShipPart(){return false;}
}
