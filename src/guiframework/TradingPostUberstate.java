package guiframework;

import guiframework.clickable.Button;
import guiframework.displayable.Displayable;

import java.awt.*;

public class TradingPostUberstate extends Uberstate{

    public TradingPostUberstate() {

        Button buyButton = new Button(new Point(256, 256),
                ImageFactory.makeCenterLabeledRect(128, 128, Color.WHITE, Color.GRAY, Color.BLACK, "Buy"),
                ImageFactory.makeCenterLabeledRect(128, 128, Color.RED, Color.GRAY, Color.BLACK, "Buy"),
                ImageFactory.makeCenterLabeledRect(128, 128, Color.ORANGE, Color.GRAY, Color.BLACK, "Buy"),
                () -> {},
                () -> {},
                () -> {},
                () -> {});

        this.addClickable(buyButton);
        this.addOverlay(buyButton);
    }
}
