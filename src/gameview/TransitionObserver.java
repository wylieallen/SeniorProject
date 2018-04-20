package gameview;

import Model.Map.Zones.Zone;

public interface TransitionObserver
{
    void switchToOverworld();
    void switchToTradingPost();
    void switchToInflight();
    void notifyTransition(Zone z);
}
