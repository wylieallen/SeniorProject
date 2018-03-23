package guiframework.gui3d.renderable.glut;

import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.gl2.GLUT;
import guiframework.gui3d.renderable.AbstractRenderable;

public class SphereRenderable extends AbstractRenderable
{
    private static GLUT glut = new GLUT();

    private int vslices, hslices;
    private float radius;

    public SphereRenderable(Point3D origin, Orientation3D orientation, float radius, int vslices, int hslices)
    {
        super(origin, new Dimension3D(radius * 2), orientation);
        this.radius = radius;
        this.vslices = vslices;
        this.hslices = hslices;
    }

    @Override
    protected void do_render(GL4 gl, int mv_loc)
    {
        glut.glutSolidSphere(radius, vslices, hslices);
    }
}
