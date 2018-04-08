package guiframework;

import Model.Map.Zones.Zone;

public interface ZoneTransitionObserver
{
    void notifyTransition(Zone nextZone);
}
