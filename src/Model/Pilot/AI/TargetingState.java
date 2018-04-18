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
    private int xOffset;
    private int yOffset;
    private int zOffset;

    @Override
    public void makeMove(Enemy thisPilot, AI ai) {

        Point3D currentPosition = ai.getPositionOf(thisPilot);

        //Get data of Target needed for calculation
        Pilot myTarget = ai.getTarget();

        Point3D targetPosition = ai.getPositionOf(myTarget);
        Vector3D targetDirection = myTarget.getShipDirection();


        //Random chance to flee
        int chance = rng.getRandomInBetween(1,500);
        if (chance == 500){
            ai.setAiState(new StandbyState(750));
            thisPilot.getActiveShip().setFiring1(false);
            while (thisPilot.getCurrentShipSpeed() > 0){
                thisPilot.decreaseShipSpeed();
            }
            System.out.println("FLEEING!!!!!!");System.out.println("FLEEING!!!!!!");System.out.println("FLEEING!!!!!!");System.out.println("FLEEING!!!!!!");System.out.println("FLEEING!!!!!!");System.out.println("FLEEING!!!!!!");
            return;
        }

        //Backout if too close
        float distance = currentPosition.distance(currentPosition, targetPosition);
        if (distance < 40f || !myTarget.getActiveShip().isAlive() || distance > myTarget.getDetectRange()){
            ai.setAiState(new StandbyState(300));
            thisPilot.getActiveShip().setFiring1(false);
            while (thisPilot.getCurrentShipSpeed() > 0){
                thisPilot.decreaseShipSpeed();
            }
            return;
        }

        //Select weapon to use
        ShipWeapon weaponToUse = thisPilot.getActiveShip().getWeaponSlot1();
        float weaponSpeed = weaponToUse.getProjectileSpeed();

        // *randomize* targetspeed so Ai is not too OP
        float targetSpeed = (float) myTarget.getCurrentShipSpeed();
        float offsetSpeed = Math.abs(targetSpeed - targetSpeed*AI_ACCURACY);


        RandomNumberGenerator rng = new RandomNumberGenerator();

        if (accuracyCooldown == 0){
            accuracySpeed = rng.getRandomInBetween(-(int) offsetSpeed, (int) offsetSpeed);
            int positionOffset = (int) (20f - AI_ACCURACY*20f);

            System.out.println("Position offset: " + positionOffset);
            xOffset = rng.getRandomInBetween(-positionOffset, positionOffset);
            yOffset = rng.getRandomInBetween(-positionOffset, positionOffset);
            zOffset = rng.getRandomInBetween(-positionOffset, positionOffset);
            accuracyCooldown = 500;
        }
        accuracyCooldown--;

        //System.out.println("Current speed: " + targetSpeed + " \nScaled speed: " + accuracySpeed + "\n");

        float targetSpeedWithAccuracy = targetSpeed+accuracySpeed;

        //Project target position to get new unit vector
        float newI = (targetDirection.getI()*targetSpeedWithAccuracy+(targetPosition.getX()+xOffset)-currentPosition.getX())/weaponSpeed;
        float newJ = (targetDirection.getJ()*targetSpeedWithAccuracy+(targetPosition.getY()+yOffset)-currentPosition.getY())/weaponSpeed;
        float newK = (targetDirection.getK()*targetSpeedWithAccuracy+(targetPosition.getZ()+zOffset)-currentPosition.getZ())/weaponSpeed;



        Vector3D direction = new Vector3D(newI, newJ, newK);
        thisPilot.getActiveShip().setFacingDirection(direction);

        thisPilot.increaseShipSpeed();
        thisPilot.getActiveShip().setFiring1(true);

    }


}
