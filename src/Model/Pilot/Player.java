package Model.Pilot;

import Model.Ship.Ship;
import Model.Ship.ShipHangar;
import Model.TradingPost.BountyMission;
import Model.TradingPost.Wallet;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

public class Player extends Pilot {

    private ShipHangar shipHangar;
    private Wallet myWallet;
    private BountyMission currentBountyMission;

    public Player(){
        super.setPilotStats(new PilotStats(1));
        super.setFaction(Faction.ALLY);
        shipHangar = new ShipHangar();
        myWallet = new Wallet(0);
        currentBountyMission = null;
    }

    public ShipHangar getShipHangar(){
        return shipHangar;
    }

    public Wallet getMyWallet(){
        return myWallet;
    }

    public BountyMission getCurrentBountyMission() {
        return currentBountyMission;
    }

    public void setCurrentBountyMission(BountyMission currentBountyMission) {
        this.currentBountyMission = currentBountyMission;
    }

    @Override
    public void setActiveShip(Ship ship) {
        //shipHangar.addShip(getActiveShip());
        super.setActiveShip(ship);
        //shipHangar.removeShip(ship);
    }

    @Override
    public void pilotDied() {
        //TODO go to death menu
        System.out.println("Game over");
    }


    @Override
    public boolean move(Point3D curPosition){
        //super.getActiveShip().setAccelerating(true);
        return false;
        //input handler
    }
}
