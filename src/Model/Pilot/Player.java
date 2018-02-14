package Model.Pilot;

import Model.Ship.Ship;
import Model.Ship.ShipHangar;

public class Player extends Pilot {

    private ShipHangar shipHangar;
    private int currency;

    public Player(){
        super.setPilotStats(new PilotStats(1));
        shipHangar = new ShipHangar();
    }

    // TODO Add Currency class and make getter/setters

    @Override
    public void setActiveShip(Ship ship) {
        shipHangar.addShip(getActiveShip());
        super.setActiveShip(ship);
        shipHangar.removeShip(ship);
    }

    @Override
    public void Move() {

    }
}
