package guiframework;

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
import guiframework.clickable.Button;
import guiframework.clickable.Overlay;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TradingPostUberstate extends Uberstate{
    static final int HEIGHT = 200;
    static final int WIDTH = 800;
    private TradingPost currentTP;
    private Player currentPlayer;
    private int selectedItem;
    private Overlay playerInventory = new Overlay(new Point());
    private Overlay tpInventory = new Overlay(new Point());
    private Overlay bountyList = new Overlay(new Point());
    private Overlay activeOverlay = new Overlay(new Point());

    public TradingPostUberstate() {

        //todo: Figure out how player and trading post are passed to Uberstate properly. Temporarily adding test player and trading post.
        currentPlayer = new Player();
        currentPlayer.getMyWallet().increaseCurrencyBalance(200);
        Ship ship = new Ship(currentPlayer, new ShipHull(1000, 1000, 30, Rarity.COMMON));
        currentPlayer.getShipHangar().addShip(ship);
        currentPlayer.setActiveShip(ship);
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));

        currentTP = new TradingPost(new Inventory(20), new Wallet(500), new ArrayList<BountyMission>());
        currentTP.getInventory().addItem(new FuelConsumable(300, 70));
        currentTP.getInventory().addItem(new FuelConsumable(300, 70));
        currentTP.getInventory().addItem(new FuelConsumable(300, 70));


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
                new ImageDisplayable(new Point(0,0),ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.BLUE, Color.GRAY, Color.RED, "Trading Post"));

        this.addLeftOverlay(tpTitle);

        //Add Buy Button
        Button buyButton = new Button(new Point(0, HEIGHT),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.BLACK, Color.BLACK, Color.WHITE, "Buy"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.BLACK, Color.WHITE, Color.WHITE, "Buy"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.BLACK, Color.BLUE, Color.WHITE, "Buy"),
                () ->
                {
                    this.removeAllRightOverlays();
                    this.removeClickable(activeOverlay);
                    activeOverlay = this.tpInventory;
                    this.addRightOverlay(this.tpInventory);
                    this.addClickable(this.tpInventory);
                });

        this.addClickable(buyButton);
        this.addLeftOverlay(buyButton);

        //Add Sell Button
        Button sellButton = new Button(new Point(0, (HEIGHT * 2) + 20),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.BLACK, Color.BLACK, Color.WHITE, "Sell"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.BLACK, Color.WHITE, Color.WHITE, "Sell"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.BLACK, Color.BLUE, Color.WHITE, "Sell"),
                () ->
                {
                    this.removeAllRightOverlays();
                    this.removeClickable(activeOverlay);
                    activeOverlay = this.playerInventory;
                    this.addRightOverlay(this.playerInventory);
                    this.addClickable(this.playerInventory);
                });

        this.addClickable(sellButton);
        this.addLeftOverlay(sellButton);

        //Add Bounty Missions Button
        Button bountyButton = new Button(new Point(0, HEIGHT * 3),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Bounty Missions"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Bounty Missions"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Bounty Missions"),
                () ->
                {
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
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Exit"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Exit"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Exit"),
                () -> {});

        this.addClickable(exitButton);
        this.addLeftOverlay(exitButton);

        Overlay playerInventory = new Overlay(new Point());
        Displayable piBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        playerInventory.add(piBackground);
        playerInventory.add(new StringDisplayable( new Point(16, 64), () -> " Player MONEY: " + currentPlayer.getMyWallet().getCurrencyBalance()));
        playerInventory.add(new StringDisplayable( new Point(WIDTH/2, 64), () -> " Trading Post MONEY: " + currentTP.getWallet().getCurrencyBalance()));

        //Adding playerInventory item displayables
        for(int i = 0; i < currentPlayer.getActiveShip().getInventory().getcurrItemsNum(); i++){
            Button playerItem = new Button(new Point((160*i) + (i*5) + 5, 100),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, currentPlayer.getActiveShip().getInventory().getItem(i).getName()),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, currentPlayer.getActiveShip().getInventory().getItem(i).getName()),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, currentPlayer.getActiveShip().getInventory().getItem(i).getName()),
                    () ->
                    {
                        currentTP.getWallet().decreaseCurrencyBalance(5);
                        currentPlayer.getMyWallet().increaseCurrencyBalance(5);
                    });

            playerInventory.addClickable(playerItem);
            playerInventory.add(playerItem);
        }

        Button itemSell = new Button(new Point(WIDTH/5,HEIGHT ),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Sell"),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Sell"),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Sell"),
                () ->
                {
                    currentPlayer.getMyWallet().increaseCurrencyBalance(5);
                    currentTP.getWallet().decreaseCurrencyBalance(5);
                });
        playerInventory.addClickable(itemSell);
        playerInventory.add(itemSell);
        this.playerInventory = playerInventory;

        Overlay tpInventory = new Overlay(new Point());
        Displayable tpBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        tpInventory.add(tpBackground);
        tpInventory.add(new StringDisplayable( new Point(16, 64), () -> "Player MONEY: " + currentPlayer.getMyWallet().getCurrencyBalance()));
        tpInventory.add(new StringDisplayable( new Point(WIDTH/2, 64), () -> " Trading Post MONEY: " + currentTP.getWallet().getCurrencyBalance()));

        //Adding tpInventory item displayables
        for(int i = 0; i < currentTP.getInventory().getcurrItemsNum(); i++){
//            Button tpItem = new Button(new Point( 160*(i) + 5, 100));
//            Displayable itemBack = new ImageDisplayable(new Point(0,0),
//                    ImageFactory.makeCenterLabeledRect(150, HEIGHT, Color.blue, Color.GRAY, Color.white, currentTP.getInventory().getItem(i).getName()));
//            tpItem.add(itemBack);


            Button tpItem = new Button(new Point((160*i) + (i*5) + 5, 100),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, currentTP.getInventory().getItem(i).getName()),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, currentTP.getInventory().getItem(i).getName()),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, currentTP.getInventory().getItem(i).getName()),
                    () ->
                    {
                        currentPlayer.getMyWallet().decreaseCurrencyBalance(5);
                        currentTP.getWallet().increaseCurrencyBalance(5);
                    });

            tpInventory.addClickable(tpItem);
            tpInventory.add(tpItem);
        }


        Button itemBuy = new Button(new Point(WIDTH/5,HEIGHT*4),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "" + this.selectedItem),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, "" + this.selectedItem),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "" + this.selectedItem),
                () ->
                {
                   currentPlayer.getMyWallet().decreaseCurrencyBalance(5);
                   currentTP.getWallet().increaseCurrencyBalance(5);
                });
        tpInventory.addClickable(itemBuy);
        tpInventory.add(itemBuy);
        this.tpInventory = tpInventory;

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

    // Placeholder money example stuff goes here
//    public int getMoney() { return money; }
//    public void modifyMoney(int newMoney) { money += newMoney; }


}
