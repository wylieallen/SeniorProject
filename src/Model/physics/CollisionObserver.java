package Model.physics;

import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.collidable.Collidable;

public interface CollisionObserver
{
    void notifyShipToProj(Body<Ship> ship, Body<Projectile> projectile);
    void notifyShipToShip(Body<Ship> a, Body<Ship> b);
    void notifyProjToProj(Body<Projectile> a, Body<Projectile> b);
}
