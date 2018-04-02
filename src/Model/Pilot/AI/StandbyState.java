package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Utility.Geom3D.Point3D;

public class StandbyState implements AIState{
    @Override
    public void makeMove(Enemy thisPilot, AI ai) {


        Point3D currentPosition = ai.getPositionOf(thisPilot);
        Point3D targetPosition = ai.getPositionOf(ai.getTarget());


        if (currentPosition.distance(currentPosition, targetPosition) > 20f){
            ai.setAiState(new CombatState());
        }
    }
}
