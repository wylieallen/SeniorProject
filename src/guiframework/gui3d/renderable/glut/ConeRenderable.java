package guiframework.gui3d.renderable.glut;

import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.gl2.GLUT;
import guiframework.gui3d.renderable.AbstractRenderable;

public class ConeRenderable extends AbstractRenderable
{
    private static GLUT glut = new GLUT();

    private int slices;

    public ConeRenderable(Point3D origin, float baseRadius, float length, Orientation3D orientation, int slices)
    {
        super(origin, new Dimension3D(baseRadius * 2, baseRadius * 2, length), orientation);
        this.slices = slices;
    }

    @Override
    protected void do_render(GL4 gl, int mv_loc)
    {
        glut.glutSolidCone(getWidth()/2, getLength(), slices, slices);
    }
}
