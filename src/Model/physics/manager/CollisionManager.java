package Model.physics.manager;

import Model.physics.CollisionObserver;
import Model.physics.collidable.Collidable;

public interface CollisionManager
{
    void add(Collidable c);
    void remove(Collidable c);
    void add(CollisionObserver o);
    void remove(CollisionObserver o);
    void update();
}
