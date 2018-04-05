package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;

public class StandbyState implements AIState{
    @Override
    public void makeMove(Enemy thisPilot, AI ai) {

        Pilot target = ai.getNearestPilotTo(thisPilot);
        if (target == null){
            return;
        }

        Point3D currentPosition = ai.getPositionOf(thisPilot);
        Point3D targetPosition = ai.getPositionOf(target);


        if (currentPosition.distance(currentPosition, targetPosition) > 150f){
            ai.setAiState(new CombatState());
        }
    }
}
