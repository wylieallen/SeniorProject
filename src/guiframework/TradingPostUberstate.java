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
import guiframework.clickable.ItemButton;
import guiframework.clickable.Overlay;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TradingPostUberstate extends Uberstate{
    private static final int HEIGHT = 200;
    private static final int WIDTH = 800;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private TradingPost currentTP;
    private Player currentPlayer;
    private int selectedItem = 0;
    private Overlay playerInventory = new Overlay(new Point());
    private Overlay tpInventory = new Overlay(new Point());
    private Overlay bountyList = new Overlay(new Point());
    private Overlay activeOverlay = new Overlay(new Point());
    private Overlay activeSelectedOverlay = new Overlay(new Point());

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
        currentTP.getInventory().addItem(new FuelConsumable(400, 80));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));


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
                new ImageDisplayable(new Point(0,0),ImageFactory.getTradingPostLabel());

        this.addLeftOverlay(tpTitle);

        //Add Buy Button
        Button buyButton = new Button(new Point(0, HEIGHT),
                ImageFactory.getBuyButtonBase(),
                ImageFactory.getBuyButtonHover(),
                ImageFactory.getBuyButtonPress(),
                () ->
                {
                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
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
                ImageFactory.getSellButtonBase(),
                ImageFactory.getSellButtonHover(),
                ImageFactory.getSellButtonPress(),
                () ->
                {
                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
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

        Overlay playerInventory = new Overlay(new Point());
        Displayable piBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        playerInventory.add(piBackground);
        playerInventory.add(new StringDisplayable( new Point(16, 16), () -> " Player Inventory"));
        playerInventory.add(new StringDisplayable( new Point(16, 64), () -> " Player MONEY: " + currentPlayer.getMyWallet().getCurrencyBalance()));
        playerInventory.add(new StringDisplayable( new Point(WIDTH/2, 64), () -> " Trading Post MONEY: " + currentTP.getWallet().getCurrencyBalance()));




        //Add selected item Overlay
        Overlay playerItemSelected = new Overlay(new Point(WIDTH/5,HEIGHT*3));
        Displayable pisBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLUE, Color.GRAY));
        playerItemSelected.add(pisBackground);
        playerItemSelected.add(new StringDisplayable( new Point(16, 16), () -> "" + currentPlayer.getActiveShip().getInventory().getItem(selectedItem).getCurrencyValue(), Color.RED, this.font));

        Button itemSell = new Button(new Point(16,100),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "Sell Item"),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, "Sell Item"),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "Sell Item"),
                () ->
                {
                });
        playerItemSelected.addClickable(itemSell);
        playerItemSelected.add(itemSell);

        //Adding playerInventory item displayables
        for(int i = 0; i < currentPlayer.getActiveShip().getInventory().getcurrItemsNum(); i++){

            Item item = currentPlayer.getActiveShip().getInventory().getItem(i);

            ItemButton playerItem = new ItemButton(item, new Point((160*i) + (i*5) + 5, 100),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, item.getName()),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, item.getName()),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, item.getName()),
                    () ->
                    {
                        playerInventory.removeClickable(playerItemSelected);
                        playerInventory.remove(playerItemSelected);
                        selectedItem = currentPlayer.getActiveShip().getInventory().getIndex(item);
                        playerInventory.addClickable(playerItemSelected);
                        playerInventory.add(playerItemSelected);
                        this.activeSelectedOverlay = playerItemSelected;
                    });

            playerInventory.addClickable(playerItem);
            playerInventory.add(playerItem);
        }


        this.playerInventory = playerInventory;

        Overlay tpInventory = new Overlay(new Point());
        Displayable tpBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        tpInventory.add(tpBackground);
        tpInventory.add(new StringDisplayable( new Point(16, 16), () -> " Trading Post Inventory"));
        tpInventory.add(new StringDisplayable( new Point(16, 64), () -> "Player MONEY: " + currentPlayer.getMyWallet().getCurrencyBalance()));
        tpInventory.add(new StringDisplayable( new Point(WIDTH/2, 64), () -> " Trading Post MONEY: " + currentTP.getWallet().getCurrencyBalance()));


        //Add selected item Overlay
        Overlay tpItemSelected = new Overlay(new Point(WIDTH/5,HEIGHT*3));
        Displayable tpiBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLUE, Color.GRAY));
        tpItemSelected.add(tpiBackground);
        tpItemSelected.add(new StringDisplayable( new Point(16, 16), () -> "" + currentTP.getInventory().getItem(selectedItem).getCurrencyValue(), Color.RED, this.font));

        Button itemBuy = new Button(new Point(16,100),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "Buy Item"),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, "Buy Item"),
                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "Buy Item"),
                () ->
                {
                });
        tpItemSelected.addClickable(itemBuy);
        tpItemSelected.add(itemBuy);

        //Adding tpInventory item displayables
        for(int i = 0; i < currentTP.getInventory().getcurrItemsNum(); i++){
            Item item = currentTP.getInventory().getItem(i);

            ItemButton tpItem = new ItemButton(item, new Point((160*i) + (i*5) + 5, 100),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, item.getName()),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, item.getName()),
                    ImageFactory.makeCenterLabeledRect(160, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, item.getName()),
                    () ->
                    {
                        tpInventory.removeClickable(tpItemSelected);
                        tpInventory.remove(tpItemSelected);
                        selectedItem = currentTP.getInventory().getIndex(item);
                        tpInventory.addClickable(tpItemSelected);
                        tpInventory.add(tpItemSelected);
                        this.activeSelectedOverlay = tpItemSelected;
                    });

            tpInventory.addClickable(tpItem);
            tpInventory.add(tpItem);
        }

//        tpInventory.addClickable(tpItemSelected);
//        tpInventory.add(tpItemSelected);
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
