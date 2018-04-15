package Model.physics;

import Model.Items.LootChest;
import Model.Map.Zones.Asteroid;
import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.collidable.Collidable;

public interface CollisionObserver
{
    void notifyShipToProj(Body<Ship> ship, Body<Projectile> projectile);
    void notifyShipToShip(Body<Ship> a, Body<Ship> b);
    void notifyProjToProj(Body<Projectile> a, Body<Projectile> b);
    void notifyShipToLoot(Body<Ship> ship, Body<LootChest> lootChest);
    void notifyAsteroidToShip(Body<Asteroid> asteroid, Body<Ship> ship);
}
