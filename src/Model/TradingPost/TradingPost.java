package Model.TradingPost;

import Model.Items.Inventory;
import Model.Items.Item;

import java.util.List;

public class TradingPost {
    private Inventory inventory;
    private Wallet wallet;
    private List<Bounty> bountyList;

    public TradingPost(Inventory i, Wallet w, List<Bounty> b){
        inventory = i;
        wallet = w;
        bountyList = b;
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
}
