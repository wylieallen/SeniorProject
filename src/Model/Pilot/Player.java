package Model.Pilot;

import Model.Ship.Ship;
import Model.Ship.ShipHangar;
import Model.TradingPost.Wallet;

public class Player extends Pilot {

    private ShipHangar shipHangar;
    private Wallet myWallet;

    public Player(){
        super.setPilotStats(new PilotStats(1));
        shipHangar = new ShipHangar();
    }

    // TODO Add Currency class and make getter/setters

    public ShipHangar getShipHangar(){
        return shipHangar;
    }

    public Wallet getMyWallet(){
        return myWallet;
    }

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
