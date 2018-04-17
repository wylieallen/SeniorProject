package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import Utility.RandomNumberGenerator;

public class StandbyState implements AIState{

    private int directionCooldown = 0;
    private int targetingCooldown;
    private RandomNumberGenerator rng = new RandomNumberGenerator();

    public StandbyState(int targetingCooldown){
        this.targetingCooldown = targetingCooldown;
    }

    @Override
    public void makeMove(Enemy thisPilot, AI ai) {

        Point3D currentPosition = ai.getPositionOf(thisPilot);

        Pilot target = ai.getNearestPilotTo(thisPilot);
        // Return to combat state if enemy is near
        if (target != null && targetingCooldown <= 0){
            Point3D targetPosition = ai.getPositionOf(target);
            int chance = rng.getRandomInBetween(1,200);
            float distance = currentPosition.distance(currentPosition, targetPosition);
            if (distance > 200f || (chance > 199 && distance > 60f)){
                ai.setAiState(new CombatState());
                return;
            }

        }
        Point3D lootPosition = ai.getNearestLootTo(thisPilot);
        // Return to looting state if loot is near
        if (lootPosition != null && targetingCooldown <= 0) {
            while (thisPilot.getCurrentShipSpeed() > 0) {
                thisPilot.decreaseShipSpeed();
            }
            ai.setAiState(new LootingState());
            return;
        }


        if (directionCooldown <= 0 || currentPosition.outOfArea() ){
            //Get a random unit vector
            float newI = rng.getRandomInBetween(-100, 100);
            float newJ = rng.getRandomInBetween(-100, 100);
            float newK = rng.getRandomInBetween(-100, 100);

            Vector3D direction = new Vector3D(newI, newJ, newK);
            direction.makeUnitVector();
            thisPilot.getActiveShip().setFacingDirection(direction);
            System.out.println("Current Position " + currentPosition.toString());
            directionCooldown = 2000;
        }
        thisPilot.increaseShipSpeed();
        directionCooldown--;
        targetingCooldown--;

    }
}
