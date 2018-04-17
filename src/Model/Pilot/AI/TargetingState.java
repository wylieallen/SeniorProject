package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.ShipWeapon;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import Utility.RandomNumberGenerator;

import static Utility.Config.AI_ACCURACY;


public class TargetingState implements AIState{

    private RandomNumberGenerator rng = new RandomNumberGenerator();
    private int accuracyCooldown = 0;
    private int accuracySpeed;
    private int accuracyWeap;

    @Override
    public void makeMove(Enemy thisPilot, AI ai) {

        Point3D currentPosition = ai.getPositionOf(thisPilot);

        //Get data of Target needed for calculation
        Pilot myTarget = ai.getTarget();

        Point3D targetPosition = ai.getPositionOf(myTarget);
        Vector3D targetDirection = myTarget.getShipDirection();


        //Random chance to flee
        int chance = rng.getRandomInBetween(1,250);
        if (chance == 250){
            ai.setAiState(new StandbyState(750));
            thisPilot.getActiveShip().setFiring1(false);
            while (thisPilot.getCurrentShipSpeed() > 0){
                thisPilot.decreaseShipSpeed();
            }
            return;
        }

        //Backout if too close
        float distance = currentPosition.distance(currentPosition, targetPosition);
        if (distance < 30f || !myTarget.getActiveShip().isAlive() || distance > myTarget.getDetectRange()){
            ai.setAiState(new StandbyState());
            thisPilot.getActiveShip().setFiring1(false);
            while (thisPilot.getCurrentShipSpeed() > 0){
                thisPilot.decreaseShipSpeed();
            }
            return;
        }

        //Select weapon to use
        ShipWeapon weaponToUse = thisPilot.getActiveShip().getWeaponSlot1();


        // *randomize* targetspeed/weaponspeed so Ai is not too OP
        float targetSpeed = (float) myTarget.getCurrentShipSpeed();
        float offsetSpeed = Math.abs(targetSpeed - targetSpeed*AI_ACCURACY);

        float weaponSpeed = weaponToUse.getProjectileSpeed();
        float offsetWeapSpeed = Math.abs(weaponSpeed - weaponSpeed*AI_ACCURACY);

        RandomNumberGenerator rng = new RandomNumberGenerator();

        if (accuracyCooldown == 0){
            accuracySpeed = rng.getRandomInBetween(-(int) offsetSpeed, (int) offsetSpeed);
            accuracyWeap = rng.getRandomInBetween(-(int) offsetWeapSpeed, (int) offsetWeapSpeed);
            accuracyCooldown = 500;
        }
        accuracyCooldown--;

        System.out.println("Current speed: " + targetSpeed + "Scaled speed: " + accuracySpeed);
        System.out.println("Current weap: " + weaponSpeed + "Scaled weap: " + accuracyWeap);

        float targetSpeedWithAccuracy = targetSpeed+accuracySpeed;
        float targetWeapSpeedWithAccuracy = weaponSpeed;//+accuracyWeap;

        //Project target position to get new unit vector
        float newI = (targetDirection.getI()*targetSpeedWithAccuracy+targetPosition.getX()-currentPosition.getX())/targetWeapSpeedWithAccuracy;
        float newJ = (targetDirection.getJ()*targetSpeedWithAccuracy+targetPosition.getY()-currentPosition.getY())/targetWeapSpeedWithAccuracy;
        float newK = (targetDirection.getK()*targetSpeedWithAccuracy+targetPosition.getZ()-currentPosition.getZ())/targetWeapSpeedWithAccuracy;

        Vector3D direction = new Vector3D(newI, newJ, newK);
        thisPilot.getActiveShip().setFacingDirection(direction);

        thisPilot.increaseShipSpeed();
        thisPilot.getActiveShip().setFiring1(true);

    }


}
