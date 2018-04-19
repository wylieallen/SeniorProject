package Model.Map.Zones;

import Model.Items.Inventory;
import Model.Items.Item;
import Model.Items.LootChest;
import Model.Items.RandomItemGenerator;
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
import Utility.RandomNumberGenerator;
import Utility.Rarity;
import gameview.observers.spawn.SpawnObserver;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static Utility.Config.BATTLEZONE_SIZE;
import static Utility.Config.FRAMERATE;


public class BattleZone extends Zone implements CollisionObserver {

    private CollisionChecker collisionChecker;
    private Set<SpawnObserver> spawnObservers;

    private int zoneID;
    private String zoneType = "Battle Zone";

    private Body<Ship> player;
    private Set<Body<Ship>> ships;
    private Set<Body<Projectile>> projectiles;
    private Set<Body<LootChest>> lootChests;
    private Set<Body<Asteroid>> asteroids;


    RandomNumberGenerator rng = new RandomNumberGenerator();
    RandomItemGenerator rig = new RandomItemGenerator();
    private int lootChestCooldown = 0;
    private int asteroidCooldown = 0;
    private int enemyCooldown = 0;
    private int numAsteroid = 0;
    private List<LocationTuple<Powerup>> powerups;


    public BattleZone(int zoneID) {
        this.zoneID = zoneID;

        //Stuff from Gamemodel
        this.collisionChecker = new NaiveCollisionChecker();
        collisionChecker.add(this);
        this.spawnObservers = new HashSet<>();
        this.ships = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.projectiles = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.lootChests = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.asteroids = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public String getZoneType() {
        return zoneType;
    }

    public void run(Body<Ship> playerShip, int numAsteroid) {
        this.player = playerShip;
        spawnShip(player);
        this.numAsteroid = numAsteroid;
        //addEnemies();
    }


    public void addEnemies(int numberToGenerate) {

        EnemyBuilder newEnemyBuilder = new EnemyBuilder();
        //Set<Body<Ship>> enemies = (newEnemyBuilder.buildEnemies("resources/Zones/battlezone", Integer.toString(zoneID)));
        Set<Body<Ship>> enemies = newEnemyBuilder.buildEnemies(numberToGenerate);
        for (Body<Ship> enemy : enemies) {
            spawnShip(enemy);
        }

    }

    private void generateRandomEnemy() {
        if (enemyCooldown == 0){
            addEnemies(1);
            enemyCooldown = 10000;
        }
        enemyCooldown--;
    }

    public void addLootChest(){
        List<Item> items = new ArrayList<>();

        int numberItemsToGenerate = rng.getScaling();
        for (int i =0; i < numberItemsToGenerate; i++){
            items.add(rig.getRandomItem());
        }
        LootChest lootChest = new LootChest(items);

        Point3D location = rng.getRandomLocation();
        Body<LootChest> newLoot = new Body<>(location, new Dimension3D(1f, 1f, 1.0f), new Orientation3D(), lootChest);
        spawnLootChest(newLoot);
    }

    private void generateRandomChest() {
        if (lootChestCooldown == 0){
            addLootChest();
            lootChestCooldown = 1000;
        }
        lootChestCooldown--;
    }

    public void addAsteroid(int i){
        int x = rng.getRandomInBetween(-BATTLEZONE_SIZE,BATTLEZONE_SIZE);
        int y = rng.getRandomInBetween(-BATTLEZONE_SIZE,BATTLEZONE_SIZE);
        int z = rng.getRandomInBetween(BATTLEZONE_SIZE-i, BATTLEZONE_SIZE);
        Asteroid asteroid = new Asteroid(.1f, new Vector3D(0,0,-1), z+BATTLEZONE_SIZE);
        Body<Asteroid> newAsteroid = new Body<>(new Point3D(x,y,z), new Dimension3D(1f, 1f, 1f), new Orientation3D(), asteroid);
        spawnAsteroid(newAsteroid);
    }

    private void generateAsteroid(){
        if (asteroidCooldown == 0){
            for (int i = 0; i < .02*numAsteroid; i++){
                addAsteroid(100);
            }
            asteroidCooldown = 500;
        }
        asteroidCooldown--;
    }

    public void addProjectile(Projectile projectile) {
        Point3D curLocation = getPositionOf(projectile.getProjectileSource());
        projectile.setStartingPoint(curLocation);
        Body<Projectile> projectileLocation = new Body<Projectile>(curLocation,
                new Dimension3D(0.2f, 0.2f, 1.0f), new Orientation3D(), projectile);
        projectiles.add(projectileLocation);
    }


    public void setPlayer(Body<Ship> playerShip) {
        this.player = playerShip;
    }

    public void spawnPlayer(Body<Ship> playerShip) {
        setPlayer(playerShip);
        spawnShip(playerShip);
    }

    public void spawnShip(Body<Ship> ship) {
        ships.add(ship);
        spawnObservers.forEach(s -> s.notifyShipSpawn(ship));
    }

    public void spawnProjectile(Body<Projectile> projectile) {
        projectiles.add(projectile);
        updateProjectile(projectile);
        spawnObservers.forEach(s -> s.notifyProjSpawn(projectile));
    }

    public void spawnLootChest(Body<LootChest> lootChest) {
        lootChests.add(lootChest);
        spawnObservers.forEach(s -> s.notifyLootSpawn(lootChest));
    }

    public void spawnAsteroid(Body<Asteroid> asteroid) {
        asteroids.add(asteroid);
        updateAsteroid(asteroid);
        spawnObservers.forEach(s -> s.notifyAsteroidSpawn(asteroid));
    }

    public Point3D getPositionOf(Pilot pilot) {

        for (Body<Ship> currentShip : ships) {
            if (currentShip.get().getMyPilot() == pilot) {
                return currentShip.getCenter();
            }
        }

        return new Point3D(0, 0, 0);
    }


    public Player getPlayer() {
        return (Player) player.get().getMyPilot();
    }

    public Pilot getNearestHostileTo(Pilot pilot) {
        Point3D currentPosition = getPositionOf(pilot);

        float minDistance = Float.MAX_VALUE;
        Pilot nearestHostile = null;

        for (Body<Ship> currentShip : ships) {
            Pilot potentialHostile = currentShip.get().getMyPilot();
            Point3D hostilePosition = currentShip.getCenter();
            float distance = currentPosition.distance(currentPosition, hostilePosition);

            if (pilot.getFaction() != potentialHostile.getFaction() && distance <= currentShip.get().getDetectRange() && distance <= minDistance) {
                minDistance = distance;
                nearestHostile = potentialHostile;
            }
        }

        return nearestHostile;
    }

    public Point3D getNearestLootTo(Pilot pilot) {
        Point3D currentPosition = getPositionOf(pilot);

        float minDistance = Float.MAX_VALUE;
        Body<LootChest> nearestLoot = null;

        for (Body<LootChest> currentLoot : lootChests) {
            Body<LootChest> potentialLoot = currentLoot;
            Point3D lootPosition = potentialLoot.getCenter();

            float distance = currentPosition.distance(currentPosition, lootPosition);

            if (distance <= minDistance) {
                minDistance = distance;
                nearestLoot = potentialLoot;
            }
        }

        if (nearestLoot == null){
            return null;
        }

        return nearestLoot.getCenter();
    }


    public void enemyDestroyed(Enemy enemy) {
        for (Body<Ship> currentShip : ships) {
            if (currentShip.get().getMyPilot() == enemy) {
                List<Item> itemsToDrop = enemy.getActiveShip().getInventory().getItems();
                itemsToDrop.addAll(rng.getRandomEquippedParts(enemy.getActiveShip()));
                LootChest lootChest = new LootChest(itemsToDrop);
                if (!lootChest.isEmpty()){
                    Body<LootChest> newLoot = new Body<>(currentShip.getCenter(), new Dimension3D(1f, 1f, 1.0f), new Orientation3D(), lootChest);
                    spawnLootChest(newLoot);
                }
                ships.remove(currentShip);
                break;
            }
        }
    }

    //  COLLISION HANDLING
    public void notifyShipToShip(Body<Ship> a, Body<Ship> b) {

        float speedOfA = a.get().getSpeed();
        float speedOfB = b.get().getSpeed();

        //Determine who caused collision
        if (speedOfA < 0 && speedOfB < 0) {
            //A caused collision since A is reversing faster than B
            if (speedOfA <= speedOfB) {
                a.moveForward(-speedOfA);
                a.get().getShipStats().modifyCurrentSpeed(-speedOfA * 1.2);

                b.moveForward(speedOfB);
                b.get().getShipStats().modifyCurrentSpeed(speedOfB * 1.2);
            }
            //B caused Collision since B is reversing faster than A
            else {
                b.moveForward(-speedOfB);
                b.get().getShipStats().modifyCurrentSpeed(-speedOfB * 1.2);

                a.moveForward(speedOfA);
                a.get().getShipStats().modifyCurrentSpeed(speedOfA * 1.2);
            }
        }

        else{
            //A caused collision since A is moving faster than B
            if (speedOfA >= speedOfB){
                a.moveForward(-speedOfA);
                a.get().getShipStats().modifyCurrentSpeed(-speedOfA*1.2);

                b.moveForward(speedOfB);
                b.get().getShipStats().modifyCurrentSpeed(speedOfB*1.2);
            }
            //B caused Collision since B is moving faster than A
            else {
                b.moveForward(-speedOfB);
                b.get().getShipStats().modifyCurrentSpeed(-speedOfB*1.2);

                a.moveForward(speedOfA);
                a.get().getShipStats().modifyCurrentSpeed(speedOfA*1.2);
            }
        }

        a.get().takeDamage(10);
        b.get().takeDamage(10);

        System.out.println(a.toString() + " " + a.get().getShipStats().getCurrentHealth() + " " + a.get().getShipStats().getCurrentShield());
        System.out.println(b.toString() + " " + b.get().getShipStats().getCurrentHealth() + " " + b.get().getShipStats().getCurrentShield());
    }

    public void notifyProjToProj(Body<Projectile> a, Body<Projectile> b) {
        a.get().disable();
        b.get().disable();
    }

    public void notifyShipToProj(Body<Ship> ship, Body<Projectile> projectile) {
        if (ship.get() != projectile.get().getProjectileSource().getActiveShip()){
            ship.get().takeDamage((int) (projectile.get().getDamage()+ (projectile.get().getDamage() * projectile.get().getProjectileSource().getPilotStats().combatScaling())));
        }
        projectile.get().disable();
        if (!(ship.get().isAlive())){
            projectile.get().getProjectileSource().gainExperience(25);
        }
        System.out.println("Proj dmg: " + projectile.get().getDamage());
        System.out.println(ship.toString() + " " + ship.get().getShipStats().getCurrentHealth() + " " + ship.get().getShipStats().getCurrentShield());
    }

    public void notifyShipToLoot(Body<Ship> ship, Body<LootChest> lootChest) {
        Inventory shipInventory = ship.get().getInventory();
        shipInventory.addItems(lootChest.get().getItems());
        lootChest.get().disable();
        lootChests.remove(lootChest);
    }

    public void notifyAsteroidToShip(Body<Asteroid> asteroid, Body<Ship> ship){
        Ship theShip = ship.get();
        ship.get().takeDamage(0);
        asteroid.get().setTrajectory(ship.get().getFacingDirection());
        asteroid.get().setSpeed(ship.get().getSpeed()/FRAMERATE);
        theShip.getShipStats().modifyCurrentSpeed(-theShip.getSpeed());
    }

    public void add(SpawnObserver o) {
        spawnObservers.add(o);
    }

    public void add(CollisionObserver o) {
        collisionChecker.add(o);
    }

//  UPDATE METHODS

    public void update() {
        //generateRandomEnemy();
        generateRandomChest();
        generateAsteroid();
        collisionChecker.processCollisions(ships, projectiles, lootChests, asteroids);

        Set<Body<Ship>> expiredShips = new HashSet<>();

        for (Body<Ship> ship : ships) {
            if (!ship.get().isAlive()) {
                expiredShips.add(ship);
                continue;
            } else {
                updateShip(ship);
            }
        }

        ships.removeAll(expiredShips);

        Set<Body<Projectile>> expiredProjectiles = new HashSet<>();

        for (Body<Projectile> projectile : projectiles) {
            if (projectile.get().expired()) {
                expiredProjectiles.add(projectile);
                continue;
            } else {
                updateProjectile(projectile);
            }
        }

        projectiles.removeAll(expiredProjectiles);

        Set<Body<Asteroid>> expiredAsteroids = new HashSet<>();

        for (Body<Asteroid> asteroid : asteroids) {
            if (asteroid.get().expired()) {
                expiredAsteroids.add(asteroid);
                continue;
            } else {
                updateAsteroid(asteroid);
            }
        }

        asteroids.removeAll(expiredAsteroids);

    }

    private void updateShip(Body<Ship> body) {
        Ship ship = body.get();
        ship.update();
        boolean directionUpdate = false;
        boolean rollingLeft = ship.getRollingLeft(), rollingRight = ship.getRollingRight(),
                pitchingUp = ship.getPitchingUp(), pitchingDown = ship.getPitchingDown(),
                yawingLeft = ship.getYawingLeft(), yawingRight = ship.getYawingRight();

        if (rollingLeft ^ rollingRight) {
            float rollSpeed = ship.getRollSpeed();
            body.adjustRoll(rollingRight ? rollSpeed : -rollSpeed);
            directionUpdate = true;
        }
        if (pitchingDown ^ pitchingUp) {
            float pitchSpeed = ship.getPitchSpeed();
            body.adjustPitch(pitchingUp ? -pitchSpeed : pitchSpeed);
            directionUpdate = true;
        }
        if (yawingLeft ^ yawingRight) {
            float yawSpeed = ship.getYawSpeed();
            body.adjustYaw(yawingRight ? yawSpeed : -yawSpeed);
            directionUpdate = true;
        }

        if (directionUpdate) {
            float yawRads = (body.getOrientation().getYaw() / 180.0f) * 3.1415926535f;
            float pitchRads = (-body.getOrientation().getPitch() / 180.0f) * 3.1415926535f;

            float i = (float) ((Math.cos(pitchRads) * Math.sin(yawRads)));
            float j = (float) Math.sin(pitchRads);
            float k = (float) (Math.cos(pitchRads) * Math.cos(yawRads));
            Vector3D facingDirection = new Vector3D(i, j, -k);
            body.get().setFacingDirection(facingDirection);
        }

        if (ship.isAccelerating()) {
            ship.accelerate();
        }
        if (ship.isDecelerating()) {
            ship.decelerate();
        }
        if (ship.isBraking()) {
            ship.brake();
        }

        //body.getCollidable().moveForward(body.get().getSpeed()/FRAMERATE);
        updateShipPosition(body);

        if (ship.isFiring1()) {
            Collection<Projectile> projectiles = ship.useWeapon1();
            for (Projectile projectile : projectiles) {
                Body<Projectile> projBody = new Body<Projectile>(
                        new Point3D(body.getCenter()), new Dimension3D(.2f),
                        new Orientation3D(body.getOrientation()), projectile);
                projBody.moveForward(body.getSize().getLength()/2);
                spawnProjectile(projBody);
            }
        }

        if (ship.isFiring2()) {
            Collection<Projectile> projectiles = ship.useWeapon2();
            for (Projectile projectile : projectiles) {
                Body<Projectile> projBody = new Body<Projectile>(
                        new Point3D(body.getCenter()), new Dimension3D(.2f),
                        new Orientation3D(body.getOrientation()), projectile);
                projBody.moveForward(body.getSize().getLength()/2);
                spawnProjectile(projBody);
            }
        }
    }


    private void updateShipPosition(Body<Ship> currentShip) {

        Pilot currentPilot = currentShip.get().getMyPilot();
        Point3D curPosition = currentShip.getOrigin();

        //TEST (Attempt to change pitch/yaw from vector to update AI
       if (currentPilot.move(curPosition))
       {
           Vector3D facingDirection = currentShip.get().getFacingDirection();
           float pitchRads = (float) Math.asin(facingDirection.getJ());
           float yawRads = (float) Math.atan2(facingDirection.getI(),-facingDirection.getK());
           currentShip.setOrientation((pitchRads/3.1415926535f)*-180.0f, (yawRads/3.1415926535f)*180.0f);
        }

        Vector3D curTrajectory = currentShip.get().getFacingDirection();
        double curSpeed = currentShip.get().getSpeed() / FRAMERATE;

        Point3D newPosition = new Point3D(curTrajectory, curPosition, curSpeed);
        currentShip.move(newPosition);

        // System.out.println("Player is currently at Position: " + newPosition.toString() + " With Speed: " + players.get(i).getObject().getCurrentShipSpeed());
        // System.out.println("Facing Direction: I:" + currentPlayer.getShipDirection().getI() + " J: " + currentPlayer.getShipDirection().getJ() + " K: " + currentPlayer.getShipDirection().getK());
    }


    private void updateProjectile(Body<Projectile> projectile) {
        projectile.get().update();
        //projectile.getCollidable().moveForward(projectile.get().getSpeed());
        updateProjectilePosition(projectile);

    }

    private void updateProjectilePosition(Body<Projectile> currentProjectile) {
        Projectile curProjectile = currentProjectile.get();
        Point3D curPosition = currentProjectile.getOrigin();

        curProjectile.move(curPosition);

        Vector3D curTrajectory = curProjectile.getTrajectory();
        double curSpeed = curProjectile.getSpeed();

        Point3D newPosition = new Point3D(curTrajectory, curPosition, curSpeed);
        currentProjectile.move(newPosition);
        //System.out.println("Projectile " + curProjectile + " is currently at Position: " + newPosition.toString());
    }

    private void updateAsteroid(Body<Asteroid> asteroid){
        asteroid.get().update();
        updateAsteroidPosition(asteroid);
    }

    private void updateAsteroidPosition(Body<Asteroid> currentAsteroid){
        Asteroid curAsteroid = currentAsteroid.get();
        Point3D curPosition = currentAsteroid.getOrigin();

        Vector3D curTrajectory = curAsteroid.getTrajectory();
        double curSpeed = curAsteroid.getSpeed();

        Point3D newPosition = new Point3D(curTrajectory, curPosition, curSpeed);
        currentAsteroid.move(newPosition);
    }


    //  END UPDATE METHODS

    public Body<Ship> getPlayerShip() {
        return player;
    }

    public boolean isGameOver(){
        if (!player.get().isAlive()){
            return true;
        }
        for (Body<Ship> ship: ships){
            if (ship.get().getMyPilot().getFaction() != player.get().getMyPilot().getFaction()){
                return false;
            }
        }
        return true;
    }
}
