package guiframework.gui3d;

import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;

public interface SpawnObserver
{
    void notifySpawn(Projectile projectile);
    void notifySpawn(Ship ship);
}
