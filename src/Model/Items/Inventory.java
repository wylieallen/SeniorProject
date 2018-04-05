package Model.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public int addItems(List<Item> items){
        for (int i = 0; i < items.size(); i++){
            if(isFull()){
                System.out.println("Inventory is full, cannot add item");
                return -1;
            }
            else{
                itemList.add(items.get(i));
            }
            currentItems++;
        }
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

    public Item getItem(int index) { return itemList.get(index); }

    public int getIndex(Item item) {
        return itemList.indexOf(item);
    }

    public List<Item> getItems() { return itemList; }

    public String toString(){
        String items = "";
        for (int i = 0; i< itemList.size(); i++){
            items += itemList.get(i).getName() + "\n";// + itemList.get(i).getAttributes();
        }
        return items;
    }
}
