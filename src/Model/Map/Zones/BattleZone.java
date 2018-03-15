package Model.Map.Zones;

import Model.Items.LootChest;
import Model.Map.LocationTuple;
import Model.Pilot.*;
import Model.Powerup.Powerup;
import Model.Ship.ShipParts.Projectile.Projectile;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

import java.util.ArrayList;
import java.util.List;

import static Utility.Config.FRAMERATE;


public class BattleZone extends Zone {

    private int zoneID;

    private List<LocationTuple<Player>> players = new ArrayList<>();
    private List<LocationTuple<Enemy>> enemies = new ArrayList<>();
    private List<LocationTuple<Projectile>> projectiles = new ArrayList<>();

    private List<LocationTuple<Powerup>> powerups;
    private List<LocationTuple<LootChest>> loot;


    public BattleZone(int zoneID){
        this.zoneID = zoneID;
    }

    public void run(Player player){
        Point3D origin = new Point3D(0f,0f,0f);
        addPlayer(origin, player);
        addEnemies();
    }

    public void update(){
        updateProjectilePosition();
    }


    public void addPlayer(Point3D location, Player player){
        LocationTuple<Player> newPlayer = new LocationTuple<Player>(location, player);
        players.add(newPlayer);
    }

    public void addEnemies() {
        EnemyBuilder newEnemyBuilder = new EnemyBuilder();
        this.enemies = newEnemyBuilder.buildEnemies("resources/Zones/battlezone", "1");
    }

    public void addProjectile(Projectile projectile){
        Point3D curLocation = getPositionOf(projectile.getProjectileSource());
        projectile.setStartingPoint(curLocation);
        projectiles.add(new LocationTuple<Projectile>(curLocation, projectile));
    }

    public Point3D getPositionOf(Pilot pilot){
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getObject() == pilot) {
                return players.get(i).getLocation();
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getObject() == pilot) {
                return enemies.get(i).getLocation();
            }
        }

        return new Point3D(0,0,0);
    }

    public Player getPlayer(){
        return players.get(0).getObject();
    }

    //See if math is correct?
    public void updatePlayerPosition(Vector3D unitVector, Player player){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getObject() == player){

                Point3D curPosition = players.get(i).getLocation();
                float newX = curPosition.getX() + unitVector.getI()*(float)(player.getCurrentShipSpeed()/FRAMERATE);
                float newY = curPosition.getY() + unitVector.getJ()*(float)(player.getCurrentShipSpeed()/FRAMERATE);
                float newZ = curPosition.getZ() + unitVector.getK()*(float)(player.getCurrentShipSpeed()/FRAMERATE);

                Point3D newPosition = new Point3D(newX, newY, newZ);
                players.get(i).setLocation(newPosition);
                System.out.println("Player is currently at Position: " + newPosition.toString() + " With Speed: " + players.get(i).getObject().getCurrentShipSpeed());
                break;
            }
        }
    }

    public void updateEnemyPosition(Vector3D unitVector, Enemy enemy){
        for (int i = 0; i < enemies.size(); i++){
            if (enemies.get(i).getObject() == enemy){

                Point3D curPosition = enemies.get(i).getLocation();
                float newX = curPosition.getX() + unitVector.getI()*(float)(enemy.getCurrentShipSpeed()/FRAMERATE);
                float newY = curPosition.getY() + unitVector.getJ()*(float)(enemy.getCurrentShipSpeed()/FRAMERATE);
                float newZ = curPosition.getZ() + unitVector.getK()*(float)(enemy.getCurrentShipSpeed()/FRAMERATE);

                Point3D newPosition = new Point3D(newX, newY, newZ);
                enemies.get(i).setLocation(newPosition);
                break;
            }
        }
    }

    public void updateProjectilePosition(){
        for (int i = 0; i < projectiles.size(); i++){
            Projectile curProjectile = projectiles.get(i).getObject();
            Point3D curPosition = projectiles.get(i).getLocation();
            Point3D newPosition = curProjectile.move(curPosition);

            //If projectile is active, update its position, otherwise remove
            if (curProjectile.isActive(newPosition)){
                projectiles.get(i).setLocation(newPosition);
                System.out.println("Projectile " + curProjectile + " is currently at Position: " + newPosition.toString());
            }
            else {
                projectiles.remove(i);
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
