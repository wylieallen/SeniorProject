package gameview.drawstate;

import Model.Items.Consumables.FuelConsumable;
import Model.Items.Consumables.HealthConsumable;
import Model.Items.Consumables.ShieldConsumable;
import Model.Items.Inventory;
import Model.Items.Item;
import Model.Pilot.Player;
import Model.Ship.Ship;
import Model.Ship.ShipParts.ShipHull;
import Model.TradingPost.BountyMission;
import Model.TradingPost.TradingPost;
import Model.TradingPost.Wallet;
import Utility.Rarity;
import guiframework.gui2d.Drawstate;
import guiframework.gui2d.ImageFactory;
import guiframework.gui2d.clickable.Button;
import guiframework.gui2d.clickable.ItemButton;
import guiframework.gui2d.clickable.Overlay;
import guiframework.gui2d.displayable.Displayable;
import guiframework.gui2d.displayable.ImageDisplayable;
import guiframework.gui2d.displayable.StringDisplayable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TradingPostDrawstate extends Drawstate
{
    private static final int HEIGHT = 200;
    private static final int WIDTH = 800;
    private static final int MARGIN = 10;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private TradingPost currentTP;
    private Player currentPlayer;
    private int selectedItem = 0;
    private List<ItemButton> playerItems;
    private List<ItemButton> tpItems;
    private Overlay playerInventoryOverlay = new Overlay(new Point());
    private Overlay tpInventoryOverlay = new Overlay(new Point());
    private Overlay bountyList = new Overlay(new Point());
    private Overlay activeOverlay = new Overlay(new Point());
    private Overlay inactiveOverlay = new Overlay(new Point());
    private Overlay activeSelectedOverlay = new Overlay(new Point());
    private Inventory playerInventory;
    private Inventory tpInventory;
    private List<ItemButton> activeItems;
    private List<ItemButton> inacvtiveItems;
    private Wallet playerWallet;
    private Wallet tpWallet;

    public TradingPostDrawstate() {

        //todo: Figure out how player and trading post are passed to Drawstate properly. Temporarily adding test player and trading post.
        currentPlayer = new Player();
        currentPlayer.getMyWallet().increaseCurrencyBalance(1500);
        Ship ship = new Ship(currentPlayer, new ShipHull(1000, 1000, 30, Rarity.COMMON));
        currentPlayer.getShipHangar().addShip(ship);
        currentPlayer.setActiveShip(ship);
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));

        currentTP = new TradingPost(new Inventory(20), new Wallet(500), new ArrayList<BountyMission>());
        currentTP.getInventory().addItem(new FuelConsumable(300, 70));
        currentTP.getInventory().addItem(new FuelConsumable(400, 80));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));

        playerInventory = currentPlayer.getActiveShip().getInventory();
        tpInventory = currentTP.getInventory();

        playerWallet = currentPlayer.getMyWallet();
        tpWallet = currentTP.getWallet();

        playerItems = new ArrayList<ItemButton>();
        tpItems = new ArrayList<ItemButton>();
        activeItems = new ArrayList<ItemButton>();
        inacvtiveItems = new ArrayList<ItemButton>();


        //todo: add space background here
