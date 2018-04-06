package gameview.drawstate;

import Model.Items.Inventory;
import Model.Map.Node;
import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Map.Zones.TradingZone;
import Model.Map.Zones.Zone;
import Model.Pilot.Player;
import Model.TradingPost.BountyMission;
import Model.TradingPost.TradingPost;
import Model.TradingPost.Wallet;
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

        Drawstate drawstate = getDrawstate();
        ClickableControlstate controlstate = getControlstate();


        mapOverlay = new Overlay(new Point(0, 0));
        ImageDisplayable mapBackground = new ImageDisplayable(new Point(0,0),
                ImageFactory.makeBorderedRect(WIDTH, 900, Color.BLACK, Color.WHITE));
        mapOverlay.add(mapBackground);

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
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.RED, Color.GRAY, Color.WHITE, "Travel to Node"),
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
                ImageFactory.makeCenterLabeledRect(200, 50, Color.RED, Color.BLACK, Color.BLACK, "Upgrade Stats"),
                ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "Upgrade Stats"),
                () -> {
                    //Remove map and upgrade button so it cant be clicked under the stats display
                    mapOverlay.removeClickable(selectedNode);
                    mapOverlay.remove(selectedNode);
//                    mapOverlay.removeClickable(upgradeStats);
//                    mapOverlay.remove(upgradeStats);
                    drawstate.removeOverlay(mapOverlay);
                    controlstate.remove(mapOverlay);
//                    drawstate.removeOverlay(upgradeStats);
//                    controlstate.remove(upgradeStats);

                    Overlay statsView = new Overlay(new Point(0,0));
                    ImageDisplayable svBackground = new ImageDisplayable(new Point(0,0),
                            ImageFactory.makeBorderedRect(1750/3, 900, Color.WHITE, Color.GRAY ));
                    statsView.add(svBackground);

                    statsView.add(new StringDisplayable( new Point(300, 100), () -> "Skill Points: " + currentPlayer.getPilotStats().getCurrentSkillPoints(), Color.RED, font));
                    statsView.add(new StringDisplayable( new Point(300, 200), () -> "Flying: " + currentPlayer.getPilotStats().getFlying(), Color.RED, font));
                    statsView.add(new StringDisplayable( new Point(300, 300), () -> "Combat: " + currentPlayer.getPilotStats().getCombat(), Color.RED, font));
                    statsView.add(new StringDisplayable( new Point(300, 400), () -> "Charisma: " + currentPlayer.getPilotStats().getCharisma(), Color.RED, font));

                    if(currentPlayer.getPilotStats().getCurrentSkillPoints() > 0) {
                        Button increaseFly = new Button(new Point(500, 200),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.WHITE, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.RED, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "+"),
                                () -> {
                                    currentPlayer.getPilotStats().levelFlying();
                                });
                        statsView.add(increaseFly);
                        statsView.addClickable(increaseFly);

                        Button increaseCombat = new Button(new Point(500, 300),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.WHITE, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.RED, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "+"),
                                () -> {
                                    currentPlayer.getPilotStats().levelCombat();
                                });
                        statsView.add(increaseCombat);
                        statsView.addClickable(increaseCombat);

                        Button increaseCharisma = new Button(new Point(500, 400),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.WHITE, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.RED, Color.BLACK, Color.BLACK, "+"),
                                ImageFactory.makeCenterLabeledRect(50, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "+"),
                                () -> {
                                    currentPlayer.getPilotStats().levelCharisma();
                                });
                        statsView.add(increaseCharisma);
                        statsView.addClickable(increaseCharisma);
                    }

                    Button closeStats = new Button(new Point(300, HEIGHT/2),
                            ImageFactory.makeCenterLabeledRect(200, 50, Color.WHITE, Color.BLACK, Color.BLACK, "Close Stats"),
                            ImageFactory.makeCenterLabeledRect(200, 50, Color.RED, Color.BLACK, Color.BLACK, "Close Stats"),
                            ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.BLACK, Color.BLACK, "Close Stats"),
                            () -> {
                                drawstate.removeOverlay(statsView);
                                controlstate.remove(statsView);

                                //readd map and upgrade stats and title
                                drawstate.addLeftOverlay(mapOverlay);
                                controlstate.add(mapOverlay);
//                                drawstate.addRightOverlay(upgradeStats);
//                                controlstate.add(upgradeStats);
                            });
                    statsView.add(closeStats);
                    statsView.addClickable(closeStats);


                    drawstate.addCenterOverlay(statsView);
                    controlstate.add(statsView);
                });

        mapOverlay.addClickable(upgradeStats);
        mapOverlay.add(upgradeStats);
//        drawstate.addRightOverlay(upgradeStats);
//        controlstate.add(upgradeStats);


        //drawstate.addCenterOverlay(overworldTitle);
    }
}
