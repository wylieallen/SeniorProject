package Model.Map.Zones;

import Model.TradingPost.TradingPost;

public class TradingZone extends Zone{
    private TradingPost tradingPost;
    private String zoneType = "Trading Post";

    public TradingZone(TradingPost tp) {
        tradingPost = tp;
    }

    public TradingPost getTradingPost() {
        return tradingPost;
    }

    public String getZoneType() {return zoneType;}
}
