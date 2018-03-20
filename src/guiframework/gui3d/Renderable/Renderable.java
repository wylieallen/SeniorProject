package guiframework.gui3d.Renderable;

import com.jogamp.opengl.GL4;

public interface Renderable
{
    float getX();
    float getY();
    float getZ();

    float getYaw();
    float getPitch();
    float getRoll();

    float getWidth();
    float getHeight();
    float getLength();

    void relocate(float x, float y, float z);

    void translate(float dx, float dy, float dz);

    void translateForward(float delta);
    void translateLateral(float delta);
    void translateVertical(float delta);

    void rotate(float dyaw, float dpitch, float droll);

    default void render(GL4 gl, int mv_loc)
    {
        renderAt(gl, getX(), getY(), getZ(), mv_loc);
    }

    void renderAt(GL4 gl, float x, float y, float z, int mv_loc);

    default boolean expired() { return false; }
    default void update() {}
}
