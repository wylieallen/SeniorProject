package Model.physics;

import Model.physics.collidable.Collidable;

public interface CollisionObserver
{
    void notifyCollision(Collidable c1, Collidable c2);
}
