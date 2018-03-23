package Model.Pilot.AI;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;

public class AI {

    private BattleZone battleZone;
    private Pilot target;

    public AI(){
        battleZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();
    }

    public void setTarget(Pilot target) {
        this.target = target;
    }

    public Pilot getTarget() {
        return target;
    }

    public Pilot getNearestPilotTo(Pilot pilot){
        Point3D currentPosition = battleZone.getPositionOf(pilot);

        //For testing: change later
        return battleZone.getPlayer();
    }

    public Point3D getPositionOf(Pilot pilot){
        return battleZone.getPositionOf(pilot);
    }


}
