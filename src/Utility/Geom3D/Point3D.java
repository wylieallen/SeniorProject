package Utility.Geom3D;

public class Point3D {
    private float x;
    private float y;
    private float z;

    public float getX(){ return x; }
    public float getY(){ return y; }
    public float getZ(){ return z; }

    public Point3D() { this(0, 0, 0); }

    public Point3D(Point3D original) { this(original.x, original.y, original.z); }

    public Point3D(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float distance(Point3D pointA, Point3D pointB){
        float xDistance = (float) Math.pow(pointB.getX() - pointA.getX(), 2);
        float yDistance = (float) Math.pow(pointB.getY() - pointA.getY(), 2);
        float zDistance = (float) Math.pow(pointB.getZ() - pointA.getZ(), 2);

        return (float) Math.sqrt(xDistance + yDistance + zDistance);
    }

    public boolean equals(Point3D pointA, Point3D pointB){
        if (pointA.getX() == pointB.getX() && pointA.getY() == pointB.getY() && pointA.getZ() == pointB.getZ())
        {
            return true;
        }
        return false;
    }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }

    public void translateX(float dx) { this.x += dx; }
    public void translateY(float dy) { this.y += dy; }
    public void translateZ(float dz) { this.z += dz; }

    public void translateForward(Orientation3D orientation, float d)
    {
        float yawRads = orientation.getYaw()/180.0f * 3.1415926535f;
        float pitchRads = -orientation.getPitch()/180.0f * 3.1415926535f;

        translateX(d * (float) (Math.cos(pitchRads) * Math.sin(yawRads)));
        translateY(d * (float) Math.sin(pitchRads));
        translateZ(d * (float) (Math.cos(pitchRads) * Math.cos(yawRads)));
    }

    public void translateLateral(Orientation3D orientation, float d)
    {
        float yawRads = orientation.getYaw()/180.0f * 3.1415926535f;

        translateX(d * (float) Math.cos(yawRads));
        translateZ(d * (float) Math.sin(yawRads));
    }

    public void translateVertical(Orientation3D orientation, float d)
    {
        float yawRads = orientation.getYaw()/180.0f * 3.1415926535f;
        float pitchRads = -(orientation.getPitch() + 90.0f) /180.0f * 3.1415926535f;

        translateX(d * (float) (Math.sin(yawRads) * Math.cos(pitchRads)));
        translateY(d * (float) Math.sin(pitchRads));
        translateZ(-d * (float) (Math.cos(pitchRads) * Math.cos(yawRads)));

    }

    public Point3D getMidpoint(Point3D other)
    {
        float x = (this.x + other.x) / 2;
        float y = (this.y + other.y) / 2;
        float z = (this.z + other.z) / 2;

        return new Point3D(x, y, z);
    }

    public String toString(){

        String coordinates = "X: " + x + " Y: " + y + " Z: " + z;
        return coordinates;
    }

}
