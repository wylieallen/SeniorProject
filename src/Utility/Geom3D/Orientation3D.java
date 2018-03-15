package Utility.Geom3D;

public class Orientation3D
{
    private float yaw, pitch, roll;

    public Orientation3D(float yaw, float pitch, float roll)
    {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public Orientation3D() { this(0, 0, 0); }

    public Orientation3D(Orientation3D original) { this(original.yaw, original.pitch, original.roll); }

    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
    public float getRoll() { return roll; }

    public void adjustYaw(float delta) { yaw = wrap(yaw + delta, 0, 360); }
    public void adjustPitch(float delta) { pitch = wrap(pitch + delta, 0, 360); }
    public void adjustRoll(float delta) { roll = wrap(roll + delta, 0, 360); }

    public void setYaw(float yaw) { this.yaw = yaw; }
    public void setPitch(float pitch) { this.pitch = pitch; }
    public void setRoll(float roll) { this.roll = roll; }

    private float wrap(float value, float min, float max)
    {
        if (value < min) value += max;
        else if (value > max) value -= max;
        return value;
    }
}
