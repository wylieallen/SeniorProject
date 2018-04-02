package Model.physics;

import Model.physics.collidable.BoundingBoxCollidable;
import Model.physics.collidable.Collidable;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;

public class Body<T> extends BoundingBoxCollidable
{
    private final T associate;

    public Body(Point3D origin, Dimension3D size, Orientation3D orientation, T associate)
    {
        super(origin, size, orientation);
        this.associate = associate;
    }

    public T get() { return associate; }
}
