package guiframework.gui3d.camera;

import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;

public class Camera
{
    private Point3D location;
    private Orientation3D orientation;

    public Camera(Point3D location, Orientation3D orientation)
    {
        this.location = location;
        this.orientation = orientation;
    }

    public Camera() { this(new Point3D(), new Orientation3D()); }

    public Point3D getLocation() { return location; }
    public Orientation3D getOrientation() { return orientation; }

    public void translate(float dx, float dy, float dz)
    {
        location.translateX(dx);
        location.translateY(dy);
        location.translateZ(dz);
    }

    public void rotate(float dyaw, float dpitch, float droll)
    {
        orientation.adjustYaw(dyaw);
        orientation.adjustPitch(dpitch);
        orientation.adjustRoll(droll);
    }

    public float getX() { return location.getX(); }
    public float getY() { return location.getY(); }
    public float getZ() { return location.getZ(); }

    public float getYaw() { return orientation.getYaw(); }
    public float getPitch() { return orientation.getPitch(); }
    public float getRoll() { return orientation.getRoll(); }

    public void translateForward(float delta) { location.translateForward(orientation, delta); }
    public void translateLateral(float delta) { location.translateLateral(orientation, delta); }
    public void translateVertical(float delta) { location.translateVertical(orientation, delta); }

    public void setPitch(float pitch) { orientation.setPitch(pitch); }
    public void setYaw(float yaw) { orientation.setYaw(yaw); }
    public void setRoll(float roll) { orientation.setRoll(roll); }

    public void setX(float x) { location.setX(x); }
    public void setY(float y) { location.setY(y); }
    public void setZ(float z) { location.setZ(z); }

    public void update() {}
}
