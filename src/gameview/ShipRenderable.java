package gameview;

import Model.Ship.Ship;
import Model.physics.Body;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.renderable.glut.ConeRenderable;

public class ShipRenderable extends ConeRenderable
{
    private Body<Ship> ship;
    private Point3D location;

    public ShipRenderable(Body<Ship> ship)
    {
        super(ship.getCollidable().getRear(), ship.getCollidable().getSize().getWidth()/2,
                ship.getCollidable().getSize().getLength(), ship.getCollidable().getOrientation(), 10);
        this.ship = ship;
        this.update();
    }

    @Override
    public void update() { location = ship.getCollidable().getRear(); }

    @Override
    public float getX() { return location.getX(); }

    @Override
    public float getY() { return location.getY(); }

    @Override
    public float getZ() { return location.getZ(); }

    @Override
    public float getYaw() { return ship.getCollidable().getOrientation().getYaw(); }

    @Override
    public float getPitch() { return ship.getCollidable().getOrientation().getPitch(); }

    @Override
    public float getRoll() { return ship.getCollidable().getOrientation().getRoll(); }

    @Override
    public boolean expired()
    {
        return ship.get().expired();
    }
}
