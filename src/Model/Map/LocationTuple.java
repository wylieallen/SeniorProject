package Model.Map;

import Utility.Point3D;

public class LocationTuple<T> {

    private Point3D location;
    private T object;

    public T getObject(){
        return object;
    }

    public void setObject(T object){
        this.object = object;
    }

    public Point3D getLocation(){
        return location;
    }

    public void setLocation(Point3D location){
        this.location = location;
    }

}
