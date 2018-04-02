package Model.Map.Zones;

import Model.Items.LootChest;
import Model.Map.LocationTuple;
import Model.Pilot.*;
import Model.Powerup.Powerup;
import Model.Ship.Ship;
import Model.Ship.ShipBuilder.ShipBuilder;
import Model.Ship.ShipParts.Projectile.LinearProjectile;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;
import Model.physics.CollisionObserver;
import Model.physics.checking.CollisionChecker;
import Model.physics.checking.NaiveCollisionChecker;
import Model.physics.collidable.BoundingBoxCollidable;
import Model.physics.collidable.Collidable;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import Utility.Rarity;
import gameview.observers.spawn.SpawnObserver;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static Utility.Config.FRAMERATE;


public class BattleZone extends Zone implements CollisionObserver {

    private CollisionChecker collisionChecker;
    private Set<SpawnObserver> spawnObservers;

    private int zoneID;
    private String zoneType = "Battle Zone";

    private Body<Ship> player;
    private Set<Body<Ship>> ships;
    private Set<Body<Projectile>> projectiles;

    private List<LocationTuple<Powerup>> powerups;
    private List<LocationTuple<LootChest>> loot;


    public BattleZone(int zoneID) {
        this.zoneID = zoneID;

        //Stuff from Gamemodel
        this.collisionChecker = new NaiveCollisionChecker();
        collisionChecker.add(this);
        this.spawnObservers = new HashSet<>();
        this.ships = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.projectiles = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public String getZoneType() {return zoneType;}

    public void run(Body<Ship> playerShip) {
        this.player = playerShip;
        spawnShip(player);
        //addEnemies();
    }


    public void addEnemies() {
        EnemyBuilder newEnemyBuilder = new EnemyBuilder();
        Set<Body<Ship>> enemies = (newEnemyBuilder.buildEnemies("resources/Zones/battlezone", Integer.toString(zoneID)));
        for (Body<Ship> enemy : enemies){
            spawnShip(enemy);
        }
    }

    public void addProjectile(Projectile projectile) {
        Point3D curLocation = getPositionOf(projectile.getProjectileSource());
        projectile.setStartingPoint(curLocation);
        Body<Projectile> projectileLocation = new Body<Projectile>(new BoundingBoxCollidable(curLocation, new Dimension3D(0.2f, 0.2f, 1.0f)), projectile);
        projectiles.add(projectileLocation);
    }


    public void setPlayer(Body<Ship> playerShip)
    {
        this.player = playerShip;
    }

    public void spawnPlayer(Body<Ship> playerShip)
    {
        setPlayer(playerShip);
        spawnShip(playerShip);
    }

    public void spawnShip(Body<Ship> ship)
    {
        ships.add(ship);
        spawnObservers.forEach(s -> s.notifyShipSpawn(ship));
    }

    public void spawnProjectile(Body<Projectile> projectile)
    {
        projectiles.add(projectile);
        updateProjectile(projectile);
        spawnObservers.forEach(s -> s.notifyProjSpawn(projectile));
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

    //  COLLISION HANDLING
    public void notifyShipToShip(Body<Ship> a, Body<Ship> b)
    {
        a.get().takeDamage(2);
        b.get().takeDamage(2);
        System.out.println(a.toString() + " " + a.get().getShipStats().getCurrentHealth() + " " + a.get().getShipStats().getCurrentShield());
        System.out.println(b.toString() + " " + b.get().getShipStats().getCurrentHealth() + " " + b.get().getShipStats().getCurrentShield());
    }

    public void notifyProjToProj(Body<Projectile> a, Body<Projectile> b)
    {
        a.get().disable();
        b.get().disable();
    }

    public void notifyShipToProj(Body<Ship> ship, Body<Projectile> projectile)
    {
        ship.get().takeDamage(projectile.get().getDamage());
        projectile.get().disable();
        System.out.println("Proj dmg: " + projectile.get().getDamage());
        System.out.println(ship.toString() + " " + ship.get().getShipStats().getCurrentHealth() + " " + ship.get().getShipStats().getCurrentShield() );
    }

    public void add(SpawnObserver o) { spawnObservers.add(o); }
    public void add(CollisionObserver o) { collisionChecker.add(o); }

//  UPDATE METHODS

    public void update() {
        collisionChecker.processCollisions(ships, projectiles);

        Set<Body<Ship>> expiredShips = new HashSet<>();

        for(Body<Ship> ship : ships)
        {
            if(!ship.get().isAlive())
            {
                expiredShips.add(ship);
                continue;
            }
            else
            {
                updateShip(ship);
            }
        }

        ships.removeAll(expiredShips);

        Set<Body<Projectile>> expiredProjectiles = new HashSet<>();

        for(Body<Projectile> projectile : projectiles)
        {
            if(projectile.get().expired())
            {
                expiredProjectiles.add(projectile);
                continue;
            }
            else
            {
                updateProjectile(projectile);
            }
        }

        projectiles.removeAll(expiredProjectiles);

    }

    private void updateShip(Body<Ship> body)
    {
        Ship ship = body.get();
        Collidable collidable = body.getCollidable();
        ship.update();
        boolean directionUpdate = false;
        boolean rollingLeft = ship.getRollingLeft(), rollingRight = ship.getRollingRight(),
                pitchingUp = ship.getPitchingUp(), pitchingDown = ship.getPitchingDown(),
                yawingLeft = ship.getYawingLeft(), yawingRight = ship.getYawingRight();

        if(rollingLeft ^ rollingRight)
        {
            float rollSpeed = ship.getRollSpeed();
            collidable.adjustRoll(rollingRight ? rollSpeed : -rollSpeed);
            directionUpdate = true;
        }
        if(pitchingDown ^ pitchingUp)
        {
            float pitchSpeed = ship.getPitchSpeed();
            collidable.adjustPitch(pitchingUp ? -pitchSpeed : pitchSpeed);
            directionUpdate = true;
        }
        if(yawingLeft ^ yawingRight)
        {
            float yawSpeed = ship.getYawSpeed();
            collidable.adjustYaw(yawingRight ? yawSpeed : -yawSpeed);
            directionUpdate = true;
        }

        if (directionUpdate){
            float yawRads = body.getCollidable().getOrientation().getYaw()/180.0f * 3.1415926535f;
            float pitchRads = -body.getCollidable().getOrientation().getPitch()/180.0f * 3.1415926535f;

            float i = (float) ((Math.cos(pitchRads) * Math.sin(yawRads)));
            float j = (float) Math.sin(pitchRads);
            float k = (float) (Math.cos(pitchRads) * Math.cos(yawRads));
            Vector3D facingDirection = new Vector3D(i,j,-k);
            body.get().setFacingDirection(facingDirection);
        }

        if(ship.isAccelerating())
        {
            ship.accelerate();
        }
        if(ship.isDecelerating())
        {
            ship.decelerate();
        }
        if(ship.isBraking()){
            ship.brake();
        }


        //TEST (Attempt to change pitch/yaw from vector to update AI
/*        if (ship.getMyPilot().move(body.getCollidable().getCenter())){ss

            float pitchRads = (float) Math.asin(ship.getFacingDirection().getJ());
            float newPitch = -pitchRads/3.1415926535f * 180.0f;
            body.getCollidable().getOrientation().setPitch(newPitch);


            float yawRadsI = (float) Math.asin(ship.getFacingDirection().getI()/Math.cos(pitchRads));
            float newYawI = yawRadsI/3.1415926535f * 180.0f;

            float yawRadsK = (float) Math.acos(-ship.getFacingDirection().getK()/Math.cos(pitchRads));
            float newYawK = yawRadsK/3.1415926535f * 180.0f;


            if (body.getCollidable().getOrientation().getYaw() == newYawI ){
                body.getCollidable().getOrientation().setYaw(newYawK);
            }
            else{
                body.getCollidable().getOrientation().setYaw(newYawI);
            }
        }*/


        //body.getCollidable().moveForward(body.get().getSpeed()/FRAMERATE);
        updateShipPosition(body);

        if(ship.isFiring1())
        {
            Collection<Projectile> projectiles = ship.useWeapon1();
            for(Projectile projectile : projectiles) {
                Body<Projectile> projBody = new Body<Projectile>(
                        new BoundingBoxCollidable(new Point3D(body.getCollidable().getRear()), new Dimension3D(.2f),
                                new Orientation3D(body.getCollidable().getOrientation())), projectile);
                projBody.getCollidable().moveForward(body.getCollidable().getSize().getLength());
                spawnProjectile(projBody);
            }
        }

        if(ship.isFiring2())
        {

        }
    }


    public void updateShipPosition(Body<Ship> currentShip) {

        Pilot currentPilot = currentShip.get().getMyPilot();
        Point3D curPosition = currentShip.getCollidable().getOrigin();
        currentPilot.move(curPosition);

        Vector3D curTrajectory = currentShip.get().getFacingDirection();
        double curSpeed = currentShip.get().getSpeed()/FRAMERATE;

        float newX = curPosition.getX() + curTrajectory.getI()*(float)(curSpeed);
        float newY = curPosition.getY() + curTrajectory.getJ()*(float)(curSpeed);
        float newZ = curPosition.getZ() + curTrajectory.getK()*(float)(curSpeed);

        Point3D newPosition = new Point3D(newX, newY, newZ);

        currentShip.getCollidable().move(newPosition);

        // System.out.println("Player is currently at Position: " + newPosition.toString() + " With Speed: " + players.get(i).getObject().getCurrentShipSpeed());
        // System.out.println("Facing Direction: I:" + currentPlayer.getShipDirection().getI() + " J: " + currentPlayer.getShipDirection().getJ() + " K: " + currentPlayer.getShipDirection().getK());
    }


    private void updateProjectile(Body<Projectile> projectile)
    {
        projectile.get().update();
        //projectile.getCollidable().moveForward(projectile.get().getSpeed());
       updateProjectilePosition(projectile);

    }

    public void updateProjectilePosition(Body<Projectile> currentProjectile) {
        Projectile curProjectile = currentProjectile.get();
        Point3D curPosition = currentProjectile.getCollidable().getOrigin();
        curProjectile.move(curPosition);

        Vector3D curTrajectory = curProjectile.getTrajectory();
        double curSpeed = curProjectile.getSpeed();

        float newX = curPosition.getX() + curTrajectory.getI()*(float)(curSpeed);
        float newY = curPosition.getY() + curTrajectory.getJ()*(float)(curSpeed);
        float newZ = curPosition.getZ() + curTrajectory.getK()*(float)(curSpeed);

        Point3D newPosition = new Point3D(newX, newY, newZ);
        currentProjectile.getCollidable().move(newPosition);
        //System.out.println("Projectile " + curProjectile + " is currently at Position: " + newPosition.toString());
    }

    //  END UPDATE METHODS

    public Body<Ship> getPlayerShip() { return player; }
}
