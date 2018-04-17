package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

public class CombatState implements AIState {

    @Override
    public void makeMove(Enemy thisPilot, AI ai) {

        // Return to Roaming State if no enemy is near
        Pilot target = ai.getNearestPilotTo(thisPilot);
        if (target == null){
            ai.setAiState(new LootingState());
            return;
        }

        ai.setTarget(target);
        Point3D currentPosition = ai.getPositionOf(thisPilot);
        Point3D targetPosition = ai.getPositionOf(ai.getTarget());

        Vector3D direction = new Vector3D(currentPosition, targetPosition);

        thisPilot.getActiveShip().setFacingDirection(direction);
        thisPilot.increaseShipSpeed();

        float distance = currentPosition.distance(currentPosition, targetPosition);

        if (distance < 180f){
            ai.setAiState(new TargetingState());
        }

    }
}
