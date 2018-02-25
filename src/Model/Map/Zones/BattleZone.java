package Model.Map.Zones;

import Model.Items.LootChest;
import Model.Map.LocationTuple;
import Model.Pilot.*;
import Model.Powerup.Powerup;
import Utility.*;

import java.io.FileNotFoundException;
import java.util.List;

import static Utility.Config.FRAMERATE;


public class BattleZone extends Zone {

    private int zoneID;

    private List<LocationTuple<Player>> players;
    private List<LocationTuple<Enemy>> enemies;
    private List<LocationTuple<Powerup>> powerups;
    private List<LocationTuple<LootChest>> loot;


    public BattleZone(Player player, int zoneID){
        this.zoneID = zoneID;
        Point3D origin = new Point3D(0f,0f,0f);
        addPlayer(origin, player);
        addEnemies();
    }

    public void addPlayer(Point3D location, Player player){
        LocationTuple<Player> newPlayer = new LocationTuple<Player>(location, player);
        players.add(newPlayer);
    }

    public void addEnemies() {
        EnemyBuilder newEnemyBuilder = new EnemyBuilder();
        this.enemies = newEnemyBuilder.buildEnemies("/resources/Zones/battlezone", Integer.toString(zoneID));
    }

    //See if math is correct?
    public void updatePlayerPosition(Vector3D unitVector, Player player){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getObject() == player){

                Point3D curPosition = players.get(i).getLocation();
                float newX = curPosition.getX() + unitVector.getX()*(player.getCurrentShipSpeed()/FRAMERATE);
                float newY = curPosition.getY() + unitVector.getY()*(player.getCurrentShipSpeed()/FRAMERATE);
                float newZ = curPosition.getZ() + unitVector.getZ()*(player.getCurrentShipSpeed()/FRAMERATE);

                Point3D newPosition = new Point3D(newX, newY, newZ);
                players.get(i).setLocation(newPosition);
                break;
            }
        }
    }

    public void updateEnemyPosition(Vector3D unitVector, Enemy enemy){
        for (int i = 0; i < enemies.size(); i++){
            if (enemies.get(i).getObject() == enemy){

                Point3D curPosition = enemies.get(i).getLocation();
                float newX = curPosition.getX() + unitVector.getX()*(enemy.getCurrentShipSpeed()/FRAMERATE);
                float newY = curPosition.getY() + unitVector.getY()*(enemy.getCurrentShipSpeed()/FRAMERATE);
                float newZ = curPosition.getZ() + unitVector.getZ()*(enemy.getCurrentShipSpeed()/FRAMERATE);

                Point3D newPosition = new Point3D(newX, newY, newZ);
                enemies.get(i).setLocation(newPosition);
                break;
            }
        }
    }

    public void enemyDestroyed(Enemy enemy){
        for (int i = 0; i < enemies.size(); i++){
            if (enemies.get(i).getObject() == enemy){
                LocationTuple<Enemy> deadEnemy = enemies.remove(i);
                addLootChest(deadEnemy.getLocation());
                break;
            }
        }
    }

    //TODO Modify lootchest to instantiate with cargo from dead enemy
    public void addLootChest(Point3D location){
        LootChest newLootChest = new LootChest();

    }

}
