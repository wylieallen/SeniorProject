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
import guiframework.gui2d.ImageFactory;
import guiframework.gui2d.clickable.Button;
import guiframework.gui2d.clickable.ItemButton;
import guiframework.gui2d.clickable.Overlay;
import guiframework.gui2d.displayable.CompositeDisplayable;
import guiframework.gui2d.displayable.Displayable;
import guiframework.gui2d.displayable.ImageDisplayable;
import guiframework.gui2d.displayable.StringDisplayable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OverworldUberstate extends Uberstate{
    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1800;
    private static final int MARGIN = 10;
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private TradingPost currentTP;
    private Player currentPlayer;

    public OverworldUberstate() {
//        //Add title box
//        ImageDisplayable tpTitle =
//                new ImageDisplayable(new Point(0,0), ImageFactory.getOverworldLabel());
//
//        this.addCenterOverlay(tpTitle);

        //Create and add Trading Post Overlay
        Overlay tpOverlay = new Overlay(new Point(0,0));
        Displayable tpBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLACK, Color.BLACK));
        tpOverlay.add(tpBackground);
        Button tpButton = new Button(new Point(WIDTH/5, (HEIGHT/2)-150),
                ImageFactory.getTradingPostBase(),
                ImageFactory.getTradingPostHover(),
                ImageFactory.getTradingPostPress(),
                () -> {

                });
        tpOverlay.add(tpButton);
        tpOverlay.addClickable(tpButton);
        this.addLeftOverlay(tpOverlay);
        this.addClickable(tpOverlay);

        //Create and add Battle Zone Overlay
        Overlay bzOverlay = new Overlay(new Point(0, 0));
        Displayable bzBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(WIDTH/2, HEIGHT, Color.BLACK, Color.BLACK));
        bzOverlay.add(bzBackground);
        Button bzButton = new Button(new Point(WIDTH/5, (HEIGHT/2)-150),
                ImageFactory.getBattleZoneBase(),
                ImageFactory.getBattleZoneHover(),
                ImageFactory.getBattleZonePress(),
                () -> {

                });
        bzOverlay.add(bzButton);
        bzOverlay.addClickable(bzButton);
        this.addRightOverlay(bzOverlay);
        this.addClickable(bzOverlay);

        //Add title box
        ImageDisplayable tpTitle =
                new ImageDisplayable(new Point(0,0), ImageFactory.getOverworldLabel());

        this.addCenterOverlay(tpTitle);


    }
}
