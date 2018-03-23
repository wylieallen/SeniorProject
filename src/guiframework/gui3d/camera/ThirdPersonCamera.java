package guiframework.gui3d.camera;

import guiframework.gui3d.Renderable.Renderable;

public class ThirdPersonCamera extends Camera
{
    private Renderable subject;
    private float trailDistance;

    public ThirdPersonCamera(Renderable subject, float trailDistance)
    {
        this.subject = subject;
        this.trailDistance = trailDistance;
        this.update();
    }

    @Override
    public void update()
    {
        super.setYaw(subject.getYaw());
        super.setPitch(subject.getPitch());
        super.setRoll(subject.getRoll());

        super.setX(subject.getX());
        super.setY(subject.getY());
        super.setZ(subject.getZ());

        super.translateForward(-trailDistance);
        super.translateVertical(-trailDistance/4);
    }
}
