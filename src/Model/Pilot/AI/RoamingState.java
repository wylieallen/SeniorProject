package Model.Pilot.AI;

import Model.Pilot.Enemy;

public class RoamingState implements AIState {
    @Override
    public void makeMove(Enemy enemy, AI ai) {
        System.out.println("Roaming");
    }
}
