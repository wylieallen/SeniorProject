package Model;

import Model.Ship.Ship;
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
import Utility.Geom3D.Point3D;
import Utility.Rarity;
import gameview.observers.spawn.SpawnObserver;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GameModel implements CollisionObserver
{
    private Body<Ship> playerShip;

    private Set<Body<Ship>> ships;
    private Set<Body<Projectile>> projectiles;

    private CollisionChecker collisionChecker;

    private Set<SpawnObserver> spawnObservers;

    public GameModel()
    {
        this.collisionChecker = new NaiveCollisionChecker();
        collisionChecker.add(this);
        this.spawnObservers = new HashSet<>();
        this.ships = new HashSet<>();
        this.projectiles = new HashSet<>();
        this.playerShip = new Body<>(new BoundingBoxCollidable(new Point3D(), new Dimension3D(0.2f, 0.2f, 1.0f)),
                new Ship(null, new ShipHull(10, 10, 10, Rarity.COMMON)));
        playerShip.get().equipEngine( new ShipEngine(10, 8, Rarity.COMMON));
        spawnShip(playerShip);
    }

    public void setPlayer(Body<Ship> playerShip)
    {
        this.playerShip = playerShip;
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
        spawnObservers.forEach(s -> s.notifyProjSpawn(projectile));
    }

    public void notifyShipToShip(Body<Ship> a, Body<Ship> b)
    {
        a.get().takeDamage(-30);
        b.get().takeDamage(-30);
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
    }

    public void add(SpawnObserver o) { spawnObservers.add(o); }
    public void add(CollisionObserver o) { collisionChecker.add(o); }

    public void update()
    {
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

    private void updateProjectile(Body<Projectile> projectile)
    {
        projectile.get().update();
        projectile.getCollidable().moveForward(projectile.get().getSpeed());
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

        if(ship.isAccelerating())
        {
            ship.accelerate();
        }
        if(ship.isDecelerating())
        {
            ship.decelerate();
        }

        body.getCollidable().moveForward(ship.getSpeed());

        if(ship.isFiring1())
        {
            spawnProjectile(new Body<Projectile>(
                    new BoundingBoxCollidable(body.getCollidable().getOrigin(), new Dimension3D(0.2f), body.getCollidable().getOrientation()),
                    new LinearProjectile()
            ));
        }

        if(ship.isFiring2())
        {

        }
    }

    public Body<Ship> getPlayerShip() { return playerShip; }
}
