package gameview.drawstate;

import Model.Items.Inventory;
import Model.Map.Node;
import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Map.Zones.TradingZone;
import Model.Map.Zones.Zone;
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

public class OverworldUberstate extends Uberstate
{
    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1750;
    private static final int MARGIN = 10;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private Overworld overworld;
    private Overlay mapOverlay;
    private Button upgradeStats;
    private ImageDisplayable nodeInfo;
    private Overlay selectedNode;
    private int selectedShip = 0;
    private TradingPost currentTP;
    private Player currentPlayer;

    public OverworldUberstate(Renderstate renderstate) {
        super(new Drawstate(), renderstate, new ClickableControlstate());

        TradingPost tp1 = new TradingPost(new Inventory(20), new Wallet(500), new ArrayList<BountyMission>());
        overworld = Overworld.getOverworld();
        overworld.addNode(new Node(new BattleZone(1)));
        overworld.addNode(new Node(new BattleZone(2)));
        overworld.addNode(new Node(new BattleZone(3)));
        overworld.addNode(new Node(new TradingZone(tp1)));
        overworld.addNode(new Node(new BattleZone(4)));

        currentPlayer = new Player();
        currentPlayer.getPilotStats().levelUp();
        currentPlayer.getPilotStats().levelUp();
        currentPlayer.getPilotStats().levelUp();
        Ship ship1 = new Ship(currentPlayer, new ShipHull(100, Rarity.COMMON, 500, 30));
        Ship ship2 = new Ship(currentPlayer, new ShipHull(200, Rarity.RARE, 750, 40));
        currentPlayer.getShipHangar().addShip(ship1);
        currentPlayer.getShipHangar().addShip(ship2);
        currentPlayer.setActiveShip(ship1);

        Drawstate drawstate = getDrawstate();
        ClickableControlstate controlstate = getControlstate();

        //Add space background
        ImageDisplayable spaceBackground =
                new ImageDisplayable(new Point(0,0), ImageFactory.getSpaceBackground());
        drawstate.addUnderlay(spaceBackground);

        mapOverlay = new Overlay(new Point(0, 0));
//        ImageDisplayable mapBackground = new ImageDisplayable(new Point(0,0),
//                //ImageFactory.makeBorderedRect(WIDTH, 900, Color.BLACK, Color.WHITE));
//                ImageFactory.getSpaceBackground());
//        mapOverlay.add(mapBackground);

        //Add title box
        ImageDisplayable overworldTitle =
                new ImageDisplayable(new Point(WIDTH/2 - (ImageFactory.getOverworldLabel().getWidth()/2),25), ImageFactory.getOverworldLabel());
        mapOverlay.add(overworldTitle);


        for(int i = 0; i < overworld.getNodes().size(); i++) {
            Node node = overworld.getNode(i);
            int x = MARGIN*4 + (WIDTH/overworld.getNodes().size())*i;
            int y = (HEIGHT-150)/2;
            Button nodeButton = new Button(new Point(x, y),
                    ImageFactory.makeBorderedRect(100, 100, Color.WHITE, Color.GRAY),
                    ImageFactory.makeBorderedRect(100, 100, Color.BLUE, Color.GRAY),
                    ImageFactory.makeBorderedRect(100, 100, Color.YELLOW, Color.GRAY),
                    () -> {
                        mapOverlay.remove(selectedNode);
                        mapOverlay.removeClickable(selectedNode);
                        selectedNode = new Overlay(new Point(WIDTH/2 - 200,HEIGHT-375));
                        ImageDisplayable snBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(400,200, Color.WHITE, Color.GRAY));
                        selectedNode.add(snBackground);

//                        ImageDisplayable nodeDisplay = new ImageDisplayable(new Point(100, 75),
//                                ImageFactory.makeCenterLabeledRect(200, 50, Color.BLACK, Color.GRAY, Color.WHITE, node.getThisZone().getZoneType()));
                        selectedNode.add(new StringDisplayable( new Point(100, 25), () -> "" + node.getThisZone().getZoneType(), Color.BLUE, font));

                        Button travelToNode = new Button(new Point(100,125),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.BLUE, Color.GRAY, Color.WHITE, "Travel to Node"),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.GREEN, Color.GRAY, Color.WHITE, "Travel to Node"),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.GRAY, Color.BLACK, "Travel to Node"),
                                () -> {
                                    System.out.println("Travel to " + node.getThisZone().getZoneType());
                                });
                        selectedNode.add(travelToNode);
                        selectedNode.addClickable(travelToNode);

