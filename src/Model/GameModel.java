package Model;

import Model.Map.Node;
import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GameModel
{
    private Body<Ship> playerShip;
    private BattleZone myBattleZone;

    public GameModel()
    {
        //INITIALIZE GAME STUFF
        Player newPlayer = new Player();
        ShipBuilder buildShip = new ShipBuilder();
        Ship myShip = buildShip.buildRandomShip(newPlayer, Rarity.COMMON);
        newPlayer.setActiveShip(myShip);
        myShip.setFacingDirection(new Vector3D(0,0,-1));
        Point3D origin = new Point3D(0f, 0f, 0f);
        playerShip = new Body<>(new BoundingBoxCollidable(origin, new Dimension3D(7.086f, 1.323f, 12.380f)), myShip);
    }

    public void run(){
        Overworld theOverworld = Overworld.getOverworld();
        theOverworld.addNode(new Node(new BattleZone(1)));
        myBattleZone = (BattleZone) theOverworld.getZoneAtNode();
        myBattleZone.run(playerShip);
    }

    public void spawnEnemies(){
        myBattleZone.addEnemies();
    }

    public void update(){
        myBattleZone.update();
    }

    public void add(SpawnObserver o) { myBattleZone.add(o); }

    public void setPlayer(Body<Ship> playerShip)
    {
        this.playerShip = playerShip;
    }

    public Body<Ship> getPlayerShip() { return playerShip; }
}
