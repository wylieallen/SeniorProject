package gameview.renderables.debug;

import Model.physics.collidable.Collidable;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.renderable.glut.SphereRenderable;

public class MinpointRenderable extends SphereRenderable
{
    private Collidable subject;
    private Point3D minpoint;

    public MinpointRenderable(Collidable subject)
    {
        super(subject.getMinPoint(), new Orientation3D(), 0.1f, 5, 5);
        this.subject = subject;
        update();
    }

    @Override
    public void update()
    {
        this.minpoint = subject.getMinPoint();
    }

    @Override
    public float getX() { return minpoint.getX(); }

    @Override
    public float getY() { return minpoint.getY(); }

    @Override
    public float getZ() { return minpoint.getZ(); }
}