//        this.addUnderlay(Displayable.NULL);
//
//        ImageDisplayable background =
//                new ImageDisplayable(new Point(WIDTH,0), ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT*5, Color.black, Color.GRAY, Color.WHITE, "Space Background. Pretty stars and stuff."));
//
//        this.addUnderlay(background);
//
//        this.addUnderlay(Displayable.NULL);

        //Add title box
        ImageDisplayable tpTitle =
                new ImageDisplayable(new Point(0,0), ImageFactory.getTradingPostLabel());

        this.addLeftOverlay(tpTitle);

        //Add Buy Button
        Button buyButton = new Button(new Point(0, HEIGHT),
                ImageFactory.getBuyButtonBase(),
                ImageFactory.getBuyButtonHover(),
                ImageFactory.getBuyButtonPress(),
                () ->
                {
                    //Clear TP Items button list
                    tpItems.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    this.removeAllRightOverlays();
                    this.removeClickable(activeOverlay);
                    activeOverlay = this.tpInventoryOverlay;
//                    inactiveOverlay = this.playerInventoryOverlay;
                    this.addRightOverlay(this.tpInventoryOverlay);
                    this.addClickable(this.tpInventoryOverlay);

                    //Add selected item Overlay
                    Overlay tpItemSelected = new Overlay(new Point(WIDTH/5,HEIGHT*3));
                    Displayable tpiBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLUE, Color.GRAY));
                    tpItemSelected.add(tpiBackground);
                    tpItemSelected.add(new StringDisplayable( new Point(16, 16), () -> "" + tpInventory.getItem(selectedItem).getCurrencyValue(), Color.RED, font));

                    Button itemBuy = new Button(new Point(16,100),
                            ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "Buy Item"),
                            ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, "Buy Item"),
                            ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "Buy Item"),
                            () ->
                            {
                                Item item = tpInventory.getItem(this.selectedItem);
                                ItemButton button =  tpItems.get(selectedItem);
                                if(playerWallet.getCurrencyBalance() >= item.getCurrencyValue()) {
                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                    activeOverlay.remove(tpItemSelected);
                                    tpWallet.increaseCurrencyBalance(item.getCurrencyValue());
                                    playerWallet.decreaseCurrencyBalance(item.getCurrencyValue());
                                    tpInventory.removeItem(item);
                                    playerInventory.addItem(item);
                                    tpInventoryOverlay.removeClickable(button);
                                    tpInventoryOverlay.remove(button);
                                    tpItems.remove(button);
//                                    inacvtiveItems.add(button);
//                                    inactiveOverlay.add(button);
//                                    inactiveOverlay.addClickable(button);
                                    redrawButtons();
                                    System.out.println("Item bought!");
                                }
                                else
                                    System.out.println("You do not have enough money to buy this item!");
//                    currentTP.getInventory().removeItem(item);
//                    playerInventory.addItem(item);
                            });
                    tpItemSelected.addClickable(itemBuy);
                    tpItemSelected.add(itemBuy);

                    //Adding tpInventoryOverlay item displayables
                    for(int i = 0; i < tpInventory.getcurrItemsNum(); i++){
                        Item item = tpInventory.getItem(i);

                        ItemButton tpItem = new ItemButton(item, new Point((160*(i%4)) + ((i%4)*MARGIN) + MARGIN, 100 +(100*(i/4))),
                                ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, item.getName()),
                                ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, item.getName()),
                                ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, item.getName()),
                                () ->
                                {
                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                    tpInventoryOverlay.remove(tpItemSelected);
                                    selectedItem = tpInventory.getIndex(item);
                                    tpInventoryOverlay.addClickable(tpItemSelected);
                                    tpInventoryOverlay.add(tpItemSelected);
                                    this.activeSelectedOverlay = tpItemSelected;
                                });

                        tpItems.add(tpItem);
                        tpInventoryOverlay.addClickable(tpItem);
                        tpInventoryOverlay.add(tpItem);
                    }
