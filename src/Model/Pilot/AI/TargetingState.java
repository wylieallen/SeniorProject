package Model.Pilot.AI;

import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Model.Ship.ShipParts.ShipWeapon;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

public class TargetingState implements AIState{

    @Override
    public void makeMove(Enemy thisPilot, AI ai) {

        Point3D currentPosition = ai.getPositionOf(thisPilot);

        //Get data of Target needed for calculation
        Pilot myTarget = ai.getTarget();
        Point3D targetPosition = ai.getPositionOf(myTarget);
        Vector3D targetDirection = myTarget.getShipDirection();
        float targetSpeed = (float) myTarget.getCurrentShipSpeed();

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
        float newI = (targetDirection.getI()*targetSpeed+targetPosition.getX()-currentPosition.getX())/weaponSpeed;
        float newJ = (targetDirection.getJ()*targetSpeed+targetPosition.getY()-currentPosition.getY())/weaponSpeed;
        float newK = (targetDirection.getK()*targetSpeed+targetPosition.getZ()-currentPosition.getZ())/weaponSpeed;

        Vector3D direction = new Vector3D(newI, newJ, newK);
        thisPilot.getActiveShip().setFacingDirection(direction);

        thisPilot.increaseShipSpeed();
        thisPilot.getActiveShip().setFiring1(true);

    }


}
