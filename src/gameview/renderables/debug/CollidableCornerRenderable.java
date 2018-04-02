package gameview.renderables.debug;

import Model.physics.collidable.Collidable;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.renderable.glut.SphereRenderable;

public class CollidableCornerRenderable extends SphereRenderable
{
    private Collidable subject;
    private Point3D corner;
    private float dx, dy, dz;

    public CollidableCornerRenderable(Collidable subject, int dx, int dy, int dz)
    {
        super(subject.getTerminus(), new Orientation3D(), 0.1f, 5, 5);
        this.subject = subject;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        update();
    }

    @Override
    public void update()
    {
        this.corner = new Point3D(subject.getOrigin());
        corner.translateForward(subject.getOrientation(), dz * subject.getSize().getLength());
        corner.translateLateral(subject.getOrientation(), dx * subject.getSize().getWidth());
        corner.translateVertical(subject.getOrientation(), dy * subject.getSize().getHeight());
    }

    @Override
    public float getX() { return corner.getX(); }

    @Override
    public float getY() { return corner.getY(); }

    @Override
    public float getZ() { return corner.getZ(); }
}
