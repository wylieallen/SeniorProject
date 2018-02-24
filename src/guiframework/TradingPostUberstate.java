package guiframework;

import guiframework.clickable.Button;
import guiframework.displayable.Displayable;
import guiframework.displayable.ImageDisplayable;

import java.awt.*;

public class TradingPostUberstate extends Uberstate{
    static final int HEIGHT = 128;
    static final int WIDTH = 640;

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
                () -> {},
                () -> {},
                () -> {},
                () -> {});

        this.addClickable(buyButton);
        this.addLeftOverlay(buyButton);

        //Add Sell Button
        Button sellButton = new Button(new Point(0, HEIGHT * 2),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.WHITE, Color.GRAY, Color.BLACK, "Sell"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.RED, Color.GRAY, Color.WHITE, "Sell"),
                ImageFactory.makeCenterLabeledRect(WIDTH, HEIGHT, Color.ORANGE, Color.GRAY, Color.BLACK, "Sell"),
                () -> {},
                () -> {},
                () -> {},
                () -> {});

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
                () -> {},
                () -> {},
                () -> {},
                () -> {});

        this.addClickable(exitButton);
        this.addLeftOverlay(exitButton);

    }
}
