package Model.Map.Zones;

import Model.Items.LootChest;
import Model.Map.LocationTuple;
import Model.Pilot.*;
import Model.Powerup.Powerup;
import Utility.*;

import java.util.List;


public class BattleZone {

    private List<LocationTuple<Player>> players;
    private List<LocationTuple<Enemy>> enemies;
    private List<LocationTuple<Powerup>> powerups;
    private List<LocationTuple<LootChest>> loot;

    public void updatePosition(Vector3D unitVector){

    }

    public void addLootBox(){

    }

}
