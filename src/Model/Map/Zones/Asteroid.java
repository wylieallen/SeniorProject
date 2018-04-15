package Model.Map.Zones;

import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;

import static Utility.Config.BASE_PROJECTILE_RANGE;

public class Asteroid {

    private Vector3D trajectory;
    private Point3D startingPoint;
    private float speed;
    private boolean expired = false;
    private float maxDist = 1000f;

    public Asteroid(float speed, Vector3D trajectory){
        this.speed = speed;
        this.trajectory = trajectory;
    }

    public float getSpeed(){
        return speed;
    }

    public Vector3D getTrajectory(){
        return trajectory;
    }

    public void update()
    {
        maxDist -= speed;
        if(maxDist <= 0) expired = true;
    }

    public boolean expired() { return expired; }
}