//                    activeOverlay.remove(activeSelectedOverlay);
//                    activeOverlay.removeClickable(activeSelectedOverlay);
//                    this.removeAllRightOverlays();
//                    this.removeClickable(activeOverlay);
//                    activeOverlay = this.tpInventoryOverlay;
//                    inactiveOverlay = this.playerInventoryOverlay;
//                    this.addRightOverlay(this.tpInventoryOverlay);
//                    this.addClickable(this.tpInventoryOverlay);
//                    playerInventory = currentTP.getInventory();
//                    tpInventory = currentPlayer.getActiveShip().getInventory();
//                    buyingWallet = currentPlayer.getMyWallet();
//                    sellingWallet = currentTP.getWallet();
//                    activeItems = tpItems;
//                    inacvtiveItems = playerItems;
                });

        this.addClickable(buyButton);
        this.addLeftOverlay(buyButton);

        //Add Sell Button
        Button sellButton = new Button(new Point(0, (HEIGHT * 2) + 20),
                ImageFactory.getSellButtonBase(),
                ImageFactory.getSellButtonHover(),
                ImageFactory.getSellButtonPress(),
                () ->
                {
                    //Clear Player Item button list
                    playerItems.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    this.removeAllRightOverlays();
                    this.removeClickable(activeOverlay);
                    activeOverlay = this.playerInventoryOverlay;
//                    inactiveOverlay = this.tpInventoryOverlay;
                    this.addRightOverlay(this.playerInventoryOverlay);
                    this.addClickable(this.playerInventoryOverlay);

                    //Add selected item Overlay
                    Overlay playerItemSelected = new Overlay(new Point(WIDTH/5,HEIGHT*3));
                    Displayable pisBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLUE, Color.GRAY));
                    playerItemSelected.add(pisBackground);
                    playerItemSelected.add(new StringDisplayable( new Point(16, 16), () -> "" + playerInventory.getItem(selectedItem).getCurrencyValue(), Color.RED, font));

                    Button itemSell = new Button(new Point(16,100),
                            ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "Sell Item"),
                            ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, "Sell Item"),
                            ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "Sell Item"),
                            () ->
                            {
                                Item item = playerInventory.getItem(this.selectedItem);
                                ItemButton button =  playerItems.get(selectedItem);
                                if(tpWallet.getCurrencyBalance() >= item.getCurrencyValue()) {
                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                    playerInventoryOverlay.remove(playerItemSelected);
                                    playerWallet.increaseCurrencyBalance(item.getCurrencyValue());
                                    tpWallet.decreaseCurrencyBalance(item.getCurrencyValue());
                                    playerInventory.removeItem(item);
                                    tpInventory.addItem(item);
                                    playerInventoryOverlay.removeClickable(button);
                                    playerInventoryOverlay.remove(button);
                                    playerItems.remove(button);
//                                    tpItems.add(button);
//                                    tpInventoryOverlay.add(button);
//                                    inactiveOverlay.addClickable(button);
                                    redrawButtons();
                                    System.out.println("Item sold!");
                                }
                                else
                                    System.out.println("Shop does not have enough money to buy this item!");
//                    currentTP.getInventory().removeItem(item);
//                    currentPlayer.getActiveShip().getInventory().addItem(item);
                            });
                    playerItemSelected.addClickable(itemSell);
                    playerItemSelected.add(itemSell);

                    //Adding playerInventoryOverlay item displayables
                    for(int i = 0; i < playerInventory.getcurrItemsNum(); i++){

                        Item item = playerInventory.getItem(i);

                        ItemButton playerItem = new ItemButton(item, new Point((160*(i%4)) + ((i%4)*MARGIN) + MARGIN, 100 +(100*(i/4))),
                                ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, item.getName()),
                                ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, item.getName()),
                                ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, item.getName()),
                                () ->
                                {
                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                    playerInventoryOverlay.remove(playerItemSelected);
                                    selectedItem = playerInventory.getIndex(item);
                                    playerInventoryOverlay.addClickable(playerItemSelected);
                                    playerInventoryOverlay.add(playerItemSelected);
                                    this.activeSelectedOverlay = playerItemSelected;
                                });

                        playerItems.add(playerItem);
                        playerInventoryOverlay.addClickable(playerItem);
                        playerInventoryOverlay.add(playerItem);
                    }
//                    activeOverlay.remove(activeSelectedOverlay);
//                    activeOverlay.removeClickable(activeSelectedOverlay);
//                    this.removeAllRightOverlays();
//                    this.removeClickable(activeOverlay);
//                    activeOverlay = this.playerInventoryOverlay;
//                    inactiveOverlay = this.tpInventoryOverlay;
//                    this.addRightOverlay(this.playerInventoryOverlay);
//                    this.addClickable(this.playerInventoryOverlay);
//                    playerInventory = playerInventory;
//                    tpInventory = currentTP.getInventory();
//                    buyingWallet = currentTP.getWallet();
//                    sellingWallet = currentPlayer.getMyWallet();
//                    activeItems = playerItems;
//                    inacvtiveItems = tpItems;
                });

        this.addClickable(sellButton);
        this.addLeftOverlay(sellButton);

        //Add Bounty Missions Button
        Button bountyButton = new Button(new Point(0, HEIGHT * 3),
                ImageFactory.getBountyButtonBase(),
                ImageFactory.getBountyButtonHover(),
                ImageFactory.getBountyButtonPress(),
                () ->
                {
                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    this.removeAllRightOverlays();
                    this.addRightOverlay(this.bountyList);
                    this.removeClickable(activeOverlay);
                    activeOverlay = this.bountyList;
                    this.addClickable(this.bountyList);
                });

        this.addClickable(bountyButton);
        this.addLeftOverlay(bountyButton);

        //Add Exit Button
        Button exitButton = new Button(new Point(0, HEIGHT * 4),
                ImageFactory.getExitButtonBase(),
                ImageFactory.getExitButtonHover(),
                ImageFactory.getExitButtonPress(),
                () -> {});

        this.addClickable(exitButton);
        this.addLeftOverlay(exitButton);

        //Create Player Inventory Overlay
        Overlay playerInventoryOverlay = new Overlay(new Point());
        Displayable piBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        playerInventoryOverlay.add(piBackground);
        playerInventoryOverlay.add(new StringDisplayable( new Point(16, 16), () -> " Player Inventory"));
        playerInventoryOverlay.add(new StringDisplayable( new Point(16, 64), () -> " Player MONEY: " + playerWallet.getCurrencyBalance()));
        playerInventoryOverlay.add(new StringDisplayable( new Point(WIDTH/2, 64), () -> " Trading Post MONEY: " + tpWallet.getCurrencyBalance()));

        this.playerInventoryOverlay = playerInventoryOverlay;


