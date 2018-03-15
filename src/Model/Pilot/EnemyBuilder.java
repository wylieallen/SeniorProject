package Model.Pilot;

import Model.Map.LocationTuple;
import Model.Ship.ShipParts.*;
import Model.Ship.*;
import Model.Ship.ShipBuilder.ShipBuilder;
import Utility.Geom3D.Point3D;
import Utility.Rarity;
import Utility.Vector3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnemyBuilder {

    private ShipBuilder shipBuilder;

    public EnemyBuilder(){
        shipBuilder = new ShipBuilder();
    }

    public List<LocationTuple<Enemy>> buildEnemies(String filepath, String zoneid) {
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

        List<LocationTuple<Enemy>> enemies = new ArrayList<>();
        int lineIndex = 1;

        while (enemyData.get(lineIndex++) == "ENEMY"){
            Enemy newEnemy = new Enemy();

            lineIndex++; //SKIP LOCATION
            int x = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            int y = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            int z = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            Point3D enemyLoc = new Point3D(x,y,z);

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

            //BUILD SHIP

            lineIndex++; //SKIP SHIP

            lineIndex++; //SKIP HULL
            int hullCost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int health = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int inventorySize = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipHull newHull = shipBuilder.buildRandomHull(hullCost, health, inventorySize, enemyRarity);

            lineIndex++; //SKIP ENGINE
            int engineCost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int speed = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipEngine newEngine = shipBuilder.buildRandomEngine(engineCost, speed, enemyRarity);

            lineIndex++; //SKIP SHIELD
            int shieldCost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int shield = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipShield newShield = shipBuilder.buildRandomShield(shieldCost, shield, enemyRarity);

            lineIndex++; //SKIP SPECIAL
            int specialCost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int fuel = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipSpecial newSpecial = shipBuilder.buildRandomSpecial();

            lineIndex++; //SKIP WEAPON
            int weap1Cost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int value = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipWeapon newWeapon1 = shipBuilder.buildRandomWeapon(100,100,100,enemyRarity);

            lineIndex++; //SKIP WEAPON2
            int weap2Cost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int value2 = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipWeapon newWeapon2 = shipBuilder.buildRandomWeapon(100,100,100,enemyRarity);


            Ship newShip = shipBuilder.buildShip(newEnemy, newEngine, newHull, newShield, newSpecial, newWeapon1, newWeapon2);
            newEnemy.setActiveShip(newShip);
            newEnemy.getActiveShip().setFacingDirection(new Vector3D(enemyLoc, new Point3D(0,0,0)));
            enemies.add(new LocationTuple<Enemy>(enemyLoc, newEnemy));
        }
        return enemies;
    }

}
