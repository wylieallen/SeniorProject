package Model.Pilot.AI;

import Model.Pilot.Enemy;

public interface AIState {


    public void makeMove(Enemy thisPilot, AI ai);

}
