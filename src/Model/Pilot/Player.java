package Model.Pilot;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Ship.Ship;
import Model.Ship.ShipHangar;
import Model.TradingPost.BountyMission;
import Model.TradingPost.Wallet;
import Utility.Vector3D;

public class Player extends Pilot {

    private ShipHangar shipHangar;
    private Wallet myWallet;
    private BountyMission currentBountyMission;

    public Player(){
        super.setPilotStats(new PilotStats(1));
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
        shipHangar.addShip(getActiveShip());
        super.setActiveShip(ship);
        shipHangar.removeShip(ship);
    }

    @Override
    public void pilotDied() {
        //TODO go to death menu
        System.out.println("Game over");
    }

    @Override
    public void accelerate(Vector3D unitVector){
        super.accelerate(unitVector);
        this.move(unitVector);
    }

    @Override
    public void applyFriction(Vector3D unitVector){
        super.applyFriction(unitVector);
        this.move(unitVector);
    }

    @Override
    public void decelerate(Vector3D unitVector){
        super.decelerate(unitVector);
        this.move(unitVector);
    }

    @Override
    public void move(Vector3D unitVector) {
        BattleZone currentZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();
        currentZone.updatePlayerPosition(unitVector, this);
    }
}
