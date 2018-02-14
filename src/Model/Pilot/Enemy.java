package Model.Pilot;

import Model.Ship.Ship;

public class Enemy extends Pilot {

    public Enemy(Ship ship){
        super.setActiveShip(ship);
        super.setPilotStats(new PilotStats(getMaxLevel()));
    }


    @Override
    public void Move() {

    }
}
