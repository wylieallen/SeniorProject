package gameview.renderables;

import Model.Map.Zones.Asteroid;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.RandomNumberGenerator;
import guiframework.gui3d.model3d.Model3DFactory;
import guiframework.gui3d.renderable.BufferedRenderable;
import guiframework.gui3d.renderable.glut.SphereRenderable;

import java.util.Random;

public class AsteroidRenderable extends BufferedRenderable
{
    private Point3D location;
    private Body<Asteroid> asteroid;

    private Orientation3D orientation;

    public AsteroidRenderable(Body<Asteroid> asteroid)
    {
        super(asteroid.getOrigin(), asteroid.getSize(), asteroid.getOrientation(), Model3DFactory.getAsteroidModel());
        Random rand = new Random();
        this.orientation = new Orientation3D(rand.nextFloat() * 360, rand.nextFloat() * 360, rand.nextFloat() * 360);
        // AsteroidRenderable generates random pitch/yaw/roll
        // Change this if you ever want the Asteroid itself to be rotating
        this.asteroid = asteroid;
        update();
    }

    @Override
    public void update() { location = asteroid.getOrigin(); }

    @Override
    public float getX() { return location.getX(); }

    @Override
    public float getY() { return location.getY(); }

    @Override
    public float getZ() { return location.getZ(); }

    @Override
    public float getYaw() { return orientation.getYaw(); }

    @Override
    public float getPitch() { return orientation.getPitch(); }

    @Override
    public float getRoll() { return orientation.getRoll(); }

    @Override
    public boolean expired()
    {
        return asteroid.get().expired();
    }
}
