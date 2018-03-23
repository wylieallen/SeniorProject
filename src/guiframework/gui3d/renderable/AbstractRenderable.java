package guiframework.gui3d.renderable;

import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import com.jogamp.opengl.GL4;
import graphicslib3D.Matrix3D;
import guiframework.gui3d.util.MatrixStack;

public abstract class AbstractRenderable implements Renderable
{
    private Point3D origin;
    private Dimension3D size;
    private Orientation3D orientation;

    public AbstractRenderable(Point3D origin, Dimension3D size, Orientation3D orientation)
    {
        this.origin = origin;
        this.size = size;
        this.orientation = orientation;
    }

    public AbstractRenderable(Point3D origin, Dimension3D size) { this(origin, size, new Orientation3D()); }

    public float getX() { return origin.getX(); }
    public float getY() { return origin.getY(); }
    public float getZ() { return origin.getZ(); }

    public float getWidth() { return size.getWidth(); }
    public float getHeight() { return size.getHeight(); }
    public float getLength() { return size.getLength(); }

    public float getYaw() { return orientation.getYaw(); }
    public float getPitch() { return orientation.getPitch(); }
    public float getRoll() { return orientation.getRoll(); }

    public void relocate(float x, float y, float z) { this.origin = new Point3D(x, y, z); }

    public void translateForward(float delta) { origin.translateForward(orientation, delta); }
    public void translateLateral(float delta) { origin.translateLateral(orientation, delta); }
    public void translateVertical(float delta) { origin.translateVertical(orientation, delta); }

    public void translate(float dx, float dy, float dz)
    {
        origin.translateX(dx);
        origin.translateY(dy);
        origin.translateZ(dz);
    }

    public void rotate(float dyaw, float dpitch, float droll)
    {
        orientation.adjustYaw(dyaw);
        orientation.adjustPitch(dpitch);
        orientation.adjustRoll(droll);
    }

    public void renderAt(GL4 gl, float x, float y, float z, int mv_loc)
    {
        MatrixStack.push();

        Matrix3D modelMat = MatrixStack.peek();
        modelMat.translate(x, y, z);
        modelMat.rotate(getPitch(), 180 - getYaw(), getRoll());

        gl.glUniformMatrix4fv(mv_loc, 1, false, MatrixStack.peek().getFloatValues(), 0);

        do_render(gl, mv_loc);

        MatrixStack.pop();
    }

    protected abstract void do_render(GL4 gl, int mv_loc);
}
