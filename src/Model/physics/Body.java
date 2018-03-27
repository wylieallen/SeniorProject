package Model.physics;

import Model.physics.collidable.Collidable;
import Utility.Geom3D.Point3D;

public class Body<T>
{
    private final T associate;
    private final Collidable collidable;

    public Body(Collidable collidable, T associate)
    {
        this.associate = associate;
        this.collidable = collidable;
    }

    public Collidable getCollidable() { return collidable; }

    public T get() { return associate; }

    public Point3D getCenter(){
        return collidable.getCenter();
    }
}
