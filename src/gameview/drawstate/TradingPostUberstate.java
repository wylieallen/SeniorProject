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
    //might change this
    private static final int ITEMSPERPAGE = 12;
    private static final int ITEMSPERLINE = 6;
    private static final int ITEMLEFTOFFSET = 400;
    private static final int YMARGIN = 350;
    private static final int YINITIAL = 200;
    private static final int ITEMINFOWIDTH = 250;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private static final Font fontSmall = new Font("Arvo", Font.PLAIN, 20);
    private TradingPost currentTP;
    private Player currentPlayer;
    private int selectedItem = 0;
    private int hoveredItem = 0;
    private int tpCurrentPageNumber = 1;
    private int playerCurrentPageNumber = 1;
    private int tpMaxPage;
    private int playerMaxPage;
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
    private Overlay activePageOverlay = new Overlay(new Point());
    private Overlay missionSelected = new Overlay(new Point());
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
        Ship ship = new Ship(currentPlayer, new ShipHull(1000, Rarity.COMMON, 1000, 30));
        currentPlayer.getShipHangar().addShip(ship);
        currentPlayer.setActiveShip(ship);
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new FuelConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new FuelConsumable(200,50));

        BountyMission m = new BountyMission(2000, 10, "Spock");
        m.completeMission();
        currentPlayer.setCurrentBountyMission(m);

        currentTP = new TradingPost(new Inventory(20), new Wallet(500), new ArrayList<BountyMission>());
        currentTP.getInventory().addItem(new FuelConsumable(300, 70));
        currentTP.getInventory().addItem(new FuelConsumable(400, 80));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(300, 70));
        currentTP.getInventory().addItem(new FuelConsumable(400, 80));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));
        currentTP.getInventory().addItem(new FuelConsumable(500, 90));

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
//                    ItemButton itemBuy = new ItemButton(new Item(), new Point(),
//                            ImageFactory.getBuyButtonBase(),
//                            ImageFactory.getBuyButtonBase(),
//                            ImageFactory.getBuyButtonBase(),
//                            () ->
//                            {
//
//                            });
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
                    //drawstate.removeAllRightOverlays();
                    drawstate.removeOverlay(activeOverlay);
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.tpInventoryOverlay;
//                    inactiveOverlay = this.playerInventoryOverlay;
                    drawstate.addCenterOverlay(this.tpInventoryOverlay);
                    controlstate.add(this.tpInventoryOverlay);

                    //Add selected item Overlay
                    Overlay tpItemSelected = new Overlay(new Point(0,0));
                    ImageDisplayable tpiBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220, Color.BLUE, Color.GRAY));
                    ImageDisplayable tpItemName = new ImageDisplayable(new Point(0,0),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, tpInventory.getItem(selectedItem).getName()));
                    ImageDisplayable tpItemPrice = new ImageDisplayable(new Point(0,55),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, tpInventory.getItem(selectedItem).getAttributes().get(0)));
                    ImageDisplayable tpItemInfo = new ImageDisplayable(new Point(0,110),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, tpInventory.getItem(selectedItem).getAttributes().get(1)));
                    tpItemSelected.add(tpiBackground);
                    tpItemSelected.add(tpItemName);
                    tpItemSelected.add(tpItemPrice);
                    tpItemSelected.add(tpItemInfo);

                    //tpItemSelected.add(new StringDisplayable( new Point(16, 16), () -> "Price: " + tpInventory.getItem(selectedItem).getCurrencyValue(), Color.GREEN, font));

                    Button itemBuy = new Button(new Point(0,165),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Buy Item"),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.WHITE, "Buy Item"),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Buy Item"),
                            () ->
                            {
                                Item item = tpInventory.getItem(this.selectedItem);
                                //ItemButton button =  tpItems.get(selectedItem - (ITEMSPERPAGE*(tpCurrentPageNumber-1)));
                                if(playerWallet.getCurrencyBalance() >= item.getCurrencyValue()) {
                                    tpInventoryOverlay.removeClickable(tpItemSelected);
                                    activeOverlay.remove(tpItemSelected);
                                    tpWallet.increaseCurrencyBalance(item.getCurrencyValue());
                                    playerWallet.decreaseCurrencyBalance(item.getCurrencyValue());
                                    tpInventory.removeItem(item);
                                    playerInventory.addItem(item);
                                    playerMaxPage = ((playerInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
                                    tpMaxPage = ((tpInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
//                                    tpInventoryOverlay.removeClickable(button);
//                                    tpInventoryOverlay.remove(button);
//                                    tpItems.remove(button);
//                                    redrawButtons();
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

                                                    //reposition tpItemSelected then add back
                                                    tpItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    tpiBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    tpItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getName()));
                                                    tpItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getAttributes().get(0)));
                                                    tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getAttributes().get(1)));

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
//                                                    hoveredItem = tpInventory.getIndex(newitem) - (ITEMSPERPAGE*(tpCurrentPageNumber-1));
//                                                    int xHover = tpItems.get(hoveredItem).getOrigin().x;
//                                                    int yHover = tpItems.get(hoveredItem).getOrigin().y;
//                                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
//                                                            ImageFactory.makeCenterLabeledRect(150, 40, Color.BLUE, Color.GRAY, Color.WHITE, "Use Value: "+newitem.getUseValue()));
////                                    this.itemInfo = new Overlay(new Point(new Point(xHover + 90, yHover + (HEIGHT/10)*3)));
////                                    itemInfo.add(new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(300,300, Color.BLUE,Color.GRAY)));
////                                    itemInfo.add(new StringDisplayable(new Point(0,0), () -> item.getAttributes()));
//                                                    tpInventoryOverlay.add(itemInfo);
                                                },
                                                //exit function
                                                () ->
                                                {
//                                                    tpInventoryOverlay.remove(itemInfo);
                                                });

                                        tpItems.add(tpItem);
                                        tpInventoryOverlay.addClickable(tpItem);
                                        tpInventoryOverlay.add(tpItem);
                                    }
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
                    for(int i = 0; i < tpInventory.getcurrItemsNum() && i < ITEMSPERPAGE; i++){
                        Item item = tpInventory.getItem(i);
                        int x = (180*(i%ITEMSPERLINE)) + ((i%ITEMSPERLINE)*MARGIN) + MARGIN;
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

                                    //reposition tpItemSelected then add back
                                    tpItemSelected.getOrigin().setLocation(x-50,y+120);
                                    tpiBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                    tpItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                    tpItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0)));
                                    tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));

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
//                                    hoveredItem = tpInventory.getIndex(item);
//                                    int xHover = tpItems.get(hoveredItem).getOrigin().x;
//                                    int yHover = tpItems.get(hoveredItem).getOrigin().y;
//                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
//                                            ImageFactory.makeCenterLabeledRect(150, 40, Color.BLUE, Color.GRAY, Color.WHITE, "Use Value: "+item.getUseValue()));
////                                    this.itemInfo = new Overlay(new Point(new Point(xHover + 90, yHover + (HEIGHT/10)*3)));
////                                    itemInfo.add(new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(300,300, Color.BLUE,Color.GRAY)));
////                                    itemInfo.add(new StringDisplayable(new Point(0,0), () -> item.getAttributes()));
//                                    tpInventoryOverlay.add(itemInfo);
                                },
                                //exit function
                                () ->
                                {
//                                    tpInventoryOverlay.remove(itemInfo);
                                });

                        tpItems.add(tpItem);
                        tpInventoryOverlay.addClickable(tpItem);
                        tpInventoryOverlay.add(tpItem);
                    }

                    //Paging for TP Inventory
                    Overlay tpPageOverlay = new Overlay(new Point(325,100));
                    //ImageDisplayable pageNum = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(150,50,Color.WHITE,Color.WHITE));
                    //pageOverlay.add(pageNum);
                    tpPageOverlay.add(new StringDisplayable(new Point(40, 5), () -> "Page " + tpCurrentPageNumber,Color.GREEN, fontSmall));
                    Button tpPageDown = new Button(new Point(0,0),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.WHITE, Color.WHITE, Color.BLACK, "<"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.WHITE, Color.WHITE, "<"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.ORANGE, Color.WHITE, Color.BLACK, "<"),
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

                                                    //reposition tpItemSelected then add back
                                                    tpItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    tpiBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    tpItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                                    tpItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0)));
                                                    tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));

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
//                                                    hoveredItem = tpInventory.getIndex(item) - (ITEMSPERPAGE*(tpCurrentPageNumber-1));
//                                                    int xHover = tpItems.get(hoveredItem).getOrigin().x;
//                                                    int yHover = tpItems.get(hoveredItem).getOrigin().y;
//                                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
//                                                            ImageFactory.makeCenterLabeledRect(150, 40, Color.BLUE, Color.GRAY, Color.WHITE, "Use Value: "+item.getUseValue()));
////                                    this.itemInfo = new Overlay(new Point(new Point(xHover + 90, yHover + (HEIGHT/10)*3)));
////                                    itemInfo.add(new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(300,300, Color.BLUE,Color.GRAY)));
////                                    itemInfo.add(new StringDisplayable(new Point(0,0), () -> item.getAttributes()));
//                                                    tpInventoryOverlay.add(itemInfo);
                                                },
                                                //exit function
                                                () ->
                                                {
//                                                    tpInventoryOverlay.remove(itemInfo);
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
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.WHITE, Color.WHITE, Color.BLACK, ">"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.WHITE, Color.WHITE, ">"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.ORANGE, Color.WHITE, Color.BLACK, ">"),
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

                                                    //reposition tpItemSelected then add back
                                                    tpItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    tpiBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    tpItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                                    tpItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0)));
                                                    tpItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));

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
//                                                    hoveredItem = tpInventory.getIndex(item) - (ITEMSPERPAGE*(tpCurrentPageNumber-1));
//                                                    int xHover = tpItems.get(hoveredItem).getOrigin().x;
//                                                    int yHover = tpItems.get(hoveredItem).getOrigin().y;
//                                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
//                                                            ImageFactory.makeCenterLabeledRect(150, 40, Color.BLUE, Color.GRAY, Color.WHITE, "Use Value: "+item.getUseValue()));
////                                    this.itemInfo = new Overlay(new Point(new Point(xHover + 90, yHover + (HEIGHT/10)*3)));
////                                    itemInfo.add(new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(300,300, Color.BLUE,Color.GRAY)));
////                                    itemInfo.add(new StringDisplayable(new Point(0,0), () -> item.getAttributes()));
//                                                    tpInventoryOverlay.add(itemInfo);
                                                },
                                                //exit function
                                                () ->
                                                {
//                                                    tpInventoryOverlay.remove(itemInfo);
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
                    //drawstate.removeAllRightOverlays();
                    drawstate.removeOverlay(activeOverlay);
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.playerInventoryOverlay;
//                    inactiveOverlay = this.tpInventoryOverlay;
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
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, playerInventory.getItem(selectedItem).getAttributes().get(1)));
                    playerItemSelected.add(playerItemBackground);
                    playerItemSelected.add(playerItemName);
                    playerItemSelected.add(playerItemPrice);
                    playerItemSelected.add(playerItemInfo);

                    Button itemSell = new Button(new Point(0,165),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Sell Item"),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.WHITE, "Sell Item"),
                            ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Sell Item"),
                            () ->
                            {
                                Item item = playerInventory.getItem(this.selectedItem);
                                //ItemButton button =  playerItems.get(selectedItem);
                                if(tpWallet.getCurrencyBalance() >= item.getCurrencyValue()) {
                                    playerInventoryOverlay.removeClickable(playerItemSelected);
                                    playerInventoryOverlay.remove(playerItemSelected);
                                    playerWallet.increaseCurrencyBalance(item.getCurrencyValue());
                                    tpWallet.decreaseCurrencyBalance(item.getCurrencyValue());
                                    playerInventory.removeItem(item);
                                    tpInventory.addItem(item);
                                    playerMaxPage = ((playerInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
                                    tpMaxPage = ((tpInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
//                                    playerInventoryOverlay.removeClickable(button);
//                                    playerInventoryOverlay.remove(button);
//                                    playerItems.remove(button);
//                                    redrawButtons();
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

                                                    //reposition playerItemSelected then add back
                                                    playerItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    playerItemBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    playerItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getName()));
                                                    playerItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getAttributes().get(0)));
                                                    playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, newitem.getAttributes().get(1)));

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
//                                                    hoveredItem = playerInventory.getIndex(newitem) - (ITEMSPERPAGE*(playerCurrentPageNumber-1));
//                                                    int xHover = playerItems.get(hoveredItem).getOrigin().x;
//                                                    int yHover = playerItems.get(hoveredItem).getOrigin().y;
//                                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
//                                                            ImageFactory.makeCenterLabeledRect(150, 40, Color.BLUE, Color.GRAY, Color.WHITE, "Use Value: "+newitem.getUseValue()));
////                                    this.itemInfo = new Overlay(new Point(new Point(xHover + 90, yHover + (HEIGHT/10)*3)));
////                                    itemInfo.add(new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(300,300, Color.BLUE,Color.GRAY)));
////                                    itemInfo.add(new StringDisplayable(new Point(0,0), () -> item.getAttributes()));
//                                                    playerInventoryOverlay.add(itemInfo);
                                                },
                                                //exit function
                                                () ->
                                                {
//                                                    playerInventoryOverlay.remove(itemInfo);
                                                });

                                        playerItems.add(playerItem);
                                        playerInventoryOverlay.addClickable(playerItem);
                                        playerInventoryOverlay.add(playerItem);
                                    }

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
                    for(int i = 0; i < playerInventory.getcurrItemsNum() && i < ITEMSPERPAGE; i++){

                        Item item = playerInventory.getItem(i);
                        int x = (180*(i%ITEMSPERLINE)) + ((i%ITEMSPERLINE)*MARGIN) + MARGIN;
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

                                    //reposition playerItemSelected then add back
                                    playerItemSelected.getOrigin().setLocation(x-50,y+120);
                                    playerItemBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                    playerItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                    playerItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0)));
                                    playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));

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
//                                    hoveredItem = playerInventory.getIndex(item);
//                                    int xHover = playerItems.get(hoveredItem).getOrigin().x;
//                                    int yHover = playerItems.get(hoveredItem).getOrigin().y;
//                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
//                                            ImageFactory.makeCenterLabeledRect(150, HEIGHT/5, Color.BLUE, Color.GRAY, Color.WHITE, "Use Value: " + item.getUseValue()));
////                                    this.itemInfo = new Overlay(new Point(new Point(xHover + 90, yHover + (HEIGHT/10)*3)));
////                                    itemInfo.add(new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(300,300, Color.BLUE,Color.GRAY)));
////                                    itemInfo.add(new StringDisplayable(new Point(0,0), () -> item.getAttributes()));
//                                    playerInventoryOverlay.add(itemInfo);
                                },
                                //exit function
                                () ->
                                {
//                                    playerInventoryOverlay.remove(itemInfo);
                                });

                        playerItems.add(playerItem);
                        playerInventoryOverlay.addClickable(playerItem);
                        playerInventoryOverlay.add(playerItem);
                    }

                    //Paging for Player Inventory
                    Overlay playerPageOverlay = new Overlay(new Point(325,100));
                    //ImageDisplayable pageNum = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(150,50,Color.WHITE,Color.WHITE));
                    //pageOverlay.add(pageNum);
                    playerPageOverlay.add(new StringDisplayable(new Point(40, 5), () -> "Page " + playerCurrentPageNumber,Color.GREEN, fontSmall));
                    Button playerPageDown = new Button(new Point(0,0),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.WHITE, Color.WHITE, Color.BLACK, "<"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.WHITE, Color.WHITE, "<"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.ORANGE, Color.WHITE, Color.BLACK, "<"),
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

                                                    //reposition playerItemSelected then add back
                                                    playerItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    playerItemBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    playerItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                                    playerItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0)));
                                                    playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));

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
//                                                    hoveredItem = playerInventory.getIndex(item) - (ITEMSPERPAGE*(playerCurrentPageNumber-1));
//                                                    int xHover = playerItems.get(hoveredItem).getOrigin().x;
//                                                    int yHover = playerItems.get(hoveredItem).getOrigin().y;
//                                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
//                                                            ImageFactory.makeCenterLabeledRect(150, 40, Color.BLUE, Color.GRAY, Color.WHITE, "Use Value: "+item.getUseValue()));
////                                    this.itemInfo = new Overlay(new Point(new Point(xHover + 90, yHover + (HEIGHT/10)*3)));
////                                    itemInfo.add(new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(300,300, Color.BLUE,Color.GRAY)));
////                                    itemInfo.add(new StringDisplayable(new Point(0,0), () -> item.getAttributes()));
//                                                    playerInventoryOverlay.add(itemInfo);
                                                },
                                                //exit function
                                                () ->
                                                {
//                                                    playerInventoryOverlay.remove(itemInfo);
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
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.WHITE, Color.WHITE, Color.BLACK, ">"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.WHITE, Color.WHITE, ">"),
                            ImageFactory.makeCenterLabeledRect(38, 50, Color.ORANGE, Color.WHITE, Color.BLACK, ">"),
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

                                                    //reposition playerItemSelected then add back
                                                    playerItemSelected.getOrigin().setLocation(x-50,y+120);
                                                    playerItemBackground.setImage(ImageFactory.makeBorderedRect(ITEMINFOWIDTH, 220,Color.WHITE, Color.GREEN));
                                                    playerItemName.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getName()));
                                                    playerItemPrice.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(0)));
                                                    playerItemInfo.setImage(ImageFactory.makeCenterLabeledRect(ITEMINFOWIDTH,55,Color.GREEN,Color.GREEN,Color.BLACK, item.getAttributes().get(1)));

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
//                                                    hoveredItem = playerInventory.getIndex(item) - (ITEMSPERPAGE*(playerCurrentPageNumber-1));
//                                                    int xHover = playerItems.get(hoveredItem).getOrigin().x;
//                                                    int yHover = playerItems.get(hoveredItem).getOrigin().y;
//                                                    this.itemInfo = new ImageDisplayable(new Point(xHover + 90, yHover + (HEIGHT/10)*3),
//                                                            ImageFactory.makeCenterLabeledRect(150, 40, Color.BLUE, Color.GRAY, Color.WHITE, "Use Value: "+item.getUseValue()));
////                                    this.itemInfo = new Overlay(new Point(new Point(xHover + 90, yHover + (HEIGHT/10)*3)));
////                                    itemInfo.add(new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(300,300, Color.BLUE,Color.GRAY)));
////                                    itemInfo.add(new StringDisplayable(new Point(0,0), () -> item.getAttributes()));
//                                                    playerInventoryOverlay.add(itemInfo);
                                                },
                                                //exit function
                                                () ->
                                                {
//                                                    playerInventoryOverlay.remove(itemInfo);
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
                    //Clear bounty button list
                    for(int i = 0; i < bountyButtons.size(); i++) {
                        Button button = bountyButtons.get(i);
                        bountyListOverlay.removeClickable(button);
                        bountyListOverlay.remove(button);
                    }
                    bountyButtons.clear();

                    activeOverlay.remove(activeSelectedOverlay);
                    activeOverlay.removeClickable(activeSelectedOverlay);
                    //drawstate.removeAllRightOverlays();
                    drawstate.removeOverlay(activeOverlay);
                    drawstate.addCenterOverlay(this.bountyListOverlay);
                    controlstate.remove(activeOverlay);
                    activeOverlay = this.bountyListOverlay;
                    controlstate.add(this.bountyListOverlay);

                    //player has a current mission
                    if(currentPlayer.getCurrentBountyMission() != null){
                        BountyMission playerMission = currentPlayer.getCurrentBountyMission();
                        if(playerMission.isCompleted()) {
                            Button turnInMission = new Button(new Point(MARGIN,HEIGHT ),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Turn in Mission"),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT, Color.GREEN, Color.GRAY, Color.WHITE, "Turn in Mission"),
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
                                            int x = (160*(i%8)) + ((i%8)*MARGIN) + MARGIN;
                                            int y = 125*i + 125 + (i * MARGIN);

                                            Button bountyListing = new Button(new Point(MARGIN, y),
                                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.WHITE, Color.GRAY, Color.BLACK, mission.getEnemyType()),
                                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.GREEN, Color.GRAY, Color.WHITE, mission.getEnemyType()),
                                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.ORANGE, Color.GRAY, Color.BLACK, mission.getEnemyType()),
                                                    () ->
                                                    {
                                                        bountyListOverlay.remove(missionSelected);
                                                        bountyListOverlay.removeClickable(missionSelected);

                                                        //Add selected mission Overlay
                                                        Overlay missionSelected = new Overlay(new Point(WIDTH/5,HEIGHT*3));
                                                        Displayable msBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLUE, Color.GRAY));
                                                        missionSelected.add(msBackground);
                                                        missionSelected.add(new StringDisplayable( new Point(16, 16), () -> "Reward: " + mission.getCurrencyValue(), Color.GREEN, font));

                                                        Button getMission = new Button(new Point(16,100),
                                                                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "Accept"),
                                                                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.GREEN, Color.GRAY, Color.WHITE, "Accept"),
                                                                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "Accept"),
                                                                () ->
                                                                {
                                                                    bountyListOverlay.remove(missionSelected);
                                                                    bountyListOverlay.removeClickable(missionSelected);

                                                                    for (Button button: bountyButtons) {
                                                                        bountyListOverlay.remove(button);
                                                                        bountyListOverlay.removeClickable(button);
                                                                    }
                                                                    currentPlayer.setCurrentBountyMission(mission);

                                                                    ImageDisplayable incompleteMission = new ImageDisplayable(new Point(MARGIN, HEIGHT),
                                                                            ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT, Color.GREEN, Color.GRAY, Color.WHITE, "Mission Accepted!"));
                                                                    bountyListOverlay.add(incompleteMission);
                                                                });
                                                        missionSelected.addClickable(getMission);
                                                        missionSelected.add(getMission);
                                                        this.missionSelected = missionSelected;
                                                        bountyListOverlay.add(missionSelected);
                                                        bountyListOverlay.addClickable(missionSelected);
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
                                                                ImageFactory.makeCenterLabeledRect(80, HEIGHT/10, Color.BLUE, Color.GRAY, Color.WHITE, "" + mission.getCurrencyValue()));
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
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT, Color.GREEN, Color.GRAY, Color.WHITE, "Your current mission is incomplete"));
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

                            Button bountyListing = new Button(new Point(x, y),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.WHITE, Color.GRAY, Color.BLACK, mission.getEnemyType()),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.GREEN, Color.GRAY, Color.WHITE, mission.getEnemyType()),
                                    ImageFactory.makeCenterLabeledRect(WIDTH - (MARGIN*2), HEIGHT/2, Color.ORANGE, Color.GRAY, Color.BLACK, mission.getEnemyType()),
                                    () ->
                                    {
                                        bountyListOverlay.remove(missionSelected);
                                        bountyListOverlay.removeClickable(missionSelected);

                                        //Add selected mission Overlay
                                        Overlay missionSelected = new Overlay(new Point(WIDTH/5,HEIGHT*3));
                                        Displayable msBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLUE, Color.GRAY));
                                        missionSelected.add(msBackground);
                                        missionSelected.add(new StringDisplayable( new Point(16, 16), () -> "Reward: " + mission.getCurrencyValue(), Color.GREEN, font));

                                        Button getMission = new Button(new Point(16,100),
                                                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.WHITE, Color.GRAY, Color.BLACK, "Accept"),
                                                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.GREEN, Color.GRAY, Color.WHITE, "Accept"),
                                                ImageFactory.makeCenterLabeledRect(WIDTH/5, HEIGHT/5, Color.ORANGE, Color.GRAY, Color.BLACK, "Accept"),
                                                () ->
                                                {
                                                    bountyListOverlay.remove(missionSelected);
                                                    bountyListOverlay.removeClickable(missionSelected);

                                                    for (Button button: bountyButtons) {
                                                        bountyListOverlay.remove(button);
                                                        bountyListOverlay.removeClickable(button);
                                                    }
                                                });
                                        missionSelected.addClickable(getMission);
                                        missionSelected.add(getMission);
                                        this.missionSelected = missionSelected;
                                        bountyListOverlay.add(missionSelected);
                                        bountyListOverlay.addClickable(missionSelected);
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
                                                ImageFactory.makeCenterLabeledRect(80, HEIGHT/10, Color.BLUE, Color.GRAY, Color.WHITE, "" + mission.getCurrencyValue()));
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
                ImageFactory.getExitButtonBase(),
                ImageFactory.getExitButtonBase(),
                () -> {
                    //todo: switch system exit to change to overworldUberstate
                    System.exit(0);
                });

        controlstate.add(exitButton);
        drawstate.addLeftOverlay(exitButton);

