package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Utility.Geom3D.Point3D;

public class RoamingState implements AIState {
    @Override
    public void makeMove(Enemy enemy, AI ai) {


        //System.out.println("Standby");
        ai.setTarget(ai.getNearestPilotTo(enemy));

        Point3D currentPosition = ai.getPositionOf(enemy);
        Point3D targetPosition = ai.getPositionOf(ai.getTarget());
        if (currentPosition.distance(currentPosition, targetPosition) > 2f){
            ai.setAiState(new CombatState());
        }
    }
}
