package Model.physics.collidable;

import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;

public interface Collidable
{
    Point3D getOrigin();
    Point3D getTerminus();
    Point3D getCenter();
    Point3D getRear();
    Point3D getMinPoint();
    Point3D getMaxPoint();
    Dimension3D getSize();
    Orientation3D getOrientation();

    boolean collidesWith(Collidable collidable);
    void collide(Collidable collidable);

    default void update() {}
    boolean expired();
    void disable();

    void moveForward(float d);
    void moveLateral(float d);
    void moveVertical(float d);

    void adjustYaw(float d);
    void adjustPitch(float d);
    void adjustRoll(float d);
}
