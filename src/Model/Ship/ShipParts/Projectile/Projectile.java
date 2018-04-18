package Model.Ship.ShipParts.Projectile;

import Model.Pilot.Enemy;
import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

import static Utility.Config.BASE_PROJECTILE_RANGE;


public abstract class Projectile {

    public static final Projectile NULL = new Projectile("NULL", 1, 0, new Vector3D(0, 0, 0), Pilot.NULL)
    {
        public void move(Point3D point3D) {};

        @Override
        public Projectile cloneProjectile(Pilot pilot, Vector3D trajectory)
        {
            return this;
        }
    };

    private String name;
    private Vector3D trajectory;
    private Point3D startingPoint;
    private float range = BASE_PROJECTILE_RANGE;
    private float speed;
    private int damage;
    private Pilot projectileSource;
    private boolean expired = false;
    private float maxDist = BASE_PROJECTILE_RANGE;

    protected Projectile(String name, float speed, int damage, Vector3D trajectory, Pilot projectileSource)
    {
        this.name = name;
        this.speed = speed;
        this.damage = damage;
        this.trajectory = trajectory;
        this.projectileSource = projectileSource;
    }

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

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(float speed){
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

    public abstract void move(Point3D currentPoint);

    public boolean isActive(Point3D currentPoint){
        if (startingPoint.distance(startingPoint, currentPoint) >= range){
            return false;
        }
        return true;
    }

    public void disable() { expired = true; }

    public boolean expired() { return expired; }
    public void update()
    {
        maxDist -= speed;
        if(maxDist <= 0) expired = true;
        //System.out.println(toString() + " " + expired);
    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getNotFoundImage();}
}
