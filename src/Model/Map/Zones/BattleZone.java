package Model.Map.Zones;

import Model.Items.LootChest;
import Model.Map.LocationTuple;
import Model.Pilot.*;
import Model.Powerup.Powerup;
import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;
import Model.physics.collidable.BoundingBoxCollidable;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Utility.Config.FRAMERATE;


public class BattleZone extends Zone {

    private int zoneID;

    private Body<Ship> player;
    private Set<Body<Ship>> ships = new HashSet<>();
    private Set<Body<Projectile>> projectiles = new HashSet<>();

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
        this.player = new Body<Ship>(new BoundingBoxCollidable(location, new Dimension3D(0.2f, 0.2f, 1.0f)), player.getActiveShip());
        ships.add(this.player);
    }

    public void addPilots() {
        EnemyBuilder newEnemyBuilder = new EnemyBuilder();
        ships.addAll(newEnemyBuilder.buildEnemies("resources/Zones/battlezone", Integer.toString(zoneID)));
    }

    public void addProjectile(Projectile projectile) {
        Point3D curLocation = getPositionOf(projectile.getProjectileSource());
        projectile.setStartingPoint(curLocation);
        Body<Projectile> projectileLocation = new Body<Projectile>(new BoundingBoxCollidable(curLocation, new Dimension3D(0.2f, 0.2f, 1.0f)), projectile);
        projectiles.add(projectileLocation);
    }

    public Point3D getPositionOf(Pilot pilot) {

        for(Body<Ship> currentShip : ships) {
            if (currentShip.get().getMyPilot() == pilot) {
                return currentShip.getCenter();
            }
        }

        return new Point3D(0, 0, 0);
    }

    public Player getPlayer() {
        return (Player) player.get().getMyPilot();
    }

    public Pilot getNearestHostileTo(Pilot pilot){
        Point3D currentPosition = getPositionOf(pilot);

        float minDistance = Float.MAX_VALUE;
        Pilot nearestHostile = null;

        for(Body<Ship> currentShip : ships) {
            Pilot potentialHostile = currentShip.get().getMyPilot();
            Point3D hostilePosition = currentShip.getCenter();
            float distance = currentPosition.distance(currentPosition, hostilePosition);

            if (pilot.getFaction() != potentialHostile.getFaction() && distance <= currentShip.get().getDetectRange() && distance <= minDistance)
            {
                minDistance = distance;
                nearestHostile = potentialHostile;
            }
        }

        return nearestHostile;
    }


    public void updatePilotPositions() {

        for(Body<Ship> currentShip : ships) {
            Pilot currentPilot = currentShip.get().getMyPilot();
            Point3D curPosition = currentShip.getCenter();
            currentPilot.move(curPosition);


            Vector3D curTrajectory = currentShip.get().getFacingDirection();
            double curSpeed = currentShip.get().getSpeed();

            float newX = curPosition.getX() + curTrajectory.getI()*(float)(curSpeed/FRAMERATE);
            float newY = curPosition.getY() + curTrajectory.getJ()*(float)(curSpeed/FRAMERATE);
            float newZ = curPosition.getZ() + curTrajectory.getK()*(float)(curSpeed/FRAMERATE);

            Point3D newPosition = new Point3D(newX, newY, newZ);

            //TODO call collidable move
            //pilots.get(i).setLocation(newPosition);


            // System.out.println("Player is currently at Position: " + newPosition.toString() + " With Speed: " + players.get(i).getObject().getCurrentShipSpeed());
            // System.out.println("Facing Direction: I:" + currentPlayer.getShipDirection().getI() + " J: " + currentPlayer.getShipDirection().getJ() + " K: " + currentPlayer.getShipDirection().getK());
        }
    }

    public void updateProjectilePosition() {


        for(Body<Projectile> currentProjectile : projectiles) {
            Projectile curProjectile = currentProjectile.get();
            Point3D curPosition = currentProjectile.getCenter();
            Point3D newPosition = curProjectile.move(curPosition);

            //If projectile is active, update its position, otherwise remove
            if (curProjectile.isActive(newPosition)) {

                //TODO call move in collidable
                //projectiles.get(i).setLocation(newPosition);
                System.out.println("Projectile " + curProjectile + " is currently at Position: " + newPosition.toString());
            } else {
                projectiles.remove(currentProjectile);
            }
        }

    }

    public void enemyDestroyed(Enemy enemy) {
        for(Body<Ship> currentShip : ships) {
            if (currentShip.get().getMyPilot() == enemy) {
                addLootChest(currentShip.getCenter());
                ships.remove(currentShip);
                break;
            }
        }
    }

    //TODO Modify lootchest to instantiate with cargo from dead enemy
    public void addLootChest(Point3D location) {
        LootChest newLootChest = new LootChest();

    }

}
