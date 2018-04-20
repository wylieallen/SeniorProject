package gameview.drawstate;

import Model.Items.Consumables.FuelConsumable;
import Model.Items.Consumables.HealthConsumable;
import Model.Items.Consumables.ShieldConsumable;
import Model.Items.Inventory;
import Model.Items.Item;
import Model.Items.RandomItemGenerator;
import Model.Pilot.Player;
import Model.Ship.Ship;
import Model.Ship.ShipParts.ShipHull;
import Model.TradingPost.BountyMission;
import Model.TradingPost.TradingPost;
import Model.TradingPost.Wallet;
import Utility.Rarity;
import gameview.TransitionObserver;
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
    private static final int ITEMSPERPAGE = 12;
    private static final int ITEMSPERLINE = 6;
    private static final int ITEMLEFTOFFSET = 0;
    private static final int YMARGIN = 350;
    private static final int YINITIAL = 200;
    private static final int ITEMINFOWIDTH = 250;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private static final Font fontSmall = new Font("Arvo", Font.PLAIN, 20);
    private TradingPost currentTP;
    private Player currentPlayer;
    private int selectedItem = 0;
    private int tpCurrentPageNumber = 1;
    private int playerCurrentPageNumber = 1;
    private int tpMaxPage;
    private int playerMaxPage;
    private List<ItemButton> playerItems;
    private List<ItemButton> tpItems;
    private List<Overlay> bountyMissions;
    private Overlay playerInventoryOverlay = new Overlay(new Point());
    private Overlay tpInventoryOverlay = new Overlay(new Point());
    private Overlay bountyListOverlay = new Overlay(new Point());
    private Overlay activeOverlay = new Overlay(new Point());
    private Overlay activeSelectedOverlay = new Overlay(new Point());
    private Overlay activePageOverlay = new Overlay(new Point());
    private Inventory playerInventory;
    private Inventory tpInventory;
    private Wallet playerWallet;
    private Wallet tpWallet;

    private TransitionObserver transitionObserver;

    public TradingPostUberstate(TransitionObserver transitionObserver, Renderstate renderstate, Player player, boolean initialized) {
        super(new Drawstate(), renderstate, new ClickableControlstate());
        //todo: Figure out how player and trading post are passed to Drawstate properly. Temporarily adding test player and trading post.
        currentPlayer = player;
        this.transitionObserver = transitionObserver;
        if(!initialized)
        {
            currentPlayer.getMyWallet().increaseCurrencyBalance(1500);
            Ship ship = new Ship(currentPlayer, new ShipHull(1000, Rarity.COMMON, 1000, 40));
            currentPlayer.getShipHangar().addShip(ship);
            currentPlayer.setActiveShip(ship);
//        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));

            BountyMission m = new BountyMission(2000, 10, "Spock");
            m.completeMission();
            currentPlayer.setCurrentBountyMission(m);
        }


        currentTP = new TradingPost(new Inventory(40), new Wallet(50000), new ArrayList<BountyMission>());
//        currentTP.getInventory().addItem(new FuelConsumable(300, 70));

        RandomItemGenerator RIG = new RandomItemGenerator();
        for(int i =0; i < 40; i++)
            currentTP.getInventory().addItem(RIG.getRandomItem());


        currentTP.getBountyList().add(new BountyMission(1000, 20,"Klingon"));
        currentTP.getBountyList().add(new BountyMission(2000, 20,"Dalek"));
        currentTP.getBountyList().add(new BountyMission(3000, 40,"Jeff"));

        playerInventory = currentPlayer.getActiveShip().getInventory();
        tpInventory = currentTP.getInventory();

        playerMaxPage = ((playerInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
        tpMaxPage = ((tpInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;

        playerWallet = currentPlayer.getMyWallet();
        tpWallet = currentTP.getWallet();

        playerItems = new ArrayList<ItemButton>();
        tpItems = new ArrayList<ItemButton>();
        bountyMissions = new ArrayList<Overlay>();

        Drawstate drawstate = getDrawstate();
        ClickableControlstate controlstate = getControlstate();

        //Add space background
        ImageDisplayable spaceBackground =
                new ImageDisplayable(new Point(0,0), ImageFactory.getSpaceBackground());
        drawstate.addUnderlay(spaceBackground);

        //Add title box
        ImageDisplayable tpTitle =
                new ImageDisplayable(new Point(0,0), ImageFactory.getTradingPostLabel());

        drawstate.addLeftOverlay(tpTitle);

        //Add Buy Button
        Button buyButton = new Button(new Point(0, HEIGHT),
                ImageFactory.getBuyButtonBase(),
                ImageFactory.getBuyButtonBase(),
                ImageFactory.getBuyButtonBase(),
                () ->
                {
                    tpCurrentPageNumber = 1;
                    selectedItem = 0;

                    //Clear TP Items button list
                    for(int i = 0; i < tpItems.size(); i++) {
                        ItemButton button = tpItems.get(i);
                        tpInventoryOverlay.removeClickable(button);
                        tpInventoryOverlay.remove(button);
                    }
                    tpItems.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    activeOverlay.remove(activePageOverlay);
                    activeOverlay.removeClickable(activePageOverlay);
                    drawstate.removeOverlay(activeOverlay);
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.tpInventoryOverlay;
                    drawstate.addCenterOverlay(this.tpInventoryOverlay);
                    controlstate.add(this.tpInventoryOverlay);

                    //Add selected item Overlay
                    Overlay tpItemSelected = new Overlay(new Point(0,0));
                    ImageDisplayable tpiBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220, Color.BLUE, Color.GRAY));
                    ImageDisplayable tpItemName = new ImageDisplayable(new Point(0,0),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, tpInventory.getItem(selectedItem).getName()));
                    ImageDisplayable tpItemPrice = new ImageDisplayable(new Point(0,55),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, tpInventory.getItem(selectedItem).getAttributes().get(0)));
                    ImageDisplayable tpItemInfo = new ImageDisplayable(new Point(0, 110),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, ""));
                    //tpItemSelected.add(tpiBackground);
                    tpItemSelected.add(tpItemName);
                    tpItemSelected.add(tpItemPrice);
                    tpItemSelected.add(tpItemInfo);
                    if(tpInventory.getItem(selectedItem).getAttributes().size() > 1) {
                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, tpInventory.getItem(selectedItem).getAttributes().get(1)));
                    }

                    Button itemBuy = new Button(new Point(0,165),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Buy Item"),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.WHITE, "Buy Item"),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Buy Item"),
                            () ->
                            {
                                Item item = tpInventory.getItem(this.selectedItem);
                                //scale item price down based on players charisma level
                                int scaledPriceDown = (int) (item.getCurrencyValue()*(1-currentPlayer.getPilotStats().charismaScaling()));
                                System.out.println(scaledPriceDown);
                                if(playerWallet.getCurrencyBalance() >= scaledPriceDown) {
                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                    activeOverlay.remove(tpItemSelected);
                                    tpWallet.increaseCurrencyBalance(scaledPriceDown);
                                    playerWallet.decreaseCurrencyBalance(scaledPriceDown);
                                    tpInventory.removeItem(item);
                                    playerInventory.addItem(item);
                                    playerMaxPage = ((playerInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
                                    tpMaxPage = ((tpInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
                                    if(tpItems.size() == 1 && tpCurrentPageNumber > 1)
                                        tpCurrentPageNumber--;

                                    //redraw buttons on current page
                                    //Clear TP Items button list
                                    for(int i = 0; i < tpItems.size(); i++) {
                                        ItemButton ibutton = tpItems.get(i);
                                        tpInventoryOverlay.removeClickable(ibutton);
                                        tpInventoryOverlay.remove(ibutton);
                                    }
                                    tpItems.clear();

                                    //add items of new page
                                    for(int i = ITEMSPERPAGE*(tpCurrentPageNumber-1); i < tpInventory.getcurrItemsNum() && i < (ITEMSPERPAGE*tpCurrentPageNumber); i++){
                                        Item newitem = tpInventory.getItem(i);
                                        int pos = i - (ITEMSPERPAGE*(tpCurrentPageNumber-1));
                                        int x = ITEMLEFTOFFSET + (180*(pos%ITEMSPERLINE)) + ((pos%ITEMSPERLINE)*MARGIN) + MARGIN;
                                        int y = YINITIAL + (YMARGIN*(pos/ITEMSPERLINE));

                                        ItemButton tpItem = new ItemButton(newitem, new Point(x, y),
                                                newitem.getImage(),
                                                newitem.getImage(),
                                                newitem.getImage(),
                                                () ->
                                                {
                                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                                    tpInventoryOverlay.remove(tpItemSelected);
                                                    selectedItem = tpInventory.getIndex(newitem);

                                                    int scaledPrice = (int)(newitem.getCurrencyValue()*(1-currentPlayer.getPilotStats().charismaScaling()));

                                                    //reposition tpItemSelected then add back
                                                    tpItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    tpiBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    tpItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getName()));
                                                    tpItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getAttributes().get(0) + "(" + scaledPrice + ")"));
                                                    if(newitem.getAttributes().size() > 1) {
                                                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, newitem.getAttributes().get(1)));
                                                    }
                                                    else
                                                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, ""));

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

                                                },
                                                //exit function
                                                () ->
                                                {

                                                });

                                        tpItems.add(tpItem);
                                        tpInventoryOverlay.addClickable(tpItem);
                                        tpInventoryOverlay.add(tpItem);
                                    }
                                    System.out.println("Item bought!");
                                }
                                else
                                    System.out.println("You do not have enough money to buy this item!");
                            });
                    tpItemSelected.addClickable(itemBuy);
                    tpItemSelected.add(itemBuy);

                    //Adding tpInventoryOverlay item displayables
                    for(int i = 0; i < tpInventory.getcurrItemsNum() && i < ITEMSPERPAGE; i++){
                        Item item = tpInventory.getItem(i);
                        int x = ITEMLEFTOFFSET + (180*(i%ITEMSPERLINE)) + ((i%ITEMSPERLINE)*MARGIN) + MARGIN;
                        int y = YINITIAL + (YMARGIN*(i/ITEMSPERLINE));

                        ItemButton tpItem = new ItemButton(item, new Point(x, y),
                                item.getImage(),
                                item.getImage(),
                                item.getImage(),
                                () ->
                                {
                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                    tpInventoryOverlay.remove(tpItemSelected);
                                    selectedItem = tpInventory.getIndex(item);

                                    int scaledPrice = (int)(item.getCurrencyValue()*(1-currentPlayer.getPilotStats().charismaScaling()));

                                    //reposition tpItemSelected then add back
                                    tpItemSelected.getOrigin().setLocation(x-50,y+120);
                                    tpiBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                    tpItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                    tpItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0) + "(" + scaledPrice + ")"));
                                    if(item.getAttributes().size() > 1) {
                                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, item.getAttributes().get(1)));
                                    }
                                    else
                                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, ""));

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

                                },
                                //exit function
                                () ->
                                {

                                });

                        tpItems.add(tpItem);
                        tpInventoryOverlay.addClickable(tpItem);
                        tpInventoryOverlay.add(tpItem);
                    }

                    //Paging for TP Inventory
                    Overlay tpPageOverlay = new Overlay(new Point(490,100));
                    tpPageOverlay.add(new StringDisplayable(new Point(40, 5), () -> " Page " + tpCurrentPageNumber,Color.GREEN, fontSmall));
                    Button tpPageDown = new Button(new Point(0,0),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.BLACK, Color.BLACK, Color.GREEN, "<"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.GREEN, Color.BLACK, "<"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.BLACK, Color.BLACK, Color.GREEN, "<"),
                            () ->
                            {
                                if(tpCurrentPageNumber > 1) {
                                    //remove all displayables from previous page
                                    selectedItem = 0;

                                    //Clear TP Items button list
                                    for(int i = 0; i < tpItems.size(); i++) {
                                        ItemButton button = tpItems.get(i);
                                        tpInventoryOverlay.removeClickable(button);
                                        tpInventoryOverlay.remove(button);
                                    }
                                    tpItems.clear();

                                    //remove selected overlay
                                    activeOverlay.remove(activeSelectedOverlay);
                                    activeOverlay.removeClickable(activeSelectedOverlay);

                                    //decrease page number
                                    tpCurrentPageNumber--;

                                    //add items of new page
                                    for(int i = ITEMSPERPAGE*(tpCurrentPageNumber-1); i < tpInventory.getcurrItemsNum() && i < (ITEMSPERPAGE*tpCurrentPageNumber); i++){
                                        Item item = tpInventory.getItem(i);
                                        int pos = i - (ITEMSPERPAGE*(tpCurrentPageNumber-1));
                                        int x = ITEMLEFTOFFSET + (180*(pos%ITEMSPERLINE)) + ((pos%ITEMSPERLINE)*MARGIN) + MARGIN;
                                        int y = YINITIAL + (YMARGIN*(pos/ITEMSPERLINE));

                                        ItemButton tpItem = new ItemButton(item, new Point(x, y),
                                                item.getImage(),
                                                item.getImage(),
                                                item.getImage(),
                                                () ->
                                                {
                                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                                    tpInventoryOverlay.remove(tpItemSelected);
                                                    selectedItem = tpInventory.getIndex(item);

                                                    int scaledPrice = (int)(item.getCurrencyValue()*(1-currentPlayer.getPilotStats().charismaScaling()));

                                                    //reposition tpItemSelected then add back
                                                    tpItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    tpiBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    tpItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                                    tpItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0) + "(" + scaledPrice + ")"));
                                                    if(item.getAttributes().size() > 1) {
                                                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, item.getAttributes().get(1)));
                                                    }
                                                    else
                                                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, ""));

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

                                                },
                                                //exit function
                                                () ->
                                                {

                                                });

                                        tpItems.add(tpItem);
                                        tpInventoryOverlay.addClickable(tpItem);
                                        tpInventoryOverlay.add(tpItem);
                                    }
                                }
                            });
                    tpPageOverlay.add(tpPageDown);
                    tpPageOverlay.addClickable(tpPageDown);
                    Button tpPageUp = new Button(new Point(112,0),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.BLACK, Color.BLACK, Color.GREEN, ">"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.GREEN, Color.BLACK, ">"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.BLACK, Color.BLACK, Color.GREEN, ">"),
                            () ->
                            {
                                if(tpCurrentPageNumber < tpMaxPage) {
                                    //remove all displayables from previous page
                                    selectedItem = 0;

                                    //Clear TP Items button list
                                    for(int i = 0; i < tpItems.size(); i++) {
                                        ItemButton button = tpItems.get(i);
                                        tpInventoryOverlay.removeClickable(button);
                                        tpInventoryOverlay.remove(button);
                                    }
                                    tpItems.clear();

                                    //remove selected overlay
                                    activeOverlay.remove(activeSelectedOverlay);
                                    activeOverlay.removeClickable(activeSelectedOverlay);

                                    //increase page number
                                    tpCurrentPageNumber++;

                                    //add items of new page
                                    for(int i = ITEMSPERPAGE*(tpCurrentPageNumber-1); i < tpInventory.getcurrItemsNum() && i < (ITEMSPERPAGE*tpCurrentPageNumber); i++){
                                        Item item = tpInventory.getItem(i);
                                        int pos = i - (ITEMSPERPAGE*(tpCurrentPageNumber-1));
                                        int x = ITEMLEFTOFFSET + (180*(pos%ITEMSPERLINE)) + ((pos%ITEMSPERLINE)*MARGIN) + MARGIN;
                                        int y = YINITIAL + (YMARGIN*(pos/ITEMSPERLINE));

                                        ItemButton tpItem = new ItemButton(item, new Point(x, y),
                                                item.getImage(),
                                                item.getImage(),
                                                item.getImage(),
                                                () ->
                                                {
                                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                                    tpInventoryOverlay.remove(tpItemSelected);
                                                    selectedItem = tpInventory.getIndex(item);

                                                    int scaledPrice = (int)(item.getCurrencyValue()*(1-currentPlayer.getPilotStats().charismaScaling()));

                                                    //reposition tpItemSelected then add back
                                                    tpItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    tpiBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    tpItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                                    tpItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0) + "(" + scaledPrice + ")"));
                                                    if(item.getAttributes().size() > 1) {
                                                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, item.getAttributes().get(1)));
                                                    }
                                                    else
                                                        tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, ""));

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

                                                },
                                                //exit function
                                                () ->
                                                {

                                                });

                                        tpItems.add(tpItem);
                                        tpInventoryOverlay.addClickable(tpItem);
                                        tpInventoryOverlay.add(tpItem);
                                    }
                                }
                            });
                    tpPageOverlay.add(tpPageUp);
                    tpPageOverlay.addClickable(tpPageUp);
                    this.activePageOverlay = tpPageOverlay;
                    tpInventoryOverlay.add(tpPageOverlay);
                    tpInventoryOverlay.addClickable(tpPageOverlay);
                });

        controlstate.add(buyButton);
        drawstate.addLeftOverlay(buyButton);

        //Add Sell Button
        Button sellButton = new Button(new Point(0, (HEIGHT * 2) + 20),
                ImageFactory.getSellButtonBase(),
                ImageFactory.getSellButtonBase(),
                ImageFactory.getSellButtonBase(),
                () ->
                {
                    playerCurrentPageNumber = 1;
                    selectedItem = 0;

                    //Clear Player Item button list
                    for(int i = 0; i < playerItems.size(); i++) {
                        ItemButton button = playerItems.get(i);
                        playerInventoryOverlay.removeClickable(button);
                        playerInventoryOverlay.remove(button);
                    }
                    playerItems.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    activeOverlay.remove(activePageOverlay);
                    activeOverlay.removeClickable(activePageOverlay);
                    drawstate.removeOverlay(activeOverlay);
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.playerInventoryOverlay;
                    drawstate.addCenterOverlay(this.playerInventoryOverlay);
                    controlstate.add(this.playerInventoryOverlay);

                    //Add selected item Overlay
                    Overlay playerItemSelected = new Overlay(new Point(0,0));
                    ImageDisplayable playerItemBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220, Color.BLUE, Color.GRAY));
                    ImageDisplayable playerItemName = new ImageDisplayable(new Point(0,0),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, playerInventory.getItem(selectedItem).getName()));
                    ImageDisplayable playerItemPrice = new ImageDisplayable(new Point(0,55),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, playerInventory.getItem(selectedItem).getAttributes().get(0)));
                    ImageDisplayable playerItemInfo = new ImageDisplayable(new Point(0,110),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, ""));
                    playerItemSelected.add(playerItemBackground);
                    playerItemSelected.add(playerItemName);
                    playerItemSelected.add(playerItemPrice);
                    playerItemSelected.add(playerItemInfo);
                    if(playerInventory.getItem(selectedItem).getAttributes().size() > 1)
                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, playerInventory.getItem(selectedItem).getAttributes().get(1)));

                    Button itemSell = new Button(new Point(0,165),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Sell Item"),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.WHITE, "Sell Item"),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Sell Item"),
                            () ->
                            {
                                Item item = playerInventory.getItem(this.selectedItem);
                                //scale item price up based on players charisma level
                                int scaledPriceUp = (int) (item.getCurrencyValue()*(1+currentPlayer.getPilotStats().charismaScaling()));
                                System.out.println(scaledPriceUp);
                                if(tpWallet.getCurrencyBalance() >= scaledPriceUp) {
                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                    playerInventoryOverlay.remove(playerItemSelected);
                                    playerWallet.increaseCurrencyBalance(scaledPriceUp);
                                    tpWallet.decreaseCurrencyBalance(scaledPriceUp);
                                    playerInventory.removeItem(item);
                                    tpInventory.addItem(item);
                                    playerMaxPage = ((playerInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
                                    tpMaxPage = ((tpInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
                                    if(playerItems.size() == 1 && playerCurrentPageNumber > 1)
                                        playerCurrentPageNumber--;

                                    //redraw buttons on current page
                                    //Clear TP Items button list
                                    for(int i = 0; i < playerItems.size(); i++) {
                                        ItemButton ibutton = playerItems.get(i);
                                        playerInventoryOverlay.removeClickable(ibutton);
                                        playerInventoryOverlay.remove(ibutton);
                                    }
                                    playerItems.clear();

                                    //add items of new page
                                    for(int i = ITEMSPERPAGE*(playerCurrentPageNumber-1); i < playerInventory.getcurrItemsNum() && i < (ITEMSPERPAGE*playerCurrentPageNumber); i++){
                                        Item newitem = playerInventory.getItem(i);
                                        int pos = i - (ITEMSPERPAGE*(playerCurrentPageNumber-1));
                                        int x = ITEMLEFTOFFSET + (180*(pos%ITEMSPERLINE)) + ((pos%ITEMSPERLINE)*MARGIN) + MARGIN;
                                        int y = YINITIAL + (YMARGIN*(pos/ITEMSPERLINE));

                                        ItemButton playerItem = new ItemButton(newitem, new Point(x, y),
                                                newitem.getImage(),
                                                newitem.getImage(),
                                                newitem.getImage(),
                                                () ->
                                                {
                                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                                    playerInventoryOverlay.remove(playerItemSelected);
                                                    selectedItem = playerInventory.getIndex(newitem);

                                                    int scaledPrice = (int) (newitem.getCurrencyValue()*(1+currentPlayer.getPilotStats().charismaScaling()));

                                                    //reposition playerItemSelected then add back
                                                    playerItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    playerItemBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    playerItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getName()));
                                                    playerItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getAttributes().get(0) + "(" + scaledPrice + ")"));
                                                    if(newitem.getAttributes().size() > 1)
                                                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getAttributes().get(1)));
                                                    else
                                                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, ""));

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

                                                },
                                                //exit function
                                                () ->
                                                {

                                                });

                                        playerItems.add(playerItem);
                                        playerInventoryOverlay.addClickable(playerItem);
                                        playerInventoryOverlay.add(playerItem);
                                    }

                                    System.out.println("Item sold!");
                                }
                                else
                                    System.out.println("Shop does not have enough money to buy this item!");
                            });
                    playerItemSelected.addClickable(itemSell);
                    playerItemSelected.add(itemSell);

                    //Adding playerInventoryOverlay item displayables
                    for(int i = 0; i < playerInventory.getcurrItemsNum() && i < ITEMSPERPAGE; i++){

                        Item item = playerInventory.getItem(i);
                        int x = ITEMLEFTOFFSET + (180*(i%ITEMSPERLINE)) + ((i%ITEMSPERLINE)*MARGIN) + MARGIN;
                        int y = YINITIAL + (YMARGIN*(i/ITEMSPERLINE));

                        ItemButton playerItem = new ItemButton(item, new Point(x, y),
                                item.getImage(),
                                item.getImage(),
                                item.getImage(),
                                () ->
                                {
                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                    playerInventoryOverlay.remove(playerItemSelected);
                                    selectedItem = playerInventory.getIndex(item);

                                    int scaledPrice = (int) (item.getCurrencyValue()*(1+currentPlayer.getPilotStats().charismaScaling()));

                                    //reposition playerItemSelected then add back
                                    playerItemSelected.getOrigin().setLocation(x-50,y+120);
                                    playerItemBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                    playerItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                    playerItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0) + "(" + scaledPrice + ")"));
                                    if(item.getAttributes().size() > 1)
                                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));
                                    else
                                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, ""));

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

                                },
                                //exit function
                                () ->
                                {

                                });

                        playerItems.add(playerItem);
                        playerInventoryOverlay.addClickable(playerItem);
                        playerInventoryOverlay.add(playerItem);
                    }

                    //Paging for Player Inventory
                    Overlay playerPageOverlay = new Overlay(new Point(490,100));
                    playerPageOverlay.add(new StringDisplayable(new Point(40, 5), () -> " Page " + playerCurrentPageNumber,Color.GREEN, fontSmall));
                    Button playerPageDown = new Button(new Point(0,0),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.BLACK, Color.BLACK, Color.GREEN, "<"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.GREEN, Color.BLACK, "<"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.BLACK, Color.BLACK, Color.GREEN, "<"),
                            () ->
                            {
                                if(playerCurrentPageNumber > 1) {
                                    //remove all displayables from previous page
                                    selectedItem = 0;

                                    //Clear TP Items button list
                                    for(int i = 0; i < playerItems.size(); i++) {
                                        ItemButton button = playerItems.get(i);
                                        playerInventoryOverlay.removeClickable(button);
                                        playerInventoryOverlay.remove(button);
                                    }
                                    playerItems.clear();

                                    //remove selected overlay
                                    activeOverlay.remove(activeSelectedOverlay);
                                    activeOverlay.removeClickable(activeSelectedOverlay);

                                    //decrease page number
                                    playerCurrentPageNumber--;

                                    //add items of new page
                                    for(int i = ITEMSPERPAGE*(playerCurrentPageNumber-1); i < playerInventory.getcurrItemsNum() && i < (ITEMSPERPAGE*playerCurrentPageNumber); i++){
                                        Item item = playerInventory.getItem(i);
                                        int pos = i - (ITEMSPERPAGE*(playerCurrentPageNumber-1));
                                        int x = ITEMLEFTOFFSET + (180*(pos%ITEMSPERLINE)) + ((pos%ITEMSPERLINE)*MARGIN) + MARGIN;
                                        int y = YINITIAL + (YMARGIN*(pos/ITEMSPERLINE));

                                        ItemButton playerItem = new ItemButton(item, new Point(x, y),
                                                item.getImage(),
                                                item.getImage(),
                                                item.getImage(),
                                                () ->
                                                {
                                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                                    playerInventoryOverlay.remove(playerItemSelected);
                                                    selectedItem = playerInventory.getIndex(item);

                                                    int scaledPrice = (int) (item.getCurrencyValue()*(1+currentPlayer.getPilotStats().charismaScaling()));

                                                    //reposition playerItemSelected then add back
                                                    playerItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    playerItemBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    playerItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                                    playerItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0) + "(" + scaledPrice + ")"));
                                                    if(item.getAttributes().size() > 1)
                                                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));
                                                    else
                                                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, ""));

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

                                                },
                                                //exit function
                                                () ->
                                                {

                                                });

                                        playerItems.add(playerItem);
                                        playerInventoryOverlay.addClickable(playerItem);
                                        playerInventoryOverlay.add(playerItem);
                                    }
                                }
                            });
                    playerPageOverlay.add(playerPageDown);
                    playerPageOverlay.addClickable(playerPageDown);
                    Button playerPageUp = new Button(new Point(112,0),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.BLACK, Color.BLACK, Color.GREEN, ">"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.GREEN, Color.BLACK, ">"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.BLACK, Color.BLACK, Color.GREEN, ">"),
                            () ->
                            {
                                if(playerCurrentPageNumber < playerMaxPage) {
                                    //remove all displayables from previous page
                                    selectedItem = 0;

                                    //Clear TP Items button list
                                    for(int i = 0; i < playerItems.size(); i++) {
                                        ItemButton button = playerItems.get(i);
                                        playerInventoryOverlay.removeClickable(button);
                                        playerInventoryOverlay.remove(button);
                                    }
                                    playerItems.clear();

                                    //remove selected overlay
                                    activeOverlay.remove(activeSelectedOverlay);
                                    activeOverlay.removeClickable(activeSelectedOverlay);

                                    //increase page number
                                    playerCurrentPageNumber++;

                                    //add items of new page
                                    for(int i = ITEMSPERPAGE*(playerCurrentPageNumber-1); i < playerInventory.getcurrItemsNum() && i < (ITEMSPERPAGE*playerCurrentPageNumber); i++){
                                        Item item = playerInventory.getItem(i);
                                        int pos = i - (ITEMSPERPAGE*(playerCurrentPageNumber-1));
                                        int x = ITEMLEFTOFFSET + (180*(pos%ITEMSPERLINE)) + ((pos%ITEMSPERLINE)*MARGIN) + MARGIN;
                                        int y = YINITIAL + (YMARGIN*(pos/ITEMSPERLINE));

                                        ItemButton playerItem = new ItemButton(item, new Point(x, y),
                                                item.getImage(),
                                                item.getImage(),
                                                item.getImage(),
                                                () ->
                                                {
                                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                                    playerInventoryOverlay.remove(playerItemSelected);
                                                    selectedItem = playerInventory.getIndex(item);

                                                    int scaledPrice = (int) (item.getCurrencyValue()*(1+currentPlayer.getPilotStats().charismaScaling()));

                                                    //reposition playerItemSelected then add back
                                                    playerItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    playerItemBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    playerItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                                    playerItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0) + "(" + scaledPrice + ")"));
                                                    if(item.getAttributes().size() > 1)
                                                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));
                                                    else
                                                        playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, ""));

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

                                                },
                                                //exit function
                                                () ->
                                                {

                                                });

                                        playerItems.add(playerItem);
                                        playerInventoryOverlay.addClickable(playerItem);
                                        playerInventoryOverlay.add(playerItem);
                                    }
                                }
                            });
                    playerPageOverlay.add(playerPageUp);
                    playerPageOverlay.addClickable(playerPageUp);
                    this.activePageOverlay = playerPageOverlay;
                    playerInventoryOverlay.add(playerPageOverlay);
                    playerInventoryOverlay.addClickable(playerPageOverlay);
                });

        controlstate.add(sellButton);
        drawstate.addLeftOverlay(sellButton);

        //Add Bounty Missions Button
        Button bountyButton = new Button(new Point(0, HEIGHT * 3),
                ImageFactory.getBountyButtonBase(),
                ImageFactory.getBountyButtonBase(),
                ImageFactory.getBountyButtonBase(),
                () ->
                {
                    //Clear bounty list
                    for(int i = 0; i < bountyMissions.size(); i++) {
                        Overlay overlay = bountyMissions.get(i);
                        bountyListOverlay.removeClickable(overlay);
                        bountyListOverlay.remove(overlay);
                    }
                    bountyMissions.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    drawstate.removeOverlay(activeOverlay);
                    drawstate.addCenterOverlay(this.bountyListOverlay);
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.bountyListOverlay;
                    controlstate.add(this.bountyListOverlay);

                    //player has a current mission
                    if(currentPlayer.getCurrentBountyMission() != null){
                        BountyMission playerMission = currentPlayer.getCurrentBountyMission();
                        if(playerMission.isCompleted()) {
                            Overlay turnInMission = new Overlay(new Point());
                            StringDisplayable playerBountyInfo = new StringDisplayable(new Point(0, 150),
                                    () -> "Current Mission: Destroy " + playerMission.getEnemyCount() + " " + playerMission.getEnemyType(),Color.GREEN, font);
                            turnInMission.add(playerBountyInfo);

                            StringDisplayable bountyReward = new StringDisplayable(new Point(0, 300),
                                    () -> "Reward: $"  + playerMission.getCurrencyValue(),Color.GREEN, font);
                            turnInMission.add(bountyReward);
                            StringDisplayable missionStatus = new StringDisplayable(new Point(0, 450),() -> "Mission Status: Complete" ,Color.GREEN, font);
                            turnInMission.add(missionStatus);

                            Button turnInButton = new Button(new Point(0,600),
                                    ImageFactory.getTurnInMissionButton(),
                                    ImageFactory.getTurnInMissionButton(),
                                    ImageFactory.getTurnInMissionButton(),
                                    () ->
                                    {
                                        playerWallet.increaseCurrencyBalance(playerMission.getCurrencyValue());
                                        currentPlayer.setCurrentBountyMission(null);
                                        bountyListOverlay.remove(turnInMission);
                                        bountyListOverlay.removeClickable(turnInMission);;
                                        bountyMissions.clear();

                                        for(int i = 0; i < currentTP.getBountyList().size(); i++) {
                                            BountyMission mission = currentTP.getBountyList().get(i);
                                            int x = (160*(i%8)) + ((i%8)*MARGIN) + MARGIN;
                                            int y = 125*i + 125 + (i * MARGIN);
                                            Overlay bountyListing = new Overlay(new Point());
                                            StringDisplayable bountyInfo = new StringDisplayable(new Point(MARGIN, y),
                                                    () -> "Kill " + mission.getEnemyCount() + " " + mission.getEnemyType() + ": $" + mission.getCurrencyValue(),Color.GREEN, font);
                                            bountyListing.add(bountyInfo);

                                            Button acceptMissionButton = new Button(new Point(500, y),
                                                    ImageFactory.getAcceptBountyMissionButton(),
                                                    ImageFactory.getAcceptBountyMissionButton(),
                                                    ImageFactory.getAcceptBountyMissionButton(),
                                                    () ->
                                                    {
                                                        //Remove all listings and add mission accepted
                                                        for (Overlay overlay: bountyMissions) {
                                                            bountyListOverlay.remove(overlay);
                                                            bountyListOverlay.removeClickable(overlay);
                                                        }
                                                        currentPlayer.setCurrentBountyMission(mission);

                                                        BountyMission currentMission = currentPlayer.getCurrentBountyMission();
                                                        Overlay missionAccepted = new Overlay(new Point());
                                                        StringDisplayable missionAcceptedStatus = new StringDisplayable(new Point(400, 150),() -> "Mission Accepted!" ,Color.GREEN, font);
                                                        missionAccepted.add(missionAcceptedStatus);
                                                        bountyMissions.add(missionAccepted);
                                                        bountyListOverlay.add(missionAccepted);

//
                                                    },
                                                    () ->
                                                    {

                                                    },
                                                    //enter function: Display mission info
                                                    () ->
                                                    {

                                                    },
                                                    //exit function
                                                    () ->
                                                    {

                                                    });
                                            bountyListing.add(acceptMissionButton);
                                            bountyListing.addClickable(acceptMissionButton);
                                            bountyMissions.add(bountyListing);
                                            bountyListOverlay.addClickable(bountyListing);
                                            bountyListOverlay.add(bountyListing);
                                        }
                                    });
                            turnInMission.add(turnInButton);
                            turnInMission.addClickable(turnInButton);
                            bountyMissions.add(turnInMission);
                            bountyListOverlay.add(turnInMission);
                            bountyListOverlay.addClickable(turnInMission);
                        }
                        else {
                            BountyMission currentMission = currentPlayer.getCurrentBountyMission();
                            Overlay incompleteMission = new Overlay(new Point());

                            StringDisplayable bountyInfo = new StringDisplayable(new Point(0, 150),
                                    () -> "Current Mission: Destroy " + currentMission.getEnemyCount() + " " + currentMission.getEnemyType(),Color.GREEN, font);
                            incompleteMission.add(bountyInfo);

                            StringDisplayable bountyReward = new StringDisplayable(new Point(0, 300),
                                    () -> "Reward: $"  + currentMission.getCurrencyValue(),Color.GREEN, font);
                            incompleteMission.add(bountyReward);

                            StringDisplayable missionStatus = new StringDisplayable(new Point(0, 450),() -> "Mission Status: Incomplete" ,Color.GREEN, font);
                            incompleteMission.add(missionStatus);

                            bountyMissions.add(incompleteMission);
                            bountyListOverlay.add(incompleteMission);
                        }
                    }

                    //Player has no current mission
                    else {
                        System.out.println("No mission");
                        for(int i = 0; i < currentTP.getBountyList().size(); i++) {
                            BountyMission mission = currentTP.getBountyList().get(i);
                            int x = MARGIN;
                            int y = 125*i + 125 + (i * MARGIN);
                            Overlay bountyListing = new Overlay(new Point());
                            StringDisplayable bountyInfo = new StringDisplayable(new Point(MARGIN, y),
                                    () -> "Kill " + mission.getEnemyCount() + " " + mission.getEnemyType() + ": $" + mission.getCurrencyValue(),Color.GREEN, font);
                            bountyListing.add(bountyInfo);

                            Button acceptMissionButton = new Button(new Point(500, y),
                                    ImageFactory.getAcceptBountyMissionButton(),
                                    ImageFactory.getAcceptBountyMissionButton(),
                                    ImageFactory.getAcceptBountyMissionButton(),
                                    () ->
                                    {
                                        //Remove all listings and add mission accepted
                                        for (Overlay overlay: bountyMissions) {
                                            bountyListOverlay.remove(overlay);
                                            bountyListOverlay.removeClickable(overlay);
                                        }
                                        currentPlayer.setCurrentBountyMission(mission);

                                        BountyMission currentMission = currentPlayer.getCurrentBountyMission();
                                        Overlay missionAccepted = new Overlay(new Point());
                                        StringDisplayable missionStatus = new StringDisplayable(new Point(400, 150),() -> "Mission Accepted!" ,Color.GREEN, font);
                                        missionAccepted.add(missionStatus);
                                        bountyMissions.add(missionAccepted);
                                        bountyListOverlay.add(missionAccepted);
                                    },
                                    () ->
                                    {

                                    },
                                    //enter function: Display mission info
                                    () ->
                                    {

                                    },
                                    //exit function
                                    () ->
                                    {

                                    });

                            bountyListing.add(acceptMissionButton);
                            bountyListing.addClickable(acceptMissionButton);
                            bountyMissions.add(bountyListing);
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
                ImageFactory.getExitButtonBase(),
                ImageFactory.getExitButtonBase(),
                () -> {
                    //todo: switch system exit to change to overworldUberstate
                    transitionObserver.switchToOverworld();
                });

        controlstate.add(exitButton);
        drawstate.addLeftOverlay(exitButton);

        //Create Player Inventory Overlay
        Overlay playerInventoryOverlay = new Overlay(new Point(0,0));
        Displayable piBackground = new ImageDisplayable(new Point(315, 0), ImageFactory.makeBorderedRect(WIDTH, 900, Color.WHITE, Color.GRAY));
        StringDisplayable piTitle = new StringDisplayable( new Point(375, 0), () -> "Player Inventory", Color.GREEN, font);
        StringDisplayable playerWalletTitle = new StringDisplayable( new Point(0, 0), () -> "Wallet: $" + playerWallet.getCurrencyBalance(), Color.GREEN, font);
        playerInventoryOverlay.add(piTitle);
        playerInventoryOverlay.add(playerWalletTitle);
        this.playerInventoryOverlay = playerInventoryOverlay;

        //Create TP Inventory Overlay
        Overlay tpInventoryOverlay = new Overlay(new Point(0,0));
        Displayable tpBackground = new ImageDisplayable(new Point(315, 0), ImageFactory.makeBorderedRect(WIDTH, 900, Color.WHITE, Color.GRAY));
        StringDisplayable tpiTitle = new StringDisplayable( new Point(300, 0), () -> "Trading Post Inventory", Color.GREEN, font);
        tpInventoryOverlay.add(tpiTitle);
        tpInventoryOverlay.add(playerWalletTitle);
        this.tpInventoryOverlay = tpInventoryOverlay;

        //Create Bounty List Overlay
        Overlay bountyListOverlay =  new Overlay(new Point(315,0));
        Displayable bountyBackground =  new ImageDisplayable(new Point(315, 0), ImageFactory.makeBorderedRect(WIDTH, 900, Color.WHITE, Color.GRAY));
        StringDisplayable blTitle = new StringDisplayable(new Point(400, 0), () -> "Bounty List", Color.GREEN, font);
        bountyListOverlay.add(blTitle);
        bountyListOverlay.add(playerWalletTitle);
        this.bountyListOverlay = bountyListOverlay;
    }
}
