package Model.Map.Zones;

import Model.Items.LootChest;
import Model.Map.LocationTuple;
import Model.Pilot.*;
import Model.Powerup.Powerup;
import Model.Ship.ShipParts.Projectile.Projectile;
import Utility.Geom3D.Point3D;


import java.util.ArrayList;
import java.util.List;


public class BattleZone extends Zone {

    private int zoneID;

    private LocationTuple<Pilot> player;
    private List<LocationTuple<Pilot>> pilots = new ArrayList<>();
    private List<LocationTuple<Projectile>> projectiles = new ArrayList<>();

    private List<LocationTuple<Powerup>> powerups;
    private List<LocationTuple<LootChest>> loot;


    public BattleZone(int zoneID) {
        this.zoneID = zoneID;
    }

    public void run(Player player) {
        Point3D origin = new Point3D(0f, 0f, 0f);
        addPlayer(origin, player);
        addPilots();
    }

    public void update() {
        updateProjectilePosition();
        updatePilotPositions();
    }


    public void addPlayer(Point3D location, Player player) {
        this.player = new LocationTuple<Pilot>(location, player);
        pilots.add(this.player);
    }

    public void addPilots() {
        EnemyBuilder newEnemyBuilder = new EnemyBuilder();
        pilots.addAll(newEnemyBuilder.buildEnemies("resources/Zones/battlezone", Integer.toString(zoneID)));
    }

    public void addProjectile(Projectile projectile) {
        Point3D curLocation = getPositionOf(projectile.getProjectileSource());
        projectile.setStartingPoint(curLocation);
        LocationTuple<Projectile> projectileLocation = new LocationTuple<Projectile>(curLocation, projectile);
        projectiles.add(projectileLocation);
    }

    public Point3D getPositionOf(Pilot pilot) {
        for (int i = 0; i < pilots.size(); i++) {
            if (pilots.get(i).getObject() == pilot) {
                return pilots.get(i).getLocation();
            }
        }

        return new Point3D(0, 0, 0);
    }

    public Player getPlayer() {
        return (Player) player.getObject();
    }

    public Pilot getNearestHostileTo(Pilot pilot){
        Point3D currentPosition = getPositionOf(pilot);

        float minDistance = Float.MAX_VALUE;
        Pilot nearestHostile = null;

        for (int i = 0; i < pilots.size(); i++){
            Pilot potentialHostile = pilots.get(i).getObject();
            Point3D hostilePosition = pilots.get(i).getLocation();
            float distance = currentPosition.distance(currentPosition, hostilePosition);

            if (pilot.getFaction() != potentialHostile.getFaction() && distance <= potentialHostile.getDetectRange() && distance <= minDistance)
            {
                minDistance = distance;
                nearestHostile = potentialHostile;
            }
        }
        return nearestHostile;
    }


    public void updatePilotPositions() {
        for (int i = 0; i < pilots.size(); i++) {
            Pilot currentPilot = pilots.get(i).getObject();
            Point3D curPosition = pilots.get(i).getLocation();
            Point3D newPosition = currentPilot.move(curPosition);
            pilots.get(i).setLocation(newPosition);
           // System.out.println("Player is currently at Position: " + newPosition.toString() + " With Speed: " + players.get(i).getObject().getCurrentShipSpeed());
           // System.out.println("Facing Direction: I:" + currentPlayer.getShipDirection().getI() + " J: " + currentPlayer.getShipDirection().getJ() + " K: " + currentPlayer.getShipDirection().getK());
        }
    }

    public void updateProjectilePosition() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile curProjectile = projectiles.get(i).getObject();
            Point3D curPosition = projectiles.get(i).getLocation();
            Point3D newPosition = curProjectile.move(curPosition);

            //If projectile is active, update its position, otherwise remove
            if (curProjectile.isActive(newPosition)) {
                projectiles.get(i).setLocation(newPosition);
                System.out.println("Projectile " + curProjectile + " is currently at Position: " + newPosition.toString());
            } else {
                projectiles.remove(i);
            }
        }
    }


    public void enemyDestroyed(Enemy enemy) {
        for (int i = 0; i < pilots.size(); i++) {
            if (pilots.get(i).getObject() == enemy) {
                LocationTuple<Pilot> deadEnemy = pilots.remove(i);
                addLootChest(deadEnemy.getLocation());
                break;
            }
        }
    }

    //TODO Modify lootchest to instantiate with cargo from dead enemy
    public void addLootChest(Point3D location) {
        LootChest newLootChest = new LootChest();

    }

}
