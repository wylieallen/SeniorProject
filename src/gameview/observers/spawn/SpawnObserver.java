package gameview.observers.spawn;

import Model.Items.LootChest;
import Model.Map.LocationTuple;
import Model.Map.Zones.Asteroid;
import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;

public interface SpawnObserver
{
    //void notifySpawn(Projectile projectile, LocationTuple<Projectile> projectileLocationTuple);
    void notifyShipSpawn(Body<Ship> ship);
    void notifyProjSpawn(Body<Projectile> projectile);
    void notifyLootSpawn(Body<LootChest> lootChest);
    void notifyAsteroidSpawn(Body<Asteroid> asteroid);
}
