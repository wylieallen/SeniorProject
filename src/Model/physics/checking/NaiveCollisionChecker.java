package Model.physics.checking;

import Model.Items.LootChest;
import Model.Map.Zones.Asteroid;
import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;
import Model.physics.CollisionObserver;
import Model.physics.collidable.Collidable;

import java.util.HashSet;
import java.util.Set;

public class NaiveCollisionChecker implements CollisionChecker
{
    private Set<CollisionObserver> observers;
    private CollisionLogger logger;

    public NaiveCollisionChecker()
    {
        this.observers = new HashSet<>();
        observers.add(logger = new CollisionLogger());
    }
    public void add(CollisionObserver o) { observers.add(o); }
    public void remove(CollisionObserver o) { observers.remove(o); }

    public void processCollisions(Set<Body<Ship>> ships, Set<Body<Projectile>> projectiles, Set<Body<LootChest>> lootChests, Set<Body<Asteroid>> asteroids)
    {
        logger.clear();

        //check ship to ship
        for(Body<Ship> a : ships)
        {
            for(Body<Ship> b : ships)
            {
                if(a != b && !a.get().expired() && !b.get().expired()
                        && !logger.loggedCollision(a, b)
                        && a.collidesWith(b))
                {
                    observers.forEach((o) -> o.notifyShipToShip(a, b));
                }
            }
        }

        //check ship to projectile
        for(Body<Ship> ship : ships)
        {
            for(Body<Projectile> projectile : projectiles)
            {
                if(!ship.get().expired() && !projectile.get().expired()
                        && !logger.loggedCollision(ship, projectile)
                        && ship.collidesWith(projectile))
                {
                    observers.forEach((o) -> o.notifyShipToProj(ship, projectile));
                }
            }
        }

        //Check projectile to projectile
        for(Body<Projectile> a : projectiles)
        {
            for(Body<Projectile> b : projectiles)
            {
                if(a != b && !a.get().expired() && !b.get().expired()
                        && !logger.loggedCollision(a, b)
                        && a.collidesWith(b))
                {
                    observers.forEach((o) -> o.notifyProjToProj(a, b));
                }
            }
        }

        //Check ship to lootchest
        for (Body<Ship> ship : ships){
            for (Body<LootChest> lootChest : lootChests){
                if(!ship.get().expired() && !lootChest.get().expired()
                        && !logger.loggedCollision(ship, lootChest)
                        && ship.collidesWith(lootChest))
                {
                    observers.forEach((o) -> o.notifyShipToLoot(ship, lootChest));
                }
            }
        }

        //check asteroid to asteroid
        for (Body<Asteroid> asteroid : asteroids){
            for (Body<Ship> ship : ships){
                if(!asteroid.get().expired() && !ship.get().expired()
                        && !logger.loggedCollision(asteroid, ship)
                        && asteroid.collidesWith(ship))
                {
                    observers.forEach((o) -> o.notifyAsteroidToShip(asteroid, ship));
                }
            }
        }
    }

    // todo: this is all wrong, CollisionLogger should be logging all CHECKED collisions but right now it's only logging CONFIRMED collisions
    private class CollisionLogger implements CollisionObserver
    {
        private Set<CollisionTuple> loggedCollisions = new HashSet<>();

        public void clear() { loggedCollisions.clear(); }

        public boolean loggedCollision(Collidable a, Collidable b)
        {
            CollisionTuple check = new CollisionTuple(a, b);

            for(CollisionTuple tuple : loggedCollisions)
            {
                if(check.equals(tuple))
                {
                    return true;
                }
            }

            return false;
        }

        public void notifyShipToShip(Body<Ship> a, Body<Ship> b) { logCollision(a, b); }
        public void notifyShipToProj(Body<Ship> ship, Body<Projectile> proj) { logCollision(ship, proj);}
        public void notifyProjToProj(Body<Projectile> a, Body<Projectile> b) { logCollision(a, b);}
        public void notifyShipToLoot(Body<Ship> a, Body<LootChest> b) { logCollision(a, b);}
        public void notifyAsteroidToShip(Body<Asteroid> asteroid, Body<Ship> ship){ logCollision(asteroid, ship);}

        public void logCollision(Collidable a, Collidable b) { loggedCollisions.add(new CollisionTuple(a, b)); }

        private class CollisionTuple
        {
            private Collidable a, b;
            public CollisionTuple(Collidable a, Collidable b)
            {
                this.a = a;
                this.b = b;
            }

            public boolean equals(CollisionTuple other)
            {
                return (this.a == other.a && this.b == other.b) || (this.a == other.b && this.b == other.a);
            }
        }
    }
}
