package gameview.renderables;

import Model.Items.LootChest;
import Model.Ship.Ship;
import Model.physics.Body;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.model3d.Model3D;
import guiframework.gui3d.model3d.Model3DFactory;
import guiframework.gui3d.renderable.BufferedRenderable;

public class LootRenderable extends BufferedRenderable {

    private Body<LootChest> loot;
    private Point3D location;

    public LootRenderable(Body<LootChest> loot)
    {
        super(loot.getOrigin(), new Dimension3D(loot.getSize()), loot.getOrientation(), Model3DFactory.getCubeModel());
        this.loot = loot;
        this.update();
    }

    @Override
    public void update() { location = loot.getOrigin(); }

    @Override
    public float getX() { return location.getX(); }

    @Override
    public float getY() { return location.getY(); }

    @Override
    public float getZ() { return location.getZ(); }

    @Override
    public float getYaw() { return loot.getOrientation().getYaw(); }

    @Override
    public float getPitch() { return loot.getOrientation().getPitch(); }

    @Override
    public float getRoll() { return loot.getOrientation().getRoll(); }

    @Override
    public boolean expired()
    {
        return loot.get().expired();
    }
}
