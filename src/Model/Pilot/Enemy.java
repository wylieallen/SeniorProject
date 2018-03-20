package Model.Pilot;

import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

public class Enemy extends Pilot {

    public Enemy(){
        super.setPilotStats(new PilotStats(getMaxLevel()));
        super.setFaction(Faction.REBEL);
    }

    @Override
    public void pilotDied() {
        BattleZone currentZone = (BattleZone) Overworld.getOverworld().getZoneAtNode();
        currentZone.enemyDestroyed(this);
    }

    @Override
    public Point3D move(Point3D curPosition){
        //Collidable updates?
        Point3D newPostion = super.move(curPosition);
        return newPostion;
    }
}
