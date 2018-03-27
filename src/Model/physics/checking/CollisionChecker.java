package Model.physics.checking;

import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;
import Model.physics.CollisionObserver;

import java.util.Set;

public interface CollisionChecker
{
    void add(CollisionObserver o);
    void remove(CollisionObserver o);
    void processCollisions(Set<Body<Ship>> ships, Set<Body<Projectile>> projectiles);
}