//        //Add selected item Overlay
//        Overlay playerItemSelected = new Overlay(new Point(WIDTH/5,HEIGHT*3));
//        Displayable pisBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLUE, Color.GRAY));
//        playerItemSelected.add(pisBackground);
//        playerItemSelected.add(new StringDisplayable( new Point(16, 16), () -> "" + playerInventory.getItem(selectedItem).getCurrencyValue(), Color.RED, font));
//
//        Button itemSell = new Button(new Point(16,100),
//                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "Sell Item"),
//                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, "Sell Item"),
//                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "Sell Item"),
//                () ->
//                {
//                    Item item = playerInventory.getItem(this.selectedItem);
//                    ItemButton button =  activeItems.get(selectedItem);
//                    if(buyingWallet.getCurrencyBalance() >= item.getCurrencyValue()) {
//                        activeOverlay.removeClickable(activeSelectedOverlay);
//                        activeOverlay.remove(activeSelectedOverlay);
//                        sellingWallet.increaseCurrencyBalance(item.getCurrencyValue());
//                        buyingWallet.decreaseCurrencyBalance(item.getCurrencyValue());
//                        playerInventory.removeItem(item);
//                        tpInventory.addItem(item);
//                        activeOverlay.removeClickable(button);
//                        activeOverlay.remove(button);
//                        activeItems.remove(button);
//                        inacvtiveItems.add(button);
//                        inactiveOverlay.add(button);
//                        inactiveOverlay.addClickable(button);
//                        redrawButtons();
//                        System.out.println("Item sold!");
//                    }
//                    else
//                        System.out.println("Shop does not have enough money to buy this item!");
////                    currentTP.getInventory().removeItem(item);
////                    playerInventory.addItem(item);
//                });
//        playerItemSelected.addClickable(itemSell);
//        playerItemSelected.add(itemSell);
//
//        //Adding playerInventoryOverlay item displayables
//        for(int i = 0; i < playerInventory.getcurrItemsNum(); i++){
//
//            Item item = playerInventory.getItem(i);
//
//            ItemButton playerItem = new ItemButton(item, new Point((160*(i%4)) + ((i%4)*MARGIN) + MARGIN, 100 +(100*(i/4))),
//                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, item.getName()),
//                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, item.getName()),
//                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, item.getName()),
//                    () ->
//                    {
//                        activeOverlay.removeClickable(playerItemSelected);
//                        activeOverlay.remove(playerItemSelected);
//                        selectedItem = playerInventory.getIndex(item);
//                        activeOverlay.addClickable(playerItemSelected);
//                        activeOverlay.add(playerItemSelected);
//                        this.activeSelectedOverlay = playerItemSelected;
//                    });
//
//            playerItems.add(playerItem);
//            playerInventoryOverlay.addClickable(playerItem);
//            playerInventoryOverlay.add(playerItem);
//        }


//        this.playerInventoryOverlay = playerInventoryOverlay;

        Overlay tpInventoryOverlay = new Overlay(new Point());
        Displayable tpBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        tpInventoryOverlay.add(tpBackground);
        tpInventoryOverlay.add(new StringDisplayable( new Point(16, 16), () -> " Trading Post Inventory"));
        tpInventoryOverlay.add(new StringDisplayable( new Point(16, 64), () -> "Player MONEY: " + currentPlayer.getMyWallet().getCurrencyBalance()));
        tpInventoryOverlay.add(new StringDisplayable( new Point(WIDTH/2, 64), () -> " Trading Post MONEY: " + currentTP.getWallet().getCurrencyBalance()));


