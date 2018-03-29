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
import guiframework.Uberstate;
import guiframework.control.ClickableControlstate;
import guiframework.gui2d.Drawstate;
import guiframework.gui2d.ImageFactory;
import guiframework.control.clickable.Button;
import guiframework.control.clickable.ItemButton;
import guiframework.control.clickable.Overlay;
import guiframework.gui2d.displayable.Displayable;
import guiframework.gui2d.displayable.ImageDisplayable;
import guiframework.gui2d.displayable.StringDisplayable;
import guiframework.gui3d.Renderstate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TradingPostUberstate extends Uberstate
{
    private static final int HEIGHT = 200;
    private static final int WIDTH = 800;
    private static final int MARGIN = 10;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private TradingPost currentTP;
    private Player currentPlayer;
    private int selectedItem = 0;
    private int hoveredItem = 0;
    private int selectedBounty = 0;
    private int hoveredBounty = 0;
    private ImageDisplayable itemInfo;
    private ImageDisplayable bountyInfo;
    private List<ItemButton> playerItems;
    private List<ItemButton> tpItems;
    private List<Button> bountyButtons;
    private Overlay playerInventoryOverlay = new Overlay(new Point());
    private Overlay tpInventoryOverlay = new Overlay(new Point());
    private Overlay bountyListOverlay = new Overlay(new Point());
    private Overlay activeOverlay = new Overlay(new Point());
//    private Overlay inactiveOverlay = new Overlay(new Point());
    private Overlay activeSelectedOverlay = new Overlay(new Point());
    private Inventory playerInventory;
    private Inventory tpInventory;
//    private List<ItemButton> activeItems;
//    private List<ItemButton> inacvtiveItems;
    private Wallet playerWallet;
    private Wallet tpWallet;

    public TradingPostUberstate(Renderstate renderstate) {
        super(new Drawstate(), renderstate, new ClickableControlstate());
        //todo: Figure out how player and trading post are passed to Drawstate properly. Temporarily adding test player and trading post.
        currentPlayer = new Player();
        currentPlayer.getMyWallet().increaseCurrencyBalance(1500);
        Ship ship = new Ship(currentPlayer, new ShipHull(1000, 1000, 30, Rarity.COMMON));
        currentPlayer.getShipHangar().addShip(ship);
        currentPlayer.setActiveShip(ship);
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));

        BountyMission m = new BountyMission(2000, "Spock");
        m.completeMission();
        currentPlayer.setCurrentBountyMission(m);

        currentTP = new TradingPost(new Inventory(20), new Wallet(500), new ArrayList<BountyMission>());
        currentTP.getInventory().addItem(new FuelConsumable(300, 70));
        currentTP.getInventory().addItem(new FuelConsumable(400, 80));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));

        currentTP.getBountyList().add(new BountyMission(1000, "Klingon"));
        currentTP.getBountyList().add(new BountyMission(1000, "Dalek"));
        currentTP.getBountyList().add(new BountyMission(1000, "Jeff"));

        playerInventory = currentPlayer.getActiveShip().getInventory();
        tpInventory = currentTP.getInventory();

        playerWallet = currentPlayer.getMyWallet();
        tpWallet = currentTP.getWallet();

        playerItems = new ArrayList<ItemButton>();
        tpItems = new ArrayList<ItemButton>();
        bountyButtons = new ArrayList<Button>();

//        activeItems = new ArrayList<ItemButton>();
//        inacvtiveItems = new ArrayList<ItemButton>();


        //todo: add space background here
