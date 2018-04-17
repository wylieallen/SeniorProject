package Model.Pilot.AI;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;

public class AI {

    private BattleZone battleZone;
    private AIState aiState;
    private Pilot target;

    public AI(){
        battleZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();
        aiState = new StandbyState(1000);
    }

    public void setAiState(AIState aiState) {
        this.aiState = aiState;
    }

    public void makeMove(Enemy thisPilot){
        aiState.makeMove(thisPilot, this);
    }

    public void setTarget(Pilot target) {
        this.target = target;
    }

    public Pilot getTarget() {
        return target;
    }

    public Pilot getNearestPilotTo(Pilot pilot){
        return battleZone.getNearestHostileTo(pilot);
    }

    public Point3D getNearestLootTo(Pilot pilot){
        return battleZone.getNearestLootTo(pilot);
    }

    public Point3D getPositionOf(Pilot pilot){
        return battleZone.getPositionOf(pilot);
    }


}
