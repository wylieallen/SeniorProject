package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

public class CombatState implements AIState {

    @Override
    public void makeMove(Enemy enemy, AI ai) {

        ai.setTarget(ai.getNearestPilotTo(enemy));

        Point3D currentPosition = ai.getPositionOf(enemy);
        Point3D targetPosition = ai.getPositionOf(ai.getTarget());

        Vector3D direction = new Vector3D(currentPosition, targetPosition);

        enemy.getActiveShip().setFacingDirection(direction);
        enemy.increaseShipSpeed();


        System.out.println("Combat State \t" + currentPosition.toString() + " At Speed: " + enemy.getCurrentShipSpeed());

        //Test
        if (currentPosition.distance(currentPosition, targetPosition) < 1f){
            enemy.setAiState(new RoamingState());
        }
    }
}
