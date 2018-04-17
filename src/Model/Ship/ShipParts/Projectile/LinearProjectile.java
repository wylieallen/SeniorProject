package Model.Ship.ShipParts.Projectile;

import Model.Pilot.Pilot;
import Utility.Geom3D.Point3D;
import Utility.Geom3D.Vector3D;
import guiframework.gui2d.ImageFactory;

import java.awt.image.BufferedImage;

import static Utility.Config.FRAMERATE;

public class LinearProjectile extends Projectile{

    public LinearProjectile(Pilot pilot, Vector3D trajectory, float speed, int damage){
        super("Linear", 3.0f, 12, trajectory, pilot);
    }

    @Override
    public Projectile cloneProjectile(Pilot pilot, Vector3D trajectory) {

        return new LinearProjectile(pilot, trajectory, super.getSpeed(), super.getDamage());
    }

    @Override
    public void move(Point3D curPosition) {
/*
        Vector3D curTrajectory = super.getTrajectory();
        float curSpeed = super.getSpeed();
*/

    }

    //todo: add switch statement for different rarity images
    public BufferedImage getImage() {return ImageFactory.getCommonLinearWeaponImage();}
}
