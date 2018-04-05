package gameview.renderables;

import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.model3d.Model3DFactory;
import guiframework.gui3d.renderable.BufferedRenderable;
import guiframework.gui3d.renderable.glut.SphereRenderable;

public class ProjectileRenderable extends BufferedRenderable
{
    private Point3D location;
    private Body<Projectile> projectile;

    public ProjectileRenderable(Body<Projectile> projectile)
    {
        super(projectile.getCenter(), projectile.getSize(), projectile.getOrientation(), Model3DFactory.getProjectileModel());
        this.projectile = projectile;
        update();
    }

    @Override
    public void update() { location = projectile.getCenter(); }

    @Override
    public float getX() { return location.getX(); }

    @Override
    public float getY() { return location.getY(); }

    @Override
    public float getZ() { return location.getZ(); }

    @Override
    public float getYaw() { return projectile.getOrientation().getYaw(); }

    @Override
    public float getPitch() { return projectile.getOrientation().getPitch(); }

    @Override
    public float getRoll() { return projectile.getOrientation().getRoll(); }

    @Override
    public boolean expired()
    {
        return projectile.get().expired();
    }
}
