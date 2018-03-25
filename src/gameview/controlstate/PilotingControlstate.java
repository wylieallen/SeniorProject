package gameview.controlstate;

import Model.Ship.Ship;
import guiframework.control.ClickableControlstate;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PilotingControlstate extends ClickableControlstate
{
    private Ship playerShip;

    private boolean pivotEnabled;

    private Point centerPt;

    public PilotingControlstate(Ship playerShip, Point centerPt)
    {
        this.playerShip = playerShip;
        this.centerPt = centerPt;
        this.pivotEnabled = false;
    }

    @Override
    public void parseMouseMove(Point mousePt)
    {
        super.parseMouseMove(mousePt);

        if(pivotEnabled) pivotCamera(mousePt);
    }

    @Override
    public void parseMousePress(int buttoncode)
    {
        super.parseMousePress(buttoncode);
        if(buttoncode == MouseEvent.BUTTON1)
        {
            pivotEnabled = true;
        }
        else if(buttoncode == MouseEvent.BUTTON2)
        {
            // start shooting

        }
    }

    @Override
    public void parseMouseRelease(int buttoncode)
    {
        super.parseMouseRelease(buttoncode);
        if(buttoncode == MouseEvent.BUTTON1)
        {
            pivotEnabled = false;
            playerShip.ceaseRotation();
        }
        else if (buttoncode == MouseEvent.BUTTON2)
        {
            // stop shooting
        }
    }

    public void setCenterPt(Point centerPt)
    {
        this.centerPt = centerPt;
    }

    public void pivotCamera(Point mousePt)
    {
        double dx = mousePt.x - centerPt.x;
        double dy = mousePt.y - centerPt.y;
        double mag = Math.sqrt(dx*dx + dy*dy);

        dx /= mag;
        dy /= mag;

        if (dx < 0)
        {
            playerShip.setYawingLeft(true);
            playerShip.setYawingRight(false);
            dx = -dx;
        }
        else if (dx > 0)
        {
            playerShip.setYawingLeft(false);
            playerShip.setYawingRight(true);
        }
        else
        {
            playerShip.setYawingLeft(false);
            playerShip.setYawingRight(false);
            dx = 0;
        }

        if (dy > 0)
        {
            playerShip.setPitchingDown(true);
            playerShip.setPitchingUp(false);
        }
        else if (dy < 0)
        {
            dy = -dy;
            playerShip.setPitchingDown(false);
            playerShip.setPitchingUp(true);
        }
        else
        {
            dy = 0;
            playerShip.setPitchingDown(false);
            playerShip.setPitchingUp(false);
        }

        mag *= 0.005;
        playerShip.setYawSpeed((float) (mag * dx));
        playerShip.setPitchSpeed((float) (mag * dy));
    }
}
