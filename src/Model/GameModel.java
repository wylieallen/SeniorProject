package Model;

import Model.Map.Node;
import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Pilot.Enemy;
import Model.Pilot.Faction;
import Model.Pilot.Player;
import Model.Ship.Ship;
import Model.Ship.ShipBuilder.ShipBuilder;
import Model.Ship.ShipParts.Projectile.LinearProjectile;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.Ship.ShipParts.ShipEngine;
import Model.Ship.ShipParts.ShipHull;
import Model.physics.Body;
import Model.physics.CollisionObserver;
import Model.physics.collidable.BoundingBoxCollidable;
import Model.physics.collidable.Collidable;
import Model.physics.checking.CollisionChecker;
import Model.physics.checking.NaiveCollisionChecker;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import Utility.Rarity;
import gameview.observers.spawn.SpawnObserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameModel {
    private Body<Ship> playerShip;
    private BattleZone myBattleZone;
    private int zoneID;
    private int numEnemy;
    private int numLootChest;
    private int numAsteroid;

    public GameModel(Player player, int battlezoneID) {
        //INITIALIZE GAME STUFF
        this.zoneID = battlezoneID;
        Point3D origin = new Point3D(0f, 0f, 0f);
        this.playerShip = new Body<>(origin, new Dimension3D(7.086f, 1.323f, 12.380f), new Orientation3D(), player.getActiveShip());
        parseZoneID();
    }


    private void parseZoneID() {
        String filename = "resources/Zones/battlezones/zone" + Integer.toString(zoneID) + ".txt";

        Scanner s = null;
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> enemyData = new ArrayList<>();
        while (s.hasNextLine()) {
            enemyData.add(s.nextLine());
        }

        int lineIndex = 1;
        lineIndex++; //SKIP ENEMY
        numEnemy = Integer.parseInt(enemyData.get(lineIndex++).split("\t")[1]);

        lineIndex++; //SKIP LOOTCHEST
        numLootChest = Integer.parseInt(enemyData.get(lineIndex++).split("\t")[1]);

        lineIndex++; //SKIP ASTEROID
        numAsteroid = Integer.parseInt(enemyData.get(lineIndex++).split("\t")[1]);
    }


    public void run() {
        Overworld theOverworld = Overworld.getOverworld();
        theOverworld.addNode(new Node(new BattleZone(zoneID)));
        myBattleZone = (BattleZone) theOverworld.getZoneAtNode();
        myBattleZone.run(playerShip, numAsteroid);
    }

    public void spawnEnemies() {
        myBattleZone.addEnemies(numEnemy);
    }

    public void spawnLootChests() {
        for (int i = 0; i < numLootChest; i++) {
            myBattleZone.addLootChest();
        }
    }

    public void spawnAsteroids() {
        for (int i = 0; i < numAsteroid; i++) {
            myBattleZone.addAsteroid(1000);
        }
    }

    public boolean gameIsOver()
    {
        return (myBattleZone.isGameOver());
    }

    public void update() {
        myBattleZone.update();
    }

    public void add(SpawnObserver o) {
        myBattleZone.add(o);
    }

    public void add(CollisionObserver o) {
        myBattleZone.add(o);
    }

    public void setPlayer(Body<Ship> playerShip) {
        this.playerShip = playerShip;
    }

    public Body<Ship> getPlayerShip() {
        return playerShip;
    }
}
