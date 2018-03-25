package Model;

import Model.Ship.Ship;
import Model.Ship.ShipParts.ShipEngine;
import Model.Ship.ShipParts.ShipHull;
import Model.physics.CollisionObserver;
import Model.physics.manager.CollisionManager;
import Model.physics.manager.NaiveCollisionManager;
import Utility.Rarity;
import gameview.observers.spawn.SpawnObserver;

import java.util.HashSet;
import java.util.Set;

public class GameModel
{
    private Ship playerShip;

    private CollisionManager collisionManager;

    private Set<SpawnObserver> spawnObservers;

    public GameModel()
    {
        this.collisionManager = new NaiveCollisionManager();
        this.spawnObservers = new HashSet<>();
        this.playerShip = new Ship(null, new ShipHull(10, 10, 10, Rarity.COMMON));
        playerShip.equipEngine( new ShipEngine(10, 8, Rarity.COMMON));
        spawn(playerShip);
    }

    public void setPlayer(Ship playerShip)
    {
        this.playerShip = playerShip;
    }

    public void spawnPlayer(Ship playerShip)
    {
        setPlayer(playerShip);
        spawn(playerShip);
    }

    public void spawn(Ship ship)
    {
        collisionManager.add(ship);
        spawnObservers.forEach(s -> s.notifySpawn(ship));
    }

    public void add(SpawnObserver o) { spawnObservers.add(o); }
    public void add(CollisionObserver o) { collisionManager.add(o); }

    public void update() { collisionManager.update(); }

    public Ship getPlayerShip() { return playerShip; }
}
