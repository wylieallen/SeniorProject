package gameview.renderables;

import Model.Ship.Ship;
import Model.physics.Body;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.model3d.Model3DFactory;
import guiframework.gui3d.renderable.BufferedRenderable;
import guiframework.gui3d.renderable.glut.ConeRenderable;

public class ShipRenderable extends BufferedRenderable
{
    private Body<Ship> ship;
    private Point3D location;

    public ShipRenderable(Body<Ship> ship)
    {
        super(ship.getOrigin(), new Dimension3D(ship.getSize()), ship.getOrientation(), Model3DFactory.getFeisarModel());
        this.ship = ship;
        this.update();
    }

    @Override
    public void update() { location = ship.getOrigin(); }

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
        return ship.get().expired();
    }
}