                        mapOverlay.add(selectedNode);
                        mapOverlay.addClickable(selectedNode);

                    },

                    () -> {

                    },
                    //Hover: Show node info
                    () -> {
                        System.out.println("Hovering over " + node.getThisZone().getZoneType());
                        this.nodeInfo = new ImageDisplayable(new Point(x + 50, y + 50),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.WHITE, Color.GRAY, Color.BLACK, "" + node.getThisZone().getZoneType()));
                        mapOverlay.add(nodeInfo);
                    },
                    //Exit: Remove node info
                    () -> {
                        mapOverlay.remove(nodeInfo);
                    });
            mapOverlay.add(nodeButton);
            mapOverlay.addClickable(nodeButton);
        }

        drawstate.addLeftOverlay(mapOverlay);
        controlstate.add(mapOverlay);

        //Add Stats Upgrade button
        upgradeStats = new Button(new Point(1525, 25),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.WHITE, Color.BLACK, Color.BLACK, "Upgrade Stats"),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.GREEN, Color.BLACK, Color.BLACK, "Upgrade Stats"),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "Upgrade Stats"),
                () -> {
                    //Remove map overlay so it cant be clicked under the stats display
                    mapOverlay.removeClickable(selectedNode);
                    mapOverlay.remove(selectedNode);
//                    mapOverlay.removeClickable(upgradeStats);
//                    mapOverlay.remove(upgradeStats);
                    drawstate.removeOverlay(mapOverlay);
                    controlstate.remove(mapOverlay);
//                    drawstate.removeOverlay(upgradeStats);
//                    controlstate.remove(upgradeStats);

                    Overlay statsOverlay = new Overlay(new Point(0,0));
//                    ImageDisplayable svBackground = new ImageDisplayable(new Point(0,0),
//                            ImageFactory.makeBorderedRect(600, 900, Color.WHITE, Color.GRAY ));
//                    statsOverlay.add(svBackground);

                    statsOverlay.add(new StringDisplayable( new Point(100, 100), () -> "Skill Points: " + currentPlayer.getPilotStats().getCurrentSkillPoints(), Color.GREEN, font));
                    statsOverlay.add(new StringDisplayable( new Point(100, 200), () -> "Flying: " + currentPlayer.getPilotStats().getFlying(), Color.GREEN, font));
                    statsOverlay.add(new StringDisplayable( new Point(100, 300), () -> "Combat: " + currentPlayer.getPilotStats().getCombat(), Color.GREEN, font));
                    statsOverlay.add(new StringDisplayable( new Point(100, 400), () -> "Charisma: " + currentPlayer.getPilotStats().getCharisma(), Color.GREEN, font));

                    if(currentPlayer.getPilotStats().getCurrentSkillPoints() > 0) {
                        Button increaseFly = new Button(new Point(450, 200),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.WHITE, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.GREEN, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "+"),
                                () -> {
                                    currentPlayer.getPilotStats().levelFlying();
                                });
                        statsOverlay.add(increaseFly);
                        statsOverlay.addClickable(increaseFly);

                        Button increaseCombat = new Button(new Point(450, 300),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.WHITE, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.GREEN, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "+"),
                                () -> {
                                    currentPlayer.getPilotStats().levelCombat();
                                });
                        statsOverlay.add(increaseCombat);
                        statsOverlay.addClickable(increaseCombat);

                        Button increaseCharisma = new Button(new Point(450, 400),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.WHITE, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.GREEN, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "+"),
                                () -> {
                                    currentPlayer.getPilotStats().levelCharisma();
                                });
                        statsOverlay.add(increaseCharisma);
                        statsOverlay.addClickable(increaseCharisma);
                    }

                    Button closeStats = new Button(new Point(200, HEIGHT/2),
                            ImageFactory.makeCenterLabeledRect(200, 50, Color.WHITE, Color.BLACK, Color.BLACK, "Close Stats"),
                            ImageFactory.makeCenterLabeledRect(200, 50, Color.GREEN, Color.BLACK, Color.BLACK, "Close Stats"),
                            ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "Close Stats"),
                            () -> {
                                drawstate.removeOverlay(statsOverlay);
                                controlstate.remove(statsOverlay);

                                //readd map and upgrade stats and title
                                drawstate.addLeftOverlay(mapOverlay);
                                controlstate.add(mapOverlay);
//                                drawstate.addRightOverlay(upgradeStats);
//                                controlstate.add(upgradeStats);
                            });
                    statsOverlay.add(closeStats);
                    statsOverlay.addClickable(closeStats);


                    drawstate.addCenterOverlay(statsOverlay);
                    controlstate.add(statsOverlay);
                });

        mapOverlay.addClickable(upgradeStats);
        mapOverlay.add(upgradeStats);
