package Model.Pilot;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Pilot.AI.AI;
import Model.Pilot.AI.AIState;
import Model.Pilot.AI.CombatState;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

public class Enemy extends Pilot {

    private AI ai;
    private AIState aiState;

    public Enemy(){
        super.setPilotStats(new PilotStats(getMaxLevel()));
        super.setFaction(Faction.REBEL);
        ai = new AI();
        aiState = new CombatState();
    }

    public void setAiState(AIState aiState) {
        this.aiState = aiState;
    }

    @Override
    public void pilotDied() {
        BattleZone currentZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();
        currentZone.enemyDestroyed(this);
    }

    @Override
    public Point3D move(Point3D curPosition){

        aiState.makeMove(this, ai);
        Point3D newPostion = super.move(curPosition);
        return newPostion;
    }
}
