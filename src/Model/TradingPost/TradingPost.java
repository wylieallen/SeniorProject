package Model.TradingPost;

import Model.Items.Inventory;
import Model.Items.Item;
import Model.Pilot.Player;

import java.util.Iterator;
import java.util.List;

public class TradingPost {
    private Inventory inventory;
    private Wallet wallet;
    private List<BountyMission> bountyList;

    public TradingPost(Inventory i, Wallet w, List<BountyMission> b){
        inventory = i;
        wallet = w;
        bountyList = b;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public List<BountyMission> getBountyList() {
        return bountyList;
    }
}