//        drawstate.addRightOverlay(upgradeStats);
//        controlstate.add(upgradeStats);

        //Add Hangar button
        Button hangarButton = new Button(new Point(1300, 25),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.WHITE, Color.BLACK, Color.BLACK, "Hangar"),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.GREEN, Color.BLACK, Color.BLACK, "Hangar"),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "Hangar"),
                () -> {
                    selectedShip = 0;
                    //remove map overlay
                    mapOverlay.removeClickable(selectedNode);
                    mapOverlay.remove(selectedNode);
                    drawstate.removeOverlay(mapOverlay);
                    controlstate.remove(mapOverlay);

                    //create hangar overlay
                    Overlay hangarOverlay = new Overlay(new Point(0,0));

                    //Add exit hangar button
                    Button exitHangar = new Button(new Point(1425, 25),
                            ImageFactory.getExitHangarButton(),
                            ImageFactory.getExitHangarButton(),
                            ImageFactory.getExitHangarButton(),
                            () -> {
                                //remove hangar overlay
                                drawstate.removeOverlay(hangarOverlay);
                                controlstate.remove(hangarOverlay);

                                //readd map overlay
                                drawstate.addLeftOverlay(mapOverlay);
                                controlstate.add(mapOverlay);


                            });
                    hangarOverlay.add(exitHangar);
                    hangarOverlay.addClickable(exitHangar);

                    //add ship images
                    StringDisplayable shipTitle = new StringDisplayable(new Point(0, 0), () -> "Ship " + (selectedShip+1), Color.GREEN, font);
                    int height = shipTitle.getSize().height;
                    int width = shipTitle.getSize().width;
                    shipTitle.getOrigin().setLocation((WIDTH/2)-(width/2), (HEIGHT/2)-250);
                    hangarOverlay.add(shipTitle);
                    ImageDisplayable currentShip = new ImageDisplayable(new Point((WIDTH/2)-75,(HEIGHT/2)-100), currentPlayer.getActiveShip().getShipImageBlack());
                    hangarOverlay.add(currentShip);

                    //add arrow buttons
                    Button leftArrow = new Button(new Point((WIDTH/2)-300, (HEIGHT/2)-125),
                            ImageFactory.getArrowLeft(),
                            ImageFactory.getArrowLeft(),
                            ImageFactory.getArrowLeft(),
                            () -> {
                                //todo: change if once hangar and activeship logic are changed
                                if(true)
                                    selectedShip--;
                            });
                    hangarOverlay.add(leftArrow);
                    hangarOverlay.addClickable(leftArrow);

                    Button rightArrow = new Button(new Point((WIDTH/2)+125, (HEIGHT/2)-125),
                            ImageFactory.getArrowRight(),
                            ImageFactory.getArrowRight(),
                            ImageFactory.getArrowRight(),
                            () -> {
                                //todo: change if once hangar and activeship logic are changed
                                if(true)
                                    selectedShip++;
                            });
                    hangarOverlay.add(rightArrow);
                    hangarOverlay.addClickable(rightArrow);

                    //add arrow buttons
                    Button setCurrentShip = new Button(new Point((WIDTH/2)-225, (HEIGHT/2)+80),
                            ImageFactory.getSetShipbutton(),
                            ImageFactory.getSetShipbutton(),
                            ImageFactory.getSetShipbutton(),
                            () -> {
                                //todo: change active ship
                            });
                    hangarOverlay.add(setCurrentShip);
                    hangarOverlay.addClickable(setCurrentShip);

                    Button changeParts = new Button(new Point((WIDTH/2)-225, (HEIGHT/2)+170),
                            ImageFactory.getChangePartsButton(),
                            ImageFactory.getChangePartsButton(),
                            ImageFactory.getChangePartsButton(),
                            () -> {
                                //todo: add change part overlay

                            });
                    hangarOverlay.add(changeParts);
                    hangarOverlay.addClickable(changeParts);


                    drawstate.addCenterOverlay(hangarOverlay);
                    controlstate.add(hangarOverlay);
                });
        mapOverlay.addClickable(hangarButton);
        mapOverlay.add(hangarButton);

        //Add Exit button
        Button exitButton = new Button(new Point(1525, 825),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.WHITE, Color.BLACK, Color.BLACK, "Exit"),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.GREEN, Color.BLACK, Color.BLACK, "Exit"),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "Exit"),
                () -> {
                    System.exit(0);
                });
        mapOverlay.addClickable(exitButton);
        mapOverlay.add(exitButton);


        //drawstate.addCenterOverlay(overworldTitle);
    }
}
