package Model.Items;

import java.util.LinkedHashSet;
import java.util.Set;
import Model.Items.Consumables.*;
import Model.Ship.ShipParts.ShipPart;

public class Inventory {
    private Set<HealthConsumable> healthConsumables;
    private Set<FuelConsumable> fuelConsumables;
    private Set<ShieldConsumable> shieldConsumables;
    private Set<Item> miscItems;
    private Set<ShipPart> shipParts;
    private int currentItems;
    private int maxItems;



    public Inventory(int mi){
        healthConsumables = new LinkedHashSet<>();
        fuelConsumables = new LinkedHashSet<>();
        shieldConsumables = new LinkedHashSet<>();
        miscItems = new LinkedHashSet<>();
        shipParts = new LinkedHashSet<>();
        currentItems = 0;
        maxItems = mi;
    }

    public int getcurrItemsNum() {return currentItems;}

    public boolean isFull() {return currentItems == maxItems;}

    public boolean isEmpty() {return currentItems == 0;}

    public void setMaxItems(int mi){maxItems = mi;}

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
            currentItems--;
            return 0;
    }

    public int addFuelConsumable(FuelConsumable fc) {
        if(isFull()){
            System.out.println("Inventory is full, cannot add item");
            return -1;
        }
        else
            fuelConsumables.add(fc);
        currentItems++;
        return 0;
    }

    public int removeFuelConsumable(FuelConsumable fc){
        if(isEmpty()) {
            System.out.println("Inventory is empty, cannot remove item");
            return -1;
        }
        else
            fuelConsumables.remove(fc);
        currentItems--;
        return 0;
    }

    public int addShieldConsumable(ShieldConsumable sc) {
        if(isFull()){
            System.out.println("Inventory is full, cannot add item");
            return -1;
        }
        else
            shieldConsumables.add(sc);
        currentItems++;
        return 0;
    }

    public int removeShieldConsumable(ShieldConsumable sc){
        if(isEmpty()) {
            System.out.println("Inventory is empty, cannot remove item");
            return -1;
        }
        else
            shieldConsumables.remove(sc);
        currentItems--;
        return 0;
    }

    public int addMiscItem(Item i) {
        if(isFull()){
            System.out.println("Inventory is full, cannot add item");
            return -1;
        }
        else
            miscItems.add(i);
        currentItems++;
        return 0;
    }

    public int removeMiscItem(Item i){
        if(isEmpty()) {
            System.out.println("Inventory is empty, cannot remove item");
            return -1;
        }
        else
            miscItems.remove(i);
        currentItems--;
        return 0;
    }

    public int addShipPart(ShipPart s) {
        if(isFull()){
            System.out.println("Inventory is full, cannot add item");
            return -1;
        }
        else
            shipParts.add(s);
        currentItems++;
        return 0;
    }

    public int removeShipPart(ShipPart s){
        if(isEmpty()) {
            System.out.println("Inventory is empty, cannot remove item");
            return -1;
        }
        else
            shipParts.remove(s);
        currentItems--;
        return 0;
    }

    //
}
