package Model.physics.collidable;

import Utility.Geom3D.Point3D;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;

import static Utility.Config.FRAMERATE;

public class BoundingBoxCollidable implements Collidable
{
    private Point3D origin, minPoint, maxPoint;
    private Dimension3D size;
    private Orientation3D orientation;

    public BoundingBoxCollidable(Point3D origin, Dimension3D size)
    {
        this(origin, size, new Orientation3D());
    }

    public BoundingBoxCollidable(Point3D origin, Dimension3D size, Orientation3D orientation)
    {
        this.origin = origin;
        this.size = size;
        this.orientation = orientation;
        recalculateBounds();
    }

    public void move(Point3D newPosition){

        origin.translateForward(newPosition);
        minPoint.translateForward(newPosition);
        maxPoint.translateForward(newPosition);
        recalculateBounds();
    }

    public void moveForward(float d)
    {
        origin.translateForward(orientation, d);
        minPoint.translateForward(orientation, d);
        maxPoint.translateForward(orientation, d);
    }

    public void moveLateral(float d)
    {
        origin.translateLateral(orientation, d);
        minPoint.translateLateral(orientation, d);
        maxPoint.translateLateral(orientation, d);
    }

    public void moveVertical(float d)
    {
        origin.translateVertical(orientation, d);
        minPoint.translateVertical(orientation, d);
        maxPoint.translateVertical(orientation, d);
    }

    public void adjustYaw(float d)
    {
        orientation.adjustYaw(d);
        recalculateBounds();
    }

    public void adjustPitch(float d)
    {
        orientation.adjustPitch(d);
        recalculateBounds();
    }

    public void adjustRoll(float d)
    {
        orientation.adjustRoll(d);
        recalculateBounds();
    }

    public void setOrientation(float pitch, float yaw){
        orientation.setPitch(pitch);
        orientation.setYaw(yaw);
        recalculateBounds();
    }

    public Point3D getOrigin() { return origin; }
    public Point3D getMinPoint() { return minPoint; }
    public Point3D getMaxPoint() { return maxPoint; }
    public Dimension3D getSize() { return size; }
    public Orientation3D getOrientation() { return orientation; }

    public Point3D getCenter() { return minPoint.getMidpoint(maxPoint); }
    public Point3D getRear()
    {
        Point3D rear = new Point3D(origin);
        rear.translateVertical(orientation, -size.getHeight() / 2);
        rear.translateLateral(orientation, size.getWidth() / 2);
        return rear;
    }

    public Point3D getTerminus()
    {
        Point3D terminus = new Point3D(origin);
        // todo: maybe just construct a new "towards terminus" orientation and do one translate
        terminus.translateForward(orientation, size.getLength());
        terminus.translateLateral(orientation, size.getWidth());
        terminus.translateVertical(orientation, size.getHeight());
        return terminus;
    }

    public void recalculateBounds()
    {
        float minX, minY, minZ, maxX, maxY, maxZ;
        minX = maxX = origin.getX();
        minY = maxY = origin.getY();
        minZ = maxZ = origin.getZ();

        for(float i = 0; i <= 1; i += 1)
        {
            for(float j = 0; j <= 1; j += 1)
            {
                for(float k = 0; k <= 1; k += 1)
                {
                    Point3D vertex = new Point3D(origin);
                    vertex.translateForward(orientation, k * size.getLength());
                    vertex.translateLateral(orientation, i * size.getWidth());
                    vertex.translateVertical(orientation, j * size.getHeight());

                    if(vertex.getX() < minX) minX = vertex.getX();
                    else if(vertex.getX() > maxX) maxX = vertex.getX();

                    if(vertex.getY() < minY) minY = vertex.getY();
                    else if(vertex.getY() > maxY) maxY = vertex.getY();

                    if(vertex.getZ() < minZ) minZ = vertex.getZ();
                    else if(vertex.getZ() > maxZ) maxZ = vertex.getZ();
                }
            }
        }

        // Why are maxZ and minZ switched? It is a mystery
        minPoint = new Point3D(minX, minY, maxZ);
        maxPoint = new Point3D(maxX, maxY, minZ);
    }

    public boolean collidesWith(Collidable collidable)
    {
        Point3D min1 = this.getMinPoint();//getOrigin();
        Point3D max1 = this.getMaxPoint();//getTerminus();
        Point3D min2 = collidable.getMinPoint();//getOrigin();
        Point3D max2 = collidable.getMaxPoint();//getTerminus();

        if(min1.getY() > max2.getY() || min2.getY() > max1.getY())
        {
            //System.out.println("Vertically Disjoint");
            return false;
        }

        else if(min1.getX() > max2.getX() || min2.getX() > max1.getX())
        {
            //System.out.println("Horizontally Disjoint");
            return false;
        }

        else if(min1.getZ() < max2.getZ() || min2.getZ() < max1.getZ())
        {
            //System.out.println("Longitudinally Disjoint");
            return false;
        }

        else
        {
            //System.out.println("CollisionChecker detected!");
            return true;
        }
    }

}
