package Model.Ship;

import java.util.ArrayList;
import java.util.List;

public class ShipHangar {
    private List<Ship> myShips;
    private int activeShipIndex;

    public ShipHangar() {
        myShips = new ArrayList<Ship>();
    }

    public void addShip(Ship ship){
        myShips.add(ship);
    }

    public void removeShip(Ship ship){
        myShips.remove(ship);
    }

    public Ship getShipAtIndex(int index){
        return myShips.get(index);
    }

    public int hangarSize(){
        return myShips.size();
    }

    public class HangarIterator{
        private int index = 0;

        public Ship currentItem() {
            return myShips.get(index);
        }

        public boolean isValid() {
            if (index >= myShips.size()) {
                return false;
            }
            return true;
        }

        public void next() {
            index++;
        }

        public void reset() {
            index = 0;
        }
    }
}
