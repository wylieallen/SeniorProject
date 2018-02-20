package Model.Pilot;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Ship.Ship;
import Model.Ship.ShipHangar;
import Model.TradingPost.BountyMission;
import Model.TradingPost.Wallet;
import Utility.Vector3D;

import java.util.Vector;

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

    // TODO Add Currency class and make getter/setters

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
    public void Move(Vector3D unitVector) {

        //Psuedo-code (get keypress and put in gameloop?)
        String keypress = "";

        BattleZone currentZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();
        if (keypress.equals("w") && getCurrentShipSpeed() != getMaxShipSpeed())
        {
            accelerate();
            currentZone.updatePlayerPosition(unitVector, this);
        }
        else if(keypress.equals("s") && getCurrentShipSpeed() != 0){
            brake();
            currentZone.updatePlayerPosition(unitVector, this);
        }
        else if (getCurrentShipSpeed() != 0)
        {
            decelerate();
            currentZone.updatePlayerPosition(unitVector, this);
        }


    }
}
