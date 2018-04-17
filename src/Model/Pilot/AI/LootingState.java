package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

public class LootingState implements AIState {
    @Override
    public void makeMove(Enemy thisPilot, AI ai) {


        // Return to Combat State if enemy is near
        Pilot target = ai.getNearestPilotTo(thisPilot);
        if (target != null){
            ai.setTarget(target);
            ai.setAiState(new CombatState());
            return;
        }


        Point3D currentPosition = ai.getPositionOf(thisPilot);
        Point3D lootPosition = ai.getNearestLootTo(thisPilot);

        // Return to Standby State if no loot is near
        if (lootPosition == null) {
            while (thisPilot.getCurrentShipSpeed() > 0) {
                thisPilot.decreaseShipSpeed();
            }
            ai.setAiState(new StandbyState(0));
            return;
        }

        //Set direction to loot
        Vector3D direction = new Vector3D(currentPosition, lootPosition);
        thisPilot.getActiveShip().setFacingDirection(direction);
        thisPilot.increaseShipSpeed();




    }
}
