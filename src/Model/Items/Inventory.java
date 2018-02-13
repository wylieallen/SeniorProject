package Model.Items;

import java.util.LinkedHashSet;
import java.util.Set;
import Model.Items.Consumables.*;

public class Inventory {
    private Set<HealthConsumable> healthConsumables;
    private Set<FuelConsumable> fuelConsumables;
    private Set<ShieldConsumable> shieldConsumables;
    private Set<Item> miscItems;
    private int currentItems;
    private int maxItems;



    public Inventory(int mi){
        healthConsumables = new LinkedHashSet<>();
        fuelConsumables = new LinkedHashSet<>();
        shieldConsumables = new LinkedHashSet<>();
        miscItems = new LinkedHashSet<>();
        currentItems = 0;
        maxItems = mi;
    }

    public int getcurrItemsNum() {return currentItems;}

    public boolean isFull() {return currentItems == maxItems;}

    public boolean isEmpty() {return currentItems == 0;}

    public int addHealthConsumable(HealthConsumable hc) {
        if(isFull()){
            System.out.println("Inventory is full, cannot add item");
            return -1;
        }
        else
            healthConsumables.add(hc);
            currentItems++;
            return 0;
    }

    public int removeHealthConsumable(HealthConsumable hc){
        if(isEmpty()) {
            System.out.println("Inventory is empty, cannot remove item");
            return -1;
        }
        else
            healthConsumables.remove(hc);
            return 0;
    }

    //Add rest of add and remove methods!
}
