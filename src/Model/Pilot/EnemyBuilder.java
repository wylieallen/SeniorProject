package Model.Pilot;

import Model.Map.LocationTuple;
import Model.Ship.ShipParts.*;
import Model.Ship.*;
import Model.Ship.ShipBuilder.ShipBuilder;
import Model.physics.Body;
import Model.physics.collidable.BoundingBoxCollidable;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.RandomNumberGenerator;
import Utility.Rarity;
import Utility.Geom3D.Vector3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EnemyBuilder {

    private ShipBuilder shipBuilder;
    private RandomNumberGenerator rng;

    public EnemyBuilder(){
        shipBuilder = new ShipBuilder();
        rng = new RandomNumberGenerator();
    }

    public Set<Body<Ship>> buildEnemies(int numberToGenerate, Faction faction){
        Set<Body<Ship>> enemies = new HashSet<>();
        for (int i = 0; i < numberToGenerate; i++){
            Enemy newEnemy = new Enemy(0);

            //Decide Rarity
            Rarity enemyRarity = rng.getRandomRarity();

            //Decide Faction
            //Faction enemyFaction = rng.getRandomFaction();
            newEnemy.setFaction(faction);

            //Decide Location
            Point3D enemyLoc = rng.getRandomLocation();

            //BUILD SHIP RANDOMLY!!!
            Ship newShip = shipBuilder.buildRandomShip(newEnemy, enemyRarity);
            newEnemy.setActiveShip(newShip);
            newEnemy.getActiveShip().setFacingDirection(new Vector3D(enemyLoc, new Point3D(0,0,0)));
            enemies.add(new Body<>(enemyLoc, new Dimension3D(7.086f, 1.323f, 12.380f),
                    new Orientation3D(), newShip));
        }
        return enemies;
    }

    public Set<Body<Ship>> buildEnemies(String filepath, String zoneid) {
        String filename = filepath + zoneid + "/enemies.txt";

        Scanner s = null;
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> enemyData = new ArrayList<>();
        while (s.hasNextLine()){
            enemyData.add(s.nextLine());
        }

        Set<Body<Ship>> enemies = new HashSet<>();
        int lineIndex = 1;

        while (enemyData.get(lineIndex++).equals("ENEMY")){
            Enemy newEnemy = new Enemy(0);

            lineIndex++; //SKIP LOCATION
            int x = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            int y = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            int z = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            Point3D enemyLoc = new Point3D(x,y,z);

            lineIndex++; //SKIP FACTION
            String faction = enemyData.get(lineIndex++).split("\t\t")[1];
            Faction enemyFaction;
            switch (faction){
                case "REBEL":
                    enemyFaction = Faction.REBEL;
                    break;
                case "ALLY":
                    enemyFaction = Faction.ALLY;
                    break;
                default:
                    enemyFaction = Faction.NEUTRAL;
                    break;
            }
            newEnemy.setFaction(enemyFaction);


            lineIndex++; //SKIP RARITY
            String rarity = enemyData.get(lineIndex++).split("\t\t")[1];
            Rarity enemyRarity;
            switch (rarity){
                case "COMMON":
                    enemyRarity = Rarity.COMMON;
                    break;
                case "RARE":
                    enemyRarity = Rarity.RARE;
                    break;
                case "EPIC":
                    enemyRarity = Rarity.EPIC;
                    break;
                case "LEGENDARY":
                    enemyRarity = Rarity.LEGENDARY;
                    break;
                default:
                    enemyRarity = Rarity.COMMON;
                    break;
            }

            //BUILD SHIP RANDOMLY!!!
            Ship newShip = shipBuilder.buildRandomShip(newEnemy, enemyRarity);
            newEnemy.setActiveShip(newShip);
            newEnemy.getActiveShip().setFacingDirection(new Vector3D(enemyLoc, new Point3D(0,0,0)));
            enemies.add(new Body<>(enemyLoc, new Dimension3D(7.086f, 1.323f, 12.380f),
                    new Orientation3D(), newShip));
        }
        return enemies;
    }

}
