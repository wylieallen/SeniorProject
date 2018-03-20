package Utility.Geom3D;

import java.awt.*;

public class Vector3D {
        private float i;
        private float j;
        private float k;

        public Vector3D(float i, float j, float k){
                this.i = i;
                this.j = j;
                this.k = k;
        }

        public Vector3D(Point3D start, Point3D destination){
            this.i = destination.getX() - start.getX();
            this.j = destination.getY() - start.getY();
            this.k = destination.getZ() - start.getZ();
        }

        public void makeUnitVector(){
            float norm = (float) Math.sqrt( (float) Math.pow(i,2) + (float) Math.pow(j,2) + (float) Math.pow(k, 2));
            i = i/norm;
            j = j/norm;
            k = k/norm;
        }

        public float getI(){ return i; }
        public float getJ(){ return j; }
        public float getK(){ return k; }
}
