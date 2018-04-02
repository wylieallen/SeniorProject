package guiframework.gui3d.renderable;

import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import com.jogamp.opengl.GL4;

import java.util.HashSet;
import java.util.Set;

public class CompositeRenderable extends AbstractRenderable
{
    private Set<Renderable> components;

    public CompositeRenderable(Point3D origin, Dimension3D size, Orientation3D orientation)
    {
        super(origin, size, orientation);
        this.components = new HashSet<>();
    }

    @Override
    public boolean expired()
    {
        return components.size() < 1;
    }

    @Override
    public void update()
    {
        Set<Renderable> expireds = new HashSet<>();
        for(Renderable r : components)
        {
            r.update();
            if(r.expired()) expireds.add(r);
        }
        components.removeAll(expireds);
    }

    public void add(Renderable r) { components.add(r); }
    public void remove(Renderable r) { components.remove(r); }

    public void do_render(GL4 gl, int mv_loc)
    {
        for(Renderable r : components)
            r.render(gl, mv_loc);
    }
}
