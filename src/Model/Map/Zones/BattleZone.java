package Model.Map.Zones;

import Model.Items.LootChest;
import Model.Map.LocationTuple;
import Model.Pilot.*;
import Model.Powerup.Powerup;
import Utility.*;

import java.util.List;


public class BattleZone extends Zone {

    private int framerate = 30; // Put into constants?

    private List<LocationTuple<Player>> players;
    private List<LocationTuple<Enemy>> enemies;
    private List<LocationTuple<Powerup>> powerups;
    private List<LocationTuple<LootChest>> loot;


    //See if math is correct?
    public void updatePlayerPosition(Vector3D unitVector, Player player){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getObject() == player){

                Point3D curPosition = players.get(i).getLocation();
                float newX = curPosition.getX() + unitVector.getX()*(player.getCurrentShipSpeed()/framerate);
                float newY = curPosition.getY() + unitVector.getY()*(player.getCurrentShipSpeed()/framerate);
                float newZ = curPosition.getZ() + unitVector.getZ()*(player.getCurrentShipSpeed()/framerate);

                Point3D newPosition = new Point3D(newX, newY, newZ);
                players.get(i).setLocation(newPosition);
            }
        }

    }

    public void addLootBox(){

    }

}
