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
            ai.setAiState(new RoamingState());
            return;
        }

        ai.setTarget(target);

        Point3D currentPosition = ai.getPositionOf(thisPilot);
        Point3D targetPosition = ai.getPositionOf(ai.getTarget());

        Vector3D direction = new Vector3D(currentPosition, targetPosition);

        thisPilot.getActiveShip().setFacingDirection(direction);
       // System.out.println("I: " +direction.getI() + "J: " + direction.getJ() + "K: " + direction.getK());
        thisPilot.increaseShipSpeed();


       // System.out.println("Combat State \t" + currentPosition.toString() + " At Speed: " + enemy.getCurrentShipSpeed());

        //Test
        if (currentPosition.distance(currentPosition, targetPosition) < 160f){
            ai.setAiState(new TargetingState());
        }
    }
}
