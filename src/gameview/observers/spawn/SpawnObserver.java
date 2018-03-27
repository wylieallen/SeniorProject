package gameview.observers.spawn;

import Model.Map.LocationTuple;
import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;

public interface SpawnObserver
{
    //void notifySpawn(Projectile projectile, LocationTuple<Projectile> projectileLocationTuple);
    void notifyShipSpawn(Body<Ship> ship);
    void notifyProjSpawn(Body<Projectile> projectile);
}
