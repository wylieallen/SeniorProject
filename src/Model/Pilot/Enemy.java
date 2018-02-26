package Model.Pilot;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Ship.Ship;
import Utility.Vector3D;

public class Enemy extends Pilot {

    public Enemy(){
        super.setPilotStats(new PilotStats(getMaxLevel()));
    }

    @Override
    public void pilotDied() {
        BattleZone currentZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();
        currentZone.enemyDestroyed(this);
    }

    @Override
    public void move(Vector3D unitVector) {

    }
}
