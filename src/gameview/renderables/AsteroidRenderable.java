package gameview.renderables;

import Model.Map.Zones.Asteroid;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.model3d.Model3DFactory;
import guiframework.gui3d.renderable.BufferedRenderable;
import guiframework.gui3d.renderable.glut.SphereRenderable;

public class AsteroidRenderable extends BufferedRenderable
{
    private Point3D location;
    private Body<Asteroid> asteroid;

    public AsteroidRenderable(Body<Asteroid> asteroid)
    {
        super(asteroid.getCenter(), asteroid.getSize(), asteroid.getOrientation(), Model3DFactory.getAsteroidModel());
        this.asteroid = asteroid;
        update();
    }

    @Override
    public void update() { location = asteroid.getCenter(); }

    @Override
    public float getX() { return location.getX(); }

    @Override
    public float getY() { return location.getY(); }

    @Override
    public float getZ() { return location.getZ(); }

    @Override
    public float getYaw() { return asteroid.getOrientation().getYaw(); }

    @Override
    public float getPitch() { return asteroid.getOrientation().getPitch(); }

    @Override
    public float getRoll() { return asteroid.getOrientation().getRoll(); }

    @Override
    public boolean expired()
    {
        return asteroid.get().expired();
    }
}
