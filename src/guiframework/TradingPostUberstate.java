package guiframework;

import guiframework.clickable.Button;
import guiframework.displayable.CompositeDisplayable;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;
import guiframework.displayable.StringDisplayable;

import java.awt.*;

public class TradingPostUberstate extends Uberstate{
    static final int HEIGHT = 128;
    static final int WIDTH = 640;
    private int money = 100;
    private CompositeDisplayable playerInventory;
    private CompositeDisplayable tpInventory = new CompositeDisplayable(new Point());

    public TradingPostUberstate() {

        //Add space background

        this.addUnderlay(Displayable.NULL);

        ImageDisplayable background =
                new ImageDisplayable(new Point(WIDTH,0), ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT*5, Color.black, Color.GRAY, Color.WHITE, "Space Background. Pretty stars and stuff."));

        this.addUnderlay(background);

        this.addUnderlay(Displayable.NULL);

        //Add title box
        ImageDisplayable tpTitle =
                new ImageDisplayable(new Point(0,0), ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.BLUE, Color.GRAY, Color.RED, "Trading Post"));

        this.addLeftOverlay(tpTitle);

        //Add Buy Button
        Button buyButton = new Button(new Point(0, HEIGHT),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Buy"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Buy"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Buy"),
                () ->
                {
                    this.removeAllRightOverlays();
                    this.addRightOverlay(this.tpInventory);
                });

        this.addClickable(buyButton);
        this.addLeftOverlay(buyButton);

        //Add Sell Button
        Button sellButton = new Button(new Point(0, HEIGHT * 2),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Sell"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Sell"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Sell"),
                () ->
                {
                    this.removeAllRightOverlays();
                    this.addRightOverlay(this.playerInventory);
                });

        this.addClickable(sellButton);
        this.addLeftOverlay(sellButton);

        //Add Bounty Missions Button
        Button bountyButton = new Button(new Point(0, HEIGHT * 3),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Bounty Missions"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Bounty Missions"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Bounty Missions"),
                () -> {},
                () -> {},
                () -> {},
                () -> {});

        this.addClickable(bountyButton);
        this.addLeftOverlay(bountyButton);

        //Add Exit Button
        Button exitButton = new Button(new Point(0, HEIGHT * 4),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Exit"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Exit"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Exit"),
                () -> {});

        this.addClickable(exitButton);
        this.addLeftOverlay(exitButton);

        CompositeDisplayable playerInventory = new CompositeDisplayable(new Point());
        Displayable piBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(256, 256, Color.WHITE, Color.GRAY));
        playerInventory.add(piBackground);
        playerInventory.add(new StringDisplayable( new Point(16, 64), () -> " Player MONEY: " + getMoney()));
        this.playerInventory = playerInventory;

        CompositeDisplayable tpInventory = new CompositeDisplayable(new Point());
        Displayable tpBackground = new ImageDisplayable(new Point(0, 0), ImageFactory.makeBorderedRect(256, 256, Color.WHITE, Color.GRAY));
        playerInventory.add(tpBackground);
        playerInventory.add(new StringDisplayable( new Point(16, 64), () -> "Trading Post MONEY: " + getMoney()));
        this.playerInventory = playerInventory;

    }

    // Placeholder money example stuff goes here


    public int getMoney() { return money; }
    public void modifyMoney(int newMoney) { money += newMoney; }
}
