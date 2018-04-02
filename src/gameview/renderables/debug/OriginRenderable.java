package gameview.renderables.debug;

import Model.physics.collidable.Collidable;
import Utility.Geom3D.Orientation3D;
import guiframework.gui3d.renderable.glut.SphereRenderable;

public class OriginRenderable extends SphereRenderable
{
    private Collidable subject;

    public OriginRenderable(Collidable subject)
    {
        super(subject.getOrigin(), new Orientation3D(), 0.1f, 5, 5);
        this.subject = subject;
    }
}