//        //Add selected item Overlay
//        Overlay tpItemSelected = new Overlay(new Point(WIDTH/5,HEIGHT*3));
//        Displayable tpiBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLUE, Color.GRAY));
//        tpItemSelected.add(tpiBackground);
//        tpItemSelected.add(new StringDisplayable( new Point(16, 16), () -> "" + playerInventory.getItem(selectedItem).getCurrencyValue(), Color.RED, font));
//
//        Button itemBuy = new Button(new Point(16,100),
//                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "Buy Item"),
//                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, "Buy Item"),
//                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "Buy Item"),
//                () ->
//                {
//                    Item item = playerInventory.getItem(this.selectedItem);
//                    ItemButton button =  activeItems.get(selectedItem);
//                    if(buyingWallet.getCurrencyBalance() >= item.getCurrencyValue()) {
//                        activeOverlay.removeClickable(activeSelectedOverlay);
//                        activeOverlay.remove(activeSelectedOverlay);
//                        sellingWallet.increaseCurrencyBalance(item.getCurrencyValue());
//                        buyingWallet.decreaseCurrencyBalance(item.getCurrencyValue());
//                        playerInventory.removeItem(item);
//                        tpInventory.addItem(item);
//                        activeOverlay.removeClickable(button);
//                        activeOverlay.remove(button);
//                        activeItems.remove(button);
//                        inacvtiveItems.add(button);
//                        inactiveOverlay.add(button);
//                        inactiveOverlay.addClickable(button);
//                        redrawButtons();
//                        System.out.println("Item bought!");
//                    }
//                    else
//                        System.out.println("You do not have enough money to buy this item!");
////                    currentTP.getInventory().removeItem(item);
////                    playerInventory.addItem(item);
//                });
//        tpItemSelected.addClickable(itemBuy);
//        tpItemSelected.add(itemBuy);
//
//        //Adding tpInventoryOverlay item displayables
//        for(int i = 0; i < currentTP.getInventory().getcurrItemsNum(); i++){
//            Item item = currentTP.getInventory().getItem(i);
//
//            ItemButton tpItem = new ItemButton(item, new Point((160*(i%4)) + ((i%4)*MARGIN) + MARGIN, 100 +(100*(i/4))),
//                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, item.getName()),
//                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, item.getName()),
//                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, item.getName()),
//                    () ->
//                    {
//                        activeOverlay.removeClickable(tpItemSelected);
//                        activeOverlay.remove(tpItemSelected);
//                        selectedItem = playerInventory.getIndex(item);
//                        activeOverlay.addClickable(tpItemSelected);
//                        activeOverlay.add(tpItemSelected);
//                        this.activeSelectedOverlay = tpItemSelected;
//                    });
//
//            tpItems.add(tpItem);
//            tpInventoryOverlay.addClickable(tpItem);
//            tpInventoryOverlay.add(tpItem);
//        }

//        tpInventoryOverlay.addClickable(tpItemSelected);
//        tpInventoryOverlay.add(tpItemSelected);
        this.tpInventoryOverlay = tpInventoryOverlay;

        Overlay bountyList =  new Overlay(new Point());
        Displayable bountyBackground =  new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        bountyList.add(bountyBackground);
        bountyList.add(new StringDisplayable(new Point(16, 64), () -> "Bounty List"));

        Button bountyListing = new Button(new Point(WIDTH/5,HEIGHT ),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Take Mission"),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Take Mission"),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Take Mission"),
                () ->
                {

                });
        bountyList.addClickable(bountyListing);
        bountyList.add(bountyListing);
        this.bountyList = bountyList;
    }

    private void redrawButtons(){
        //redraw tpInventoryOverlay
        for(int i = 0; i < tpItems.size(); i++) {
            tpItems.get(i).getOrigin().setLocation((160*(i%4)) + ((i%4)*MARGIN) + MARGIN, 100 +(100*(i/4)));
        }

        //redraw playerInventoryOverlay
        for(int i = 0; i < playerItems.size(); i++) {
            playerItems.get(i).getOrigin().setLocation((160*(i%4)) + ((i%4)*MARGIN) + MARGIN, 100+(100*(i/4)));
        }
    }
    // Placeholder money example stuff goes here
//    public int getMoney() { return money; }
//    public void modifyMoney(int newMoney) { money += newMoney; }


}
