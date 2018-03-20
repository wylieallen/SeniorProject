package gameview;

import Model.Ship.Ship;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.Renderable.glut.ConeRenderable;

public class ShipRenderable extends ConeRenderable
{
    private Ship ship;
    private Point3D location;

    public ShipRenderable(Ship ship)
    {
        super(ship.getRear(), ship.getSize().getWidth()/2, ship.getSize().getLength(), ship.getOrientation(), 10);
        this.ship = ship;
        this.update();
    }

    @Override
    public void update() { location = ship.getRear(); }

    @Override
    public float getX() { return location.getX(); }

    @Override
    public float getY() { return location.getY(); }

    @Override
    public float getZ() { return location.getZ(); }

    @Override
    public float getYaw() { return ship.getOrientation().getYaw(); }

    @Override
    public float getPitch() { return ship.getOrientation().getPitch(); }

    @Override
    public float getRoll() { return ship.getOrientation().getRoll(); }

    @Override
    public boolean expired()
    {
        return ship.expired();
    }
}
