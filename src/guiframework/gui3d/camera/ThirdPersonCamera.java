package guiframework.gui3d.camera;

import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.renderable.Renderable;

import java.util.LinkedList;
import java.util.Queue;

public class ThirdPersonCamera extends Camera
{
    private Renderable subject;
    private float trailDistance;
    private Queue<Point3D> destinations;
    private Queue<Orientation3D> orientations;

    public ThirdPersonCamera(Renderable subject, float trailDistance)
    {
        this.subject = subject;
        this.trailDistance = trailDistance;
        this.destinations = new LinkedList<>();
        this.orientations = new LinkedList<>();
        for(int i = 0; i < 2; i++)
        {
            destinations.add(new Point3D(subject.getX(), subject.getY(), subject.getZ()));
            orientations.add(new Orientation3D(subject.getYaw(), subject.getPitch(), subject.getRoll()));
        }
        this.update();
    }

    @Override
    public void update()
    {
        destinations.add(new Point3D(subject.getX(), subject.getY(), subject.getZ()));
        orientations.add(new Orientation3D(subject.getYaw(), subject.getPitch(), subject.getRoll()));

        Point3D subjOrigin = destinations.remove();
        Orientation3D subjDirection = orientations.remove();

        super.setYaw(subjDirection.getYaw());
        super.setPitch(subjDirection.getPitch());
        super.setRoll(subjDirection.getRoll());

        super.setX(subjOrigin.getX());
        super.setY(subjOrigin.getY());
        super.setZ(subjOrigin.getZ());

        super.translateForward(-trailDistance);
        super.translateLateral(subject.getWidth()/2);
        super.translateVertical(-trailDistance/4);
    }
}
