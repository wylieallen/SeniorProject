package gameview.renderables.debug;

import Model.physics.collidable.Collidable;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.renderable.glut.SphereRenderable;

public class TerminusRenderable extends SphereRenderable
{
    private Collidable subject;
    private Point3D terminus;

    public TerminusRenderable(Collidable subject)
    {
        super(subject.getTerminus(), new Orientation3D(), 0.1f, 5, 5);
        this.subject = subject;
        update();
    }

    @Override
    public void update()
    {
        this.terminus = subject.getTerminus();
    }

    @Override
    public float getX() { return terminus.getX(); }

    @Override
    public float getY() { return terminus.getY(); }

    @Override
    public float getZ() { return terminus.getZ(); }
}
