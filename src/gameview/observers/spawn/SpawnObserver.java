package gameview.observers.spawn;

import Model.Map.LocationTuple;
import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;

public interface SpawnObserver
{
    void notifySpawn(Projectile projectile, LocationTuple<Projectile> projectileLocationTuple);
    void notifySpawn(Ship ship);
}
