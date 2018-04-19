package Model.Map.Zones;

import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

import static Utility.Config.BASE_PROJECTILE_RANGE;
import static Utility.Config.BATTLEZONE_SIZE;

public class Asteroid {

    private Vector3D trajectory;
    private Point3D startingPoint;
    private float speed;
    private boolean expired = false;
    private float maxDist = BATTLEZONE_SIZE*2;

    public Asteroid(float speed, Vector3D trajectory, float maxDist){
        this.speed = speed;
        this.trajectory = trajectory;
        this.maxDist = maxDist;
    }

    public float getSpeed(){
        return speed;
    }

    public Vector3D getTrajectory(){
        return trajectory;
    }

    public void setTrajectory(Vector3D trajectory) {
        this.trajectory = trajectory;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void update()
    {
        maxDist -= speed;
        if(maxDist <= 0) expired = true;
    }

    public boolean expired() { return expired; }
}
