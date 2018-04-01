package guiframework.gui3d.renderable;

import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import com.jogamp.opengl.GL4;
import guiframework.gui3d.model3d.Model3D;

public class BufferedRenderable extends AbstractRenderable
{
    private Model3D model;

    public BufferedRenderable(Point3D origin, Dimension3D size, Orientation3D orientation, Model3D model)
    {
        super(origin, size, orientation);
        this.model = model;
    }

    protected void do_render(GL4 gl, int mv_loc)
    {
        model.render(gl);
    }
}
