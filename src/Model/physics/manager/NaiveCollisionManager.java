package Model.physics.manager;

import Model.physics.CollisionObserver;
import Model.physics.collidable.Collidable;

import java.util.HashSet;
import java.util.Set;

public class NaiveCollisionManager implements CollisionManager
{
    private Set<CollisionObserver> observers;
    private Set<Collidable> collidables;

    private CollisionLogger logger;

    public NaiveCollisionManager()
    {
        this.observers = new HashSet<>();
        this.collidables = new HashSet<>();
        observers.add(logger = new CollisionLogger());
    }

    public void add(Collidable c) { collidables.add(c); }
    public void remove(Collidable c) { collidables.remove(c); }

    public void add(CollisionObserver o) { observers.add(o); }
    public void remove(CollisionObserver o) { observers.remove(o); }

    public void update()
    {
        for(Collidable c : collidables)
            c.update();

        Set<Collidable> expireds = new HashSet<>();

        for(Collidable a : collidables)
        {
            if(a.expired())
            {
                expireds.add(a);
                continue;
            }

            for(Collidable b : collidables)
            if(a != b && !a.expired() && !b.expired() && !logger.loggedCollision(a, b) && a.collidesWith(b))
            {
                a.collide(b);

                observers.forEach((o) -> o.notifyCollision(a, b));
            }
        }

        logger.clear();
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

        public void notifyCollision(Collidable a, Collidable b)
        {
            loggedCollisions.add(new CollisionTuple(a, b));
        }

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
