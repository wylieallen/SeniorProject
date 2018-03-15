package Model.Ship.ShipParts.Projectile;

import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;


public abstract class Projectile {

    private String name;
    private Vector3D trajectory;
    private Point3D startingPoint;
    private float range = 1000;
    private int speed;
    private int damage;
    private Pilot projectileSource;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Vector3D getTrajectory(){
        return trajectory;
    }

    public void setTrajectory(Vector3D trajectory){
        this.trajectory = trajectory;
    }

    public void setStartingPoint(Point3D startingPoint){
        this.startingPoint = startingPoint;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setProjectileSource(Pilot projectileSource){ this.projectileSource = projectileSource; }

    public Pilot getProjectileSource(){ return projectileSource; }

    public abstract Projectile cloneProjectile(Pilot pilot, Vector3D trajectory);

    public abstract Point3D move(Point3D currentPoint);

    public boolean isActive(Point3D currentPoint){
        if (startingPoint.distance(startingPoint, currentPoint) >= range){
            return false;
        }
        return true;
    }

}
