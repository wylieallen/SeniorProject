package Model.Pilot;

import Model.Map.LocationTuple;
import Model.Ship.ShipParts.*;
import Model.Ship.*;
import Utility.Point3D;

import javax.swing.text.html.parser.Entity;
import javax.xml.stream.Location;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnemyBuilder {

    public EnemyBuilder(){

    }

    public List<LocationTuple<Enemy>> buildEnemies(String filepath, String zoneid) throws FileNotFoundException {
        String filename = filepath + zoneid + "/enemies.txt";

        Scanner s = new Scanner(new File(filename));
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

            lineIndex++; //SKIP SHIP
            int hullCost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            int health = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            int inventory = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t")[1]);
            ShipHull newHull = new ShipHull(hullCost, health, inventory);
            Ship newShip = new Ship(newEnemy, newHull);

            lineIndex++; //SKIP ENGINE
            int engineCost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int speed = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipEngine newEngine = new ShipEngine(engineCost, speed);
            newShip.equipEngine(newEngine);

            lineIndex++; //SKIP SHIELD
            int shieldCost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int shield = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipShield newShield = new ShipShield(shieldCost, shield);
            newShip.equipShield(newShield);

            lineIndex++; //SKIP SPECIAL
            int specialCost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int fuel = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipSpecial newSpecial = new ShipSpecial(specialCost, fuel);
            newShip.equipSpeical(newSpecial);

            lineIndex++; //SKIP WEAPON
            int weap1Cost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int value = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipWeapon newWeapon1 = new ShipWeapon(weap1Cost, value);
            newShip.equipWeapon1(newWeapon1);

            lineIndex++; //SKIP WEAPON2
            int weap2Cost = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            int value2 = Integer.parseInt(enemyData.get(lineIndex++).split("\t\t\t")[1]);
            ShipWeapon newWeapon2 = new ShipWeapon(weap2Cost, value2);
            newShip.equipWeapon1(newWeapon2);

            newEnemy.setActiveShip(newShip);
            enemies.add(new LocationTuple<Enemy>(enemyLoc, newEnemy));
        }
        return enemies;
    }

}
