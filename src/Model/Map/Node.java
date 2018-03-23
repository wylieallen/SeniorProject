package Model.Map;

import Model.Map.Zones.Zone;

public class Node {
    private Zone thisZone;

    public Node(Zone z) {
        thisZone = z;
    }

    public Zone getThisZone() {
        return thisZone;
    }
}