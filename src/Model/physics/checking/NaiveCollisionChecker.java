package Model.physics.checking;

import Model.Items.LootChest;
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

    public void processCollisions(Set<Body<Ship>> ships, Set<Body<Projectile>> projectiles, Set<Body<LootChest>> lootChests)
    {
        logger.clear();

        for(Body<Ship> a : ships)
        {
            for(Body<Ship> b : ships)
            {
                if(a != b && !a.get().expired() && !b.get().expired()
                        && !logger.loggedCollision(a.getCollidable(), b.getCollidable())
                        && a.getCollidable().collidesWith(b.getCollidable()))
                {
                    observers.forEach((o) -> o.notifyShipToShip(a, b));
                }
            }
        }

        for(Body<Ship> ship : ships)
        {
            for(Body<Projectile> projectile : projectiles)
            {
                if(!ship.get().expired() && !projectile.get().expired()
                        && !logger.loggedCollision(ship.getCollidable(), projectile.getCollidable())
                        && ship.getCollidable().collidesWith(projectile.getCollidable()))
                {
                    observers.forEach((o) -> o.notifyShipToProj(ship, projectile));
                }
            }
        }

        for(Body<Projectile> a : projectiles)
        {
            for(Body<Projectile> b : projectiles)
            {
                if(a != b && !a.get().expired() && !b.get().expired()
                        && !logger.loggedCollision(a.getCollidable(), b.getCollidable())
                        && a.getCollidable().collidesWith(b.getCollidable()))
                {
                    observers.forEach((o) -> o.notifyProjToProj(a, b));
                }
            }
        }

        for (Body<Ship> ship : ships){
            for (Body<LootChest> lootChest : lootChests){
                if(!ship.get().expired() && !lootChest.get().expired()
                        && !logger.loggedCollision(ship.getCollidable(), lootChest.getCollidable())
                        && ship.getCollidable().collidesWith(lootChest.getCollidable()))
                {
                    observers.forEach((o) -> o.notifyShipToLoot(ship, lootChest));
                }
            }
        }
    }

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

        public void notifyShipToShip(Body<Ship> a, Body<Ship> b) { logCollision(a.getCollidable(), b.getCollidable()); }
        public void notifyShipToProj(Body<Ship> ship, Body<Projectile> proj) { logCollision(ship.getCollidable(), proj.getCollidable());}
        public void notifyProjToProj(Body<Projectile> a, Body<Projectile> b) { logCollision(a.getCollidable(), b.getCollidable());}
        public void notifyShipToLoot(Body<Ship> a, Body<LootChest> b) { logCollision(a.getCollidable(), b.getCollidable());}

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