//        this.addUnderlay(Displayable.NULL);
//
//        ImageDisplayable background =
//                new ImageDisplayable(new Point(WIDTH,0), ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT*5, Color.black, Color.GRAY, Color.WHITE, "Space Background. Pretty stars and stuff."));
//
//        this.addUnderlay(background);
//
//        this.addUnderlay(Displayable.NULL);

        Drawstate drawstate = getDrawstate();
        ClickableControlstate controlstate = getControlstate();

        //Add title box
        ImageDisplayable tpTitle =
                new ImageDisplayable(new Point(0,0), ImageFactory.getTradingPostLabel());

        drawstate.addLeftOverlay(tpTitle);

        //Add Buy Button
        Button buyButton = new Button(new Point(0, HEIGHT),
                ImageFactory.getBuyButtonBase(),
                ImageFactory.getBuyButtonHover(),
                ImageFactory.getBuyButtonPress(),
                () ->
                {
//                    ItemButton itemBuy = new ItemButton(new Item(), new Point(),
//                            ImageFactory.getBuyButtonBase(),
//                            ImageFactory.getBuyButtonBase(),
//                            ImageFactory.getBuyButtonBase(),
//                            () ->
//                            {
//
//                            });
                    this.selectedItem = 0;

                    //Clear TP Items button list
                    for(int i = 0; i < tpItems.size(); i++) {
                        ItemButton button = tpItems.get(i);
                        tpInventoryOverlay.removeClickable(button);
                        tpInventoryOverlay.remove(button);
                    }
                    tpItems.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    drawstate.removeAllRightOverlays();
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.tpInventoryOverlay;
//                    inactiveOverlay = this.playerInventoryOverlay;
                    drawstate.addRightOverlay(this.tpInventoryOverlay);
                    controlstate.add(this.tpInventoryOverlay);

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
                        int x = (180*(i%4)) + ((i%4)*MARGIN) + MARGIN;
                        int y = 100 +(100*(i/4));

                        ItemButton tpItem = new ItemButton(item, new Point(x, y),
                                ImageFactory.makeCenterLabeledRect(180, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, item.getName()),
                                ImageFactory.makeCenterLabeledRect(180, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, item.getName()),
                                ImageFactory.makeCenterLabeledRect(180, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, item.getName()),
                                () ->
                                {
                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                    tpInventoryOverlay.remove(tpItemSelected);
                                    selectedItem = tpInventory.getIndex(item);
                                    tpInventoryOverlay.addClickable(tpItemSelected);
                                    tpInventoryOverlay.add(tpItemSelected);
                                    this.activeSelectedOverlay = tpItemSelected;
                                },
                                () ->
                                {

                                },
                                //enter function: Display Item info
                                () ->
                                {
                                    hoveredItem = tpInventory.getIndex(item);
                                    int xHover = tpItems.get(hoveredItem).getOrigin().x;
                                    int yHover = tpItems.get(hoveredItem).getOrigin().y;
                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
                                            ImageFactory.makeCenterLabeledRect(90, HEIGHT/10, Color.BLUE, Color.GRAY, Color.WHITE, "" + hoveredItem));
                                    tpInventoryOverlay.add(itemInfo);
                                },
                                //exit function
                                () ->
                                {
                                    tpInventoryOverlay.remove(itemInfo);
                                });

                        tpItems.add(tpItem);
                        tpInventoryOverlay.addClickable(tpItem);
                        tpInventoryOverlay.add(tpItem);
                    }
                });

        controlstate.add(buyButton);
        drawstate.addLeftOverlay(buyButton);

        //Add Sell Button
        Button sellButton = new Button(new Point(0, (HEIGHT * 2) + 20),
                ImageFactory.getSellButtonBase(),
                ImageFactory.getSellButtonHover(),
                ImageFactory.getSellButtonPress(),
                () ->
                {
                    this.selectedItem = 0;

                    //Clear Player Item button list
                    for(int i = 0; i < playerItems.size(); i++) {
                        ItemButton button = playerItems.get(i);
                        playerInventoryOverlay.removeClickable(button);
                        playerInventoryOverlay.remove(button);
                    }
                    playerItems.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    drawstate.removeAllRightOverlays();
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.playerInventoryOverlay;
//                    inactiveOverlay = this.tpInventoryOverlay;
                    drawstate.addRightOverlay(this.playerInventoryOverlay);
                    controlstate.add(this.playerInventoryOverlay);

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
                        int x = (180*(i%4)) + ((i%4)*MARGIN) + MARGIN;
                        int y = 100 +(100*(i/4));

                        ItemButton playerItem = new ItemButton(item, new Point(x, y),
                                ImageFactory.makeCenterLabeledRect(180, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, item.getName()),
                                ImageFactory.makeCenterLabeledRect(180, HEIGHT/5, Color.RED, Color.GRAY, Color.WHITE, item.getName()),
                                ImageFactory.makeCenterLabeledRect(180, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, item.getName()),
                                () ->
                                {
                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                    playerInventoryOverlay.remove(playerItemSelected);
                                    selectedItem = playerInventory.getIndex(item);
                                    playerInventoryOverlay.addClickable(playerItemSelected);
                                    playerInventoryOverlay.add(playerItemSelected);
                                    this.activeSelectedOverlay = playerItemSelected;
                                },
                                () ->
                                {

                                },
                                //enter function: Display Item info
                                () ->
                                {
                                    hoveredItem = playerInventory.getIndex(item);
                                    int xHover = playerItems.get(hoveredItem).getOrigin().x;
                                    int yHover = playerItems.get(hoveredItem).getOrigin().y;
                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
                                            ImageFactory.makeCenterLabeledRect(90, HEIGHT/10, Color.BLUE, Color.GRAY, Color.WHITE, "" + hoveredItem));
                                    playerInventoryOverlay.add(itemInfo);
                                },
                                //exit function
                                () ->
                                {
                                    playerInventoryOverlay.remove(itemInfo);
                                });

                        playerItems.add(playerItem);
                        playerInventoryOverlay.addClickable(playerItem);
                        playerInventoryOverlay.add(playerItem);
                    }
                });

        controlstate.add(sellButton);
        drawstate.addLeftOverlay(sellButton);

        //Add Bounty Missions Button
        Button bountyButton = new Button(new Point(0, HEIGHT * 3),
                ImageFactory.getBountyButtonBase(),
                ImageFactory.getBountyButtonHover(),
                ImageFactory.getBountyButtonPress(),
                () ->
                {
                    //Clear bounty button list
                    for(int i = 0; i < bountyButtons.size(); i++) {
                        Button button = bountyButtons.get(i);
                        bountyListOverlay.removeClickable(button);
                        bountyListOverlay.remove(button);
                    }
                    bountyButtons.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    drawstate.removeAllRightOverlays();
                    drawstate.addRightOverlay(this.bountyListOverlay);
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.bountyListOverlay;
                    controlstate.add(this.bountyListOverlay);

                    //player has a current mission
                    if(currentPlayer.getCurrentBountyMission() != null){
                        BountyMission playerMission = currentPlayer.getCurrentBountyMission();
                        if(playerMission.isCompleted()) {
                            Button turnInMission = new Button(new Point(MARGIN,HEIGHT ),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Turn in Mission"),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Turn in Mission"),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Turn in Mission"),
                                    () ->
                                    {
                                        playerWallet.increaseCurrencyBalance(playerMission.getCurrencyValue());
                                        currentPlayer.setCurrentBountyMission(null);
                                        bountyListOverlay.remove(bountyButtons.get(0));
                                        bountyListOverlay.removeClickable(bountyButtons.get(0));;
                                        bountyButtons.clear();

                                        for(int i = 0; i < currentTP.getBountyList().size(); i++) {
                                            BountyMission mission = currentTP.getBountyList().get(i);
                                            int x = (160*(i%4)) + ((i%4)*MARGIN) + MARGIN;
                                            int y = 100*i + 100 + (i * MARGIN);

                                            Button bountyListing = new Button(new Point(MARGIN, y),
                                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.WHITE, Color.GRAY, Color.BLACK, mission.getEnemyType()),
                                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.RED, Color.GRAY, Color.WHITE, mission.getEnemyType()),
                                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.ORANGE, Color.GRAY, Color.BLACK, mission.getEnemyType()),
                                                    () ->
                                                    {

                                                    },
                                                    () ->
                                                    {

                                                    },
                                                    //enter function: Display mission info
                                                    () ->
                                                    {
                                                        hoveredBounty = currentTP.getBountyList().indexOf(mission);
                                                        int yHover = bountyButtons.get(hoveredBounty).getOrigin().y;
                                                        this.bountyInfo = new ImageDisplayable(new Point(WIDTH - MARGIN - 80, yHover + HEIGHT/4),
                                                                ImageFactory.makeCenterLabeledRect(80, HEIGHT/10, Color.BLUE, Color.GRAY, Color.WHITE, "" + hoveredBounty));
                                                        bountyListOverlay.add(bountyInfo);
                                                    },
                                                    //exit function
                                                    () ->
                                                    {
                                                        bountyListOverlay.remove(bountyInfo);
                                                    });

                                            bountyButtons.add(bountyListing);
                                            bountyListOverlay.addClickable(bountyListing);
                                            bountyListOverlay.add(bountyListing);
                                        }
                                    });
                            bountyButtons.add(turnInMission);
                            bountyListOverlay.add(turnInMission);
                            bountyListOverlay.addClickable(turnInMission);
                        }
                        else {
                            ImageDisplayable incompleteMission = new ImageDisplayable(new Point(MARGIN, HEIGHT),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Your current mission is incomplete"));
                            bountyListOverlay.add(incompleteMission);
                        }
                    }

                    //Player has no current mission
                    else {
                        System.out.println("No mission");
                        for(int i = 0; i < currentTP.getBountyList().size(); i++) {
                            BountyMission mission = currentTP.getBountyList().get(i);
                            int x = MARGIN;
                            int y = 100*i + 100 + (i * MARGIN);

                            Button bountyListing = new Button(new Point(x, y),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.WHITE, Color.GRAY, Color.BLACK, mission.getEnemyType()),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.RED, Color.GRAY, Color.WHITE, mission.getEnemyType()),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.ORANGE, Color.GRAY, Color.BLACK, mission.getEnemyType()),
                                    () ->
                                    {

                                    },
                                    () ->
                                    {

                                    },
                                    //enter function: Display mission info
                                    () ->
                                    {
                                        hoveredBounty = currentTP.getBountyList().indexOf(mission);
                                        int yHover = bountyButtons.get(hoveredBounty).getOrigin().y;
                                        this.bountyInfo = new ImageDisplayable(new Point(WIDTH - MARGIN - 80, yHover + HEIGHT/4),
                                                ImageFactory.makeCenterLabeledRect(80, HEIGHT/10, Color.BLUE, Color.GRAY, Color.WHITE, "" + hoveredBounty));
                                        bountyListOverlay.add(bountyInfo);
                                    },
                                    //exit function
                                    () ->
                                    {
                                        bountyListOverlay.remove(bountyInfo);
                                    });

                            bountyButtons.add(bountyListing);
                            bountyListOverlay.addClickable(bountyListing);
                            bountyListOverlay.add(bountyListing);
                        }
                    }
                });

        controlstate.add(bountyButton);
        drawstate.addLeftOverlay(bountyButton);

        //Add Exit Button
        Button exitButton = new Button(new Point(0, HEIGHT * 4),
                ImageFactory.getExitButtonBase(),
                ImageFactory.getExitButtonHover(),
                ImageFactory.getExitButtonPress(),
                () -> {});

        controlstate.add(exitButton);
        drawstate.addLeftOverlay(exitButton);

        //Create Player Inventory Overlay
        Overlay playerInventoryOverlay = new Overlay(new Point());
        Displayable piBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        playerInventoryOverlay.add(piBackground);
        playerInventoryOverlay.add(new StringDisplayable( new Point(16, 16), () -> " Player Inventory"));
        playerInventoryOverlay.add(new StringDisplayable( new Point(16, 64), () -> " Player MONEY: " + playerWallet.getCurrencyBalance()));
        playerInventoryOverlay.add(new StringDisplayable( new Point(WIDTH/2, 64), () -> " Trading Post MONEY: " + tpWallet.getCurrencyBalance()));
        this.playerInventoryOverlay = playerInventoryOverlay;

        //Create TP Inventory Overlay
        Overlay tpInventoryOverlay = new Overlay(new Point());
        Displayable tpBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        tpInventoryOverlay.add(tpBackground);
        tpInventoryOverlay.add(new StringDisplayable( new Point(16, 16), () -> " Trading Post Inventory"));
        tpInventoryOverlay.add(new StringDisplayable( new Point(16, 64), () -> "Player MONEY: " + currentPlayer.getMyWallet().getCurrencyBalance()));
        tpInventoryOverlay.add(new StringDisplayable( new Point(WIDTH/2, 64), () -> " Trading Post MONEY: " + currentTP.getWallet().getCurrencyBalance()));
        this.tpInventoryOverlay = tpInventoryOverlay;

        //Create Bounty List Overlay
        Overlay bountyListOverlay =  new Overlay(new Point());
        Displayable bountyBackground =  new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(WIDTH, HEIGHT*5, Color.WHITE, Color.GRAY));
        bountyListOverlay.add(bountyBackground);
        bountyListOverlay.add(new StringDisplayable(new Point(16, 64), () -> "Bounty List"));
        this.bountyListOverlay = bountyListOverlay;
    }

    private void redrawButtons(){
        //redraw tpInventoryOverlay
        for(int i = 0; i < tpItems.size(); i++) {
            tpItems.get(i).getOrigin().setLocation((180*(i%4)) + ((i%4)*MARGIN) + MARGIN, 100 +(100*(i/4)));
        }

        //redraw playerInventoryOverlay
        for(int i = 0; i < playerItems.size(); i++) {
            playerItems.get(i).getOrigin().setLocation((180*(i%4)) + ((i%4)*MARGIN) + MARGIN, 100+(100*(i/4)));
        }
    }
    // Placeholder money example stuff goes here
//    public int getMoney() { return money; }
//    public void modifyMoney(int newMoney) { money += newMoney; }


}
