package gameview.drawstate;

import Model.Pilot.Player;
import Model.TradingPost.TradingPost;
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

public class OverworldUberstate extends Uberstate
{
    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1800;
    private static final int MARGIN = 10;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private TradingPost currentTP;
    private Player currentPlayer;

    public OverworldUberstate(Renderstate renderstate) {
        super(new Drawstate(), renderstate, new ClickableControlstate());

        Drawstate drawstate = getDrawstate();
        ClickableControlstate controlstate = getControlstate();
//        //Add title box
//        ImageDisplayable tpTitle =
//                new ImageDisplayable(new Point(0,0), ImageFactory.getOverworldLabel());

        //drawstate.addCenterOverlay(tpTitle);

        //Create and add Trading Post Overlay
        Overlay tpOverlay = new Overlay(new Point(0,0));
        Displayable tpBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLACK, Color.BLACK));
        tpOverlay.add(tpBackground);
        Button tpButton = new Button(new Point(WIDTH/5, (HEIGHT/2)-150),
                ImageFactory.getTradingPostBase(),
                ImageFactory.getTradingPostHover(),
                ImageFactory.getTradingPostPress(),
                () -> {
                    System.out.println("TradingPost clicked!");
                });
        tpOverlay.add(tpButton);
        tpOverlay.addClickable(tpButton);
        drawstate.addLeftOverlay(tpOverlay);
        controlstate.add(tpOverlay);

        //Create and add Battle Zone Overlay
        Overlay bzOverlay = new Overlay(new Point(0, 0));
        Displayable bzBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLACK, Color.BLACK));
        bzOverlay.add(bzBackground);
        Button bzButton = new Button(new Point(WIDTH/5, (HEIGHT/2)-150),
                ImageFactory.getBattleZoneBase(),
                ImageFactory.getBattleZoneHover(),
                ImageFactory.getBattleZonePress(),
                () -> {
                    System.out.println("BattleZone clicked!");
                });
        bzOverlay.add(bzButton);
        bzOverlay.addClickable(bzButton);
        drawstate.addRightOverlay(bzOverlay);
        controlstate.add(bzOverlay);

        //Add title box
        ImageDisplayable tpTitle =
                new ImageDisplayable(new Point(0,0), ImageFactory.getOverworldLabel());

        drawstate.addCenterOverlay(tpTitle);


    }
}
