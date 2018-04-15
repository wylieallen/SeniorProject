package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.ShipWeapon;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import Utility.RandomNumberGenerator;

import static Utility.Config.AI_ACCURACY;

public class TargetingState implements AIState{

    @Override
    public void makeMove(Enemy thisPilot, AI ai) {

        Point3D currentPosition = ai.getPositionOf(thisPilot);

        //Get data of Target needed for calculation
        Pilot myTarget = ai.getTarget();
        Point3D targetPosition = ai.getPositionOf(myTarget);
        Vector3D targetDirection = myTarget.getShipDirection();

        // *randomize* targetspeed so Ai is not too OP
        float targetSpeed = (float) myTarget.getCurrentShipSpeed();
        float offset = Math.abs(targetSpeed - targetSpeed*AI_ACCURACY);

        RandomNumberGenerator rng = new RandomNumberGenerator();
        float targetSpeedWithAccuracy;

        if (targetSpeed < 0 ){
            targetSpeedWithAccuracy = -rng.getRandomInBetween( Math.abs((int) (targetSpeed+offset)), Math.abs((int) (targetSpeed-offset)));;
        }
        else {
            targetSpeedWithAccuracy = rng.getRandomInBetween( Math.abs((int) (targetSpeed-offset)), Math.abs((int) (targetSpeed+offset)));
        }


        //Backout if too close
        if (currentPosition.distance(currentPosition, targetPosition) < 25f){

            ai.setAiState(new StandbyState());
            thisPilot.getActiveShip().setFiring1(false);
            while (thisPilot.getCurrentShipSpeed() > 0){
                thisPilot.decreaseShipSpeed();
            }
            return;
        }

        //Select weapon to use
        float weaponSpeed = thisPilot.getActiveShip().getWeaponSlot1ProjectileSpeed();

        //Project target position to get new unit vector
        float newI = (targetDirection.getI()*targetSpeedWithAccuracy+targetPosition.getX()-currentPosition.getX())/weaponSpeed;
        float newJ = (targetDirection.getJ()*targetSpeedWithAccuracy+targetPosition.getY()-currentPosition.getY())/weaponSpeed;
        float newK = (targetDirection.getK()*targetSpeedWithAccuracy+targetPosition.getZ()-currentPosition.getZ())/weaponSpeed;

        Vector3D direction = new Vector3D(newI, newJ, newK);
        thisPilot.getActiveShip().setFacingDirection(direction);

        thisPilot.increaseShipSpeed();
        thisPilot.getActiveShip().setFiring1(true);

    }


}
