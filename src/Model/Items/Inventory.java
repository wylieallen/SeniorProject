package Model.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import Model.Items.Consumables.*;
import Model.Ship.ShipParts.ShipPart;

public class Inventory {
    private List<Item> itemList;
    private int currentItems;
    private int maxItems;
    private Iterator<Item> itemIterator;



    public Inventory(int mi){
        itemList = new ArrayList<>();
        itemIterator = itemList.iterator();
        currentItems = 0;
        maxItems = mi;
    }

    public int getcurrItemsNum() {return currentItems;}

    public boolean isFull() {return currentItems == maxItems;}

    public boolean isEmpty() {return currentItems == 0;}

    public void setMaxItems(int mi){maxItems = mi;}

    public int addItem(Item i) {
        if(isFull()){
            System.out.println("Inventory is full, cannot add item");
            return -1;
        }
        else
            itemList.add(i);
        currentItems++;
        return 0;
    }

    public Item removeItem(Item i){
        if(isEmpty()) {
            System.out.println("Inventory is empty, cannot remove item");
            return null;
        }
        else
            itemList.remove(i);
        currentItems--;
        return i;
    }

}
