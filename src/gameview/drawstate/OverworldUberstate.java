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
    private static final int WIDTH = 1800;
    private static final int MARGIN = 10;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private Overworld overworld;
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

        Drawstate drawstate = getDrawstate();
        ClickableControlstate controlstate = getControlstate();
//        //Add title box
//        ImageDisplayable tpTitle =
//                new ImageDisplayable(new Point(0,0), ImageFactory.getOverworldLabel());

        //drawstate.addCenterOverlay(tpTitle);

        //Create and add Trading Post Overlay
//        Overlay tpOverlay = new Overlay(new Point(0,0));
//        Displayable tpBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLACK, Color.BLACK));
//        tpOverlay.add(tpBackground);
//        Button tpButton = new Button(new Point(WIDTH/5, (HEIGHT/2)-150),
//                ImageFactory.getTradingPostBase(),
//                ImageFactory.getTradingPostHover(),
//                ImageFactory.getTradingPostPress(),
//                () -> {
//                    System.out.println("TradingPost clicked!");
//                });
//        tpOverlay.add(tpButton);
//        tpOverlay.addClickable(tpButton);
//        drawstate.addLeftOverlay(tpOverlay);
//        controlstate.add(tpOverlay);
//
//        //Create and add Battle Zone Overlay
//        Overlay bzOverlay = new Overlay(new Point(0, 0));
//        Displayable bzBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLACK, Color.BLACK));
//        bzOverlay.add(bzBackground);
//        Button bzButton = new Button(new Point(WIDTH/5, (HEIGHT/2)-150),
//                ImageFactory.getBattleZoneBase(),
//                ImageFactory.getBattleZoneHover(),
//                ImageFactory.getBattleZonePress(),
//                () -> {
//                    System.out.println("BattleZone clicked!");
//                });
//        bzOverlay.add(bzButton);
//        bzOverlay.addClickable(bzButton);
//        drawstate.addRightOverlay(bzOverlay);
//        controlstate.add(bzOverlay);
        Overlay mapOverlay = new Overlay(new Point(0, 0));
        ImageDisplayable mapBackground = new ImageDisplayable(new Point(0,50),
                ImageFactory.makeBorderedRect(WIDTH, HEIGHT - 200, Color.BLUE, Color.GRAY));
        mapOverlay.add(mapBackground);


        for(int i = 0; i < overworld.getNodes().size(); i++) {
            Node node = overworld.getNode(i);
            int x = MARGIN*4 + (WIDTH/overworld.getNodes().size())*i;
            int y = (HEIGHT-150)/2;
            Button nodeButton = new Button(new Point(x, y),
                    ImageFactory.makeBorderedRect(100, 100, Color.WHITE, Color.GRAY),
                    ImageFactory.makeBorderedRect(100, 100, Color.RED, Color.GRAY),
                    ImageFactory.makeBorderedRect(100, 100, Color.YELLOW, Color.GRAY),
                    () -> {
                        mapOverlay.remove(selectedNode);
                        mapOverlay.removeClickable(selectedNode);
                        selectedNode = new Overlay(new Point(WIDTH-1100,HEIGHT-375));
                        ImageDisplayable snBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(400,200, Color.WHITE, Color.GRAY));
                        selectedNode.add(snBackground);

//                        ImageDisplayable nodeDisplay = new ImageDisplayable(new Point(100, 75),
//                                ImageFactory.makeCenterLabeledRect(200, 50, Color.BLACK, Color.GRAY, Color.WHITE, node.getThisZone().getZoneType()));
                        selectedNode.add(new StringDisplayable( new Point(100, 25), () -> "" + node.getThisZone().getZoneType(), Color.RED, font));

                        Button travelToNode = new Button(new Point(100,125),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.BLACK, Color.GRAY, Color.WHITE, "Travel to Node"),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.RED, Color.GRAY, Color.WHITE, "Travel to Node"),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.GRAY, Color.WHITE, "Travel to Node"),
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

        //Add title box
        ImageDisplayable tpTitle =
                new ImageDisplayable(new Point(0,0), ImageFactory.getOverworldLabel());

        drawstate.addCenterOverlay(tpTitle);


    }
}