//        //Paging for TP Inventory
//        Overlay tpPageOverlay = new Overlay(new Point(325,100));
//        //ImageDisplayable pageNum = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(150,50,Color.WHITE,Color.WHITE));
//        //pageOverlay.add(pageNum);
//        tpPageOverlay.add(new StringDisplayable(new Point(40, 5), () -> "Page " + tpCurrentPageNumber,Color.BLACK, fontSmall));
//        Button tpPageDown = new Button(new Point(0,0),
//                ImageFactory.makeCenterLabeledRect(38, 50, Color.WHITE, Color.WHITE, Color.BLACK, "<"),
//                ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.WHITE, Color.WHITE, "<"),
//                ImageFactory.makeCenterLabeledRect(38, 50, Color.ORANGE, Color.WHITE, Color.BLACK, "<"),
//                () ->
//                {
//                    if(tpCurrentPageNumber > 1)
//                        tpCurrentPageNumber--;
//                });
//        tpPageOverlay.add(tpPageDown);
//        tpPageOverlay.addClickable(tpPageDown);
//        Button tpPageUp = new Button(new Point(112,0),
//                ImageFactory.makeCenterLabeledRect(38, 50, Color.WHITE, Color.WHITE, Color.BLACK, ">"),
//                ImageFactory.makeCenterLabeledRect(38, 50, Color.GREEN, Color.WHITE, Color.WHITE, ">"),
//                ImageFactory.makeCenterLabeledRect(38, 50, Color.ORANGE, Color.WHITE, Color.BLACK, ">"),
//                () ->
//                {
//                    //tpMaxPage = ((tpInventory.getcurrItemsNum()-1)/ITEMSPERPAGE) + 1;
//                    System.out.println("tpItems: " + tpInventory.getcurrItemsNum());
//                    System.out.println("tpItems - 1: " + (tpInventory.getcurrItemsNum()-1));
//                    System.out.println("(tpItems - 1)/16: " + ((tpInventory.getcurrItemsNum()-1)/16));
//                    System.out.println("(tpItems - 1)/IPP: " + ((tpInventory.getcurrItemsNum()-1)/ITEMSPERPAGE));
//                    System.out.println("tpMaxPage: " + tpMaxPage);
//                    System.out.println("playerMaxPage: " + playerMaxPage);
//                    if(tpCurrentPageNumber < tpMaxPage)
//                        tpCurrentPageNumber++;
//                });
//        tpPageOverlay.add(tpPageUp);
//        tpPageOverlay.addClickable(tpPageUp);
//        tpInventoryOverlay.add(tpPageOverlay);
//        tpInventoryOverlay.addClickable(tpPageOverlay);

        //Create Player Inventory Overlay
        Overlay playerInventoryOverlay = new Overlay(new Point(315,0));
        Displayable piBackground = new ImageDisplayable(new Point(315, 0), ImageFactory.makeBorderedRect(WIDTH, 900, Color.WHITE, Color.GRAY));
        //playerInventoryOverlay.add(piBackground);
        StringDisplayable piTitle = new StringDisplayable( new Point(450, 0), () -> "Player Inventory", Color.GREEN, font);
        StringDisplayable playerWalletTitle = new StringDisplayable( new Point(50, 0), () -> "Wallet: $" + playerWallet.getCurrencyBalance(), Color.GREEN, font);
        playerInventoryOverlay.add(piTitle);
        playerInventoryOverlay.add(playerWalletTitle);
        //playerInventoryOverlay.add(new StringDisplayable( new Point(WIDTH/2, 50), () -> " Trading Post MONEY: " + tpWallet.getCurrencyBalance(), Color.GREEN, font));
        //playerInventoryOverlay.add(pageOverlay);
        //playerInventoryOverlay.addClickable(pageOverlay);
        this.playerInventoryOverlay = playerInventoryOverlay;

        //Create TP Inventory Overlay
        Overlay tpInventoryOverlay = new Overlay(new Point(315,0));
        Displayable tpBackground = new ImageDisplayable(new Point(315, 0), ImageFactory.makeBorderedRect(WIDTH, 900, Color.WHITE, Color.GRAY));
        //tpInventoryOverlay.add(tpBackground);
        StringDisplayable tpiTitle = new StringDisplayable( new Point(400, 0), () -> "Trading Post Inventory", Color.GREEN, font);
        tpInventoryOverlay.add(tpiTitle);
        tpInventoryOverlay.add(playerWalletTitle);
        //tpInventoryOverlay.add(new StringDisplayable( new Point(16, 50), () -> "Wallet: $" + currentPlayer.getMyWallet().getCurrencyBalance(), Color.GREEN, font));
        //tpInventoryOverlay.add(new StringDisplayable( new Point(WIDTH/2, 50), () -> " Trading Post MONEY: " + currentTP.getWallet().getCurrencyBalance(), Color.GREEN, font));
