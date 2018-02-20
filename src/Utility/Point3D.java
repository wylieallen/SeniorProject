package Utility;

public class Point3D {
    private float x;
    private float y;
    private float z;

    public float getX(){ return x; }
    public float getY(){ return y; }
    public float getZ(){ return z; }

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

}
