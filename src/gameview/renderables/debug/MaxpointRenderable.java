package gameview.renderables.debug;

import Model.physics.collidable.Collidable;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.renderable.glut.SphereRenderable;

public class MaxpointRenderable extends SphereRenderable
{
    private Collidable subject;
    private Point3D maxpoint;

    public MaxpointRenderable(Collidable subject)
    {
        super(subject.getMinPoint(), new Orientation3D(), 0.1f, 5, 5);
        this.subject = subject;
        update();
    }

    @Override
    public void update()
    {
        this.maxpoint = subject.getMaxPoint();
    }

    @Override
    public float getX() { return maxpoint.getX(); }

    @Override
    public float getY() { return maxpoint.getY(); }

    @Override
    public float getZ() { return maxpoint.getZ(); }
}
