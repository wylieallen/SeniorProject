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
    private Iterator<BountyMission> bountyIterator;

    public TradingPost(Inventory i, Wallet w, List<BountyMission> b){
        inventory = i;
        wallet = w;
        bountyList = b;
        bountyIterator = bountyList.iterator();
    }

    public void Buy(Item i) {
        int value = i.getCurrencyValue();

        if(value <= wallet.getCurrencyBalance()) {
            inventory.addItem(i);
            wallet.decreaseCurrencyBalance(value);
        }
        else
            System.out.println("Could not buy item: Insuffiecient Funds");
    }

    public void Sell(Item i){
        int value = i.getCurrencyValue();
        inventory.removeItem(i);
        wallet.increaseCurrencyBalance(value);
    }

    public void giveBountyMission(Player p, BountyMission b) {
        p.setCurrentBountyMission(b);
        bountyList.remove(b);
    }

    public void rewardFinishedBounty(Player p) {
        BountyMission b = p.getCurrentBountyMission();
        Wallet w = p.getMyWallet();
        if(b.isCompleted()) {
            w.increaseCurrencyBalance(b.getCurrencyValue());
            p.setCurrentBountyMission(null);
        }
    }
}
