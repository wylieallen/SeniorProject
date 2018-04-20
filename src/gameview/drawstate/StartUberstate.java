package gameview.drawstate;

import Model.Items.Consumables.HealthConsumable;
import Model.Items.Consumables.ShieldConsumable;
import Model.Items.Inventory;
import Model.Items.RandomItemGenerator;
import Model.Map.Node;
import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Map.Zones.TradingZone;
import Model.Pilot.Player;
import Model.Ship.Ship;
import Model.Ship.ShipBuilder.ShipBuilder;
import Model.Ship.ShipParts.ShipEngine;
import Model.Ship.ShipParts.ShipHull;
import Model.Ship.ShipParts.ShipShield;
import Model.TradingPost.BountyMission;
import Model.TradingPost.TradingPost;
import Model.TradingPost.Wallet;
import Utility.Rarity;
import gameview.TransitionObserver;
import guiframework.Uberstate;
import guiframework.control.ClickableControlstate;
import guiframework.control.clickable.Overlay;
import guiframework.gui2d.Drawstate;
import guiframework.gui2d.ImageFactory;
import guiframework.control.clickable.Button;
import guiframework.gui2d.displayable.ImageDisplayable;
import guiframework.gui3d.Renderstate;

import java.awt.*;
import java.util.ArrayList;

public class StartUberstate extends Uberstate {
    private TransitionObserver transitionObserver;
    private Overworld overworld;
    public StartUberstate(TransitionObserver transitionObserver, Renderstate renderstate, Player player, boolean initialized) {
        super(new Drawstate(), renderstate, new ClickableControlstate());
        this.transitionObserver = transitionObserver;

        if(!initialized)
        {
            ShipBuilder buildShip = new ShipBuilder();
            Ship myShip = buildShip.buildRandomShip(player, Rarity.COMMON);
            player.getShipHangar().addShip(myShip);
            player.setActiveShip(myShip);

            TradingPost tp1 = new TradingPost(new Inventory(20), new Wallet(500), new ArrayList<BountyMission>());
            overworld = Overworld.getOverworld();
            overworld.addNode(new Node(new BattleZone(1)));
            overworld.addNode(new Node(new BattleZone(2)));
            overworld.addNode(new Node(new BattleZone(3)));
            overworld.addNode(new Node(new TradingZone(tp1)));
            overworld.addNode(new Node(new BattleZone(4)));

            Ship ship2 = buildShip.buildRandomShip(player, Rarity.RARE);
            Ship ship3 = buildShip.buildRandomShip(player, Rarity.EPIC);
            Ship ship4 = buildShip.buildRandomShip(player, Rarity.LEGENDARY);
            player.getShipHangar().addShip(ship2);
            player.getShipHangar().addShip(ship3);
            player.getShipHangar().addShip(ship4);
            player.getActiveShip().getInventory().addItem(new ShipHull(100,Rarity.COMMON,100, 16));
            player.getActiveShip().getInventory().addItem(new ShipShield(100,100, Rarity.COMMON));

        }

        Drawstate drawstate = getDrawstate();
        ClickableControlstate controlstate = getControlstate();

        //Add space background
        ImageDisplayable spaceBackground =
                new ImageDisplayable(new Point(0,0), ImageFactory.getSpaceBackground());
        drawstate.addUnderlay(spaceBackground);

        Overlay startGameOverlay = new Overlay(new Point());

        //Add start button
        //todo: add start button images
        Button startGameButton = new Button(new Point(0,165),
                ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Start Game"),
                ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.WHITE, "Start Game"),
                ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Start Game"),
                transitionObserver::switchToOverworld);

        startGameOverlay.add(startGameButton);
        startGameOverlay.addClickable(startGameButton);

        drawstate.addCenterOverlay(startGameOverlay);
        controlstate.add(startGameOverlay);


    }
}
