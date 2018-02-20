package Model.Pilot;

import Model.Map.Zones.BattleZone;
import Model.Ship.Ship;
import Utility.Vector3D;

public class Enemy extends Pilot {

    public Enemy(Ship ship){
        super.setActiveShip(ship);
        super.setPilotStats(new PilotStats(getMaxLevel()));
    }


    @Override
    public void Move(Vector3D unitVector) {

    }
}