//        tpInventoryOverlay.add(tpPageOverlay);
//        tpInventoryOverlay.addClickable(tpPageOverlay);
        this.tpInventoryOverlay = tpInventoryOverlay;

        //Create Bounty List Overlay
        Overlay bountyListOverlay =  new Overlay(new Point(315,0));
        Displayable bountyBackground =  new ImageDisplayable(new Point(315, 0), ImageFactory.makeBorderedRect(WIDTH, 900, Color.WHITE, Color.GRAY));
        //bountyListOverlay.add(bountyBackground);
        StringDisplayable blTitle = new StringDisplayable(new Point(400, 0), () -> "Bounty List", Color.GREEN, font);
        bountyListOverlay.add(blTitle);
        //bountyListOverlay.add(new StringDisplayable( new Point(16, 50), () -> "Player MONEY: " + currentPlayer.getMyWallet().getCurrencyBalance(), Color.GREEN, font));
        //bountyListOverlay.add(new StringDisplayable( new Point(WIDTH/2, 50), () -> " Trading Post MONEY: " + currentTP.getWallet().getCurrencyBalance(), Color.GREEN, font));
        this.bountyListOverlay = bountyListOverlay;
    }

    private void redrawButtons(){
        //redraw tpInventoryOverlay
        for(int i = 0; i < tpItems.size(); i++) {
            tpItems.get(i).getOrigin().setLocation((180*(i%8)) + ((i%8)*MARGIN) + MARGIN, 150 + (125*(i/8)));
        }

        //redraw playerInventoryOverlay
        for(int i = 0; i < playerItems.size(); i++) {
            playerItems.get(i).getOrigin().setLocation((180*(i%8)) + ((i%8)*MARGIN) + MARGIN, 150 + (125*(i/8)));
        }
    }
    // Placeholder money example stuff goes here
//    public int getMoney() { return money; }
//    public void modifyMoney(int newMoney) { money += newMoney; }


}
