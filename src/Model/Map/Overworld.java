package Model.Map;

import Model.Map.Zones.Zone;

import java.util.List;

public class Overworld {

    public static Overworld instance = null;

    private List<Node> nodes;
    private Node currentNode;

    private Overworld(){
        //Singleton
    }

    public static Overworld getOverworld(){
        if (instance == null){
            instance = new Overworld();
        }
        return instance;
    }

    public Node getCurrentNode(){
        return currentNode;
    }

    public Zone getZoneAtNode(){
        return currentNode.getThisZone();
    }



}
