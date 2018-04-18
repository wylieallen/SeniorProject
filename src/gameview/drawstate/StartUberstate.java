package gameview.drawstate;

import guiframework.Uberstate;
import guiframework.control.ClickableControlstate;
import guiframework.control.clickable.Overlay;
import guiframework.gui2d.Drawstate;
import guiframework.gui2d.ImageFactory;
import guiframework.control.clickable.Button;
import guiframework.gui2d.displayable.ImageDisplayable;
import guiframework.gui3d.Renderstate;

import java.awt.*;

public class StartUberstate extends Uberstate {


    public StartUberstate(Renderstate renderstate) {
        super(new Drawstate(), renderstate, new ClickableControlstate());


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
                () ->
                {

                });

        startGameOverlay.add(startGameButton);
        startGameOverlay.addClickable(startGameButton);

        drawstate.addCenterOverlay(startGameOverlay);
        controlstate.add(startGameOverlay);


    }
}
