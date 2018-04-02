package Model.Items;

import java.util.ArrayList;
import java.util.List;

public class LootChest {

    private List<Item> items = new ArrayList<>();
    private boolean expired = false;

    public LootChest(){
        //Randomly add items
    }

    public LootChest(List<Item> items){
        this.items = items;
    }

    public List<Item> getItems(){
        return items;
    }

    public void disable() { expired = true; }

    public boolean expired() { return expired; }

}
