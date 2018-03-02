package Model.Map;

import Model.Map.Zones.Zone;

import java.util.ArrayList;
import java.util.List;

public class Overworld {

    public static Overworld instance = null;

    private List<Node> nodes;
    private Node currentNode;

    private Overworld(){
        //Singleton
        nodes = new ArrayList<>();
    }

    public static Overworld getOverworld(){
        if (instance == null){
            instance = new Overworld();
        }
        return instance;
    }

    public void addNode(Node node){
        nodes.add(node);
        currentNode = node;
    }

    public void setCurrentNode(Node node){
        currentNode = node;
    }

    public Node getCurrentNode(){
        return currentNode;
    }

    public Zone getZoneAtNode(){
        return currentNode.getThisZone();
    }



}
