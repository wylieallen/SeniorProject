package Model.Map.Zones;

import Model.TradingPost.TradingPost;

public class TradingZone extends Zone{
    private TradingPost tradingPost;

    public TradingZone(TradingPost tp) {
        tradingPost = tp;
    }

    public TradingPost getTradingPost() {
        return tradingPost;
    }
}
