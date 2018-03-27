package gameview;

import Model.GameModel;
import Model.Ship.Ship;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.Ship.ShipParts.ShipHull;
import Model.physics.Body;
import Model.physics.collidable.BoundingBoxCollidable;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.Rarity;
import com.jogamp.opengl.GLAutoDrawable;
import gameview.controlstate.PilotingControlstate;
import gameview.observers.spawn.SpawnObserver;
import gameview.renderables.ProjectileRenderable;
import gameview.renderables.ShipRenderable;
import guiframework.Uberstate;
import guiframework.control.ClickableControlstate;
import guiframework.gui2d.Drawstate;
import guiframework.gui3d.Renderstate;
import guiframework.gui3d.camera.ThirdPersonCamera;
import guiframework.gui3d.model3d.Model3DFactory;
import guiframework.gui3d.renderable.BufferedRenderable;
import guiframework.gui3d.renderable.glut.ConeRenderable;
import guiframework.gui3d.renderable.glut.SphereRenderable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameUberstate extends Uberstate implements SpawnObserver
{
    private PilotingControlstate controlstate;
    private GameModel gameModel;
    private Body<Ship> playerShip;

    public GameUberstate(Renderstate renderstate, Point centerPt)
    {
        super(new Drawstate(), renderstate, new ClickableControlstate());
        gameModel = new GameModel();
        playerShip = gameModel.getPlayerShip();
        gameModel.add(this);

        super.setControlstate(this.controlstate = new PilotingControlstate(playerShip.get(), centerPt));

        bindKeyPress(KeyEvent.VK_W, () -> playerShip.get().setAccelerating(true));
        bindKeyPress(KeyEvent.VK_S, () -> playerShip.get().setDecelerating(true));
        bindKeyPress(KeyEvent.VK_UP, () -> playerShip.get().setPitchingDown(true));
        bindKeyPress(KeyEvent.VK_DOWN, () -> playerShip.get().setPitchingUp(true));
        bindKeyPress(KeyEvent.VK_LEFT, () -> playerShip.get().setYawingLeft(true));
        bindKeyPress(KeyEvent.VK_RIGHT, () -> playerShip.get().setYawingRight(true));

        bindKeyRelease(KeyEvent.VK_W, () -> playerShip.get().setAccelerating(false));
        bindKeyRelease(KeyEvent.VK_S, () -> playerShip.get().setDecelerating(false));
        bindKeyRelease(KeyEvent.VK_UP, () -> playerShip.get().setPitchingDown(false));
        bindKeyRelease(KeyEvent.VK_DOWN, () -> playerShip.get().setPitchingUp(false));
        bindKeyRelease(KeyEvent.VK_LEFT, () -> playerShip.get().setYawingLeft(false));
        bindKeyRelease(KeyEvent.VK_RIGHT, () -> playerShip.get().setYawingRight(false));
    }

    @Override
    public void init(GLAutoDrawable drawable)
    {
        super.init(drawable);

        // Renderables need to be initialized AFTER the base Uberstate has been!
        // Otherwise models won't be loaded in the Model3DFactory yet!

        Renderstate renderstate = super.getRenderstate();

        ShipRenderable playerRenderable = new ShipRenderable(playerShip);

        renderstate.add(playerRenderable);
        renderstate.setCamera(new ThirdPersonCamera(playerRenderable, 4));

        renderstate.add( new BufferedRenderable(new Point3D(0, -2, 0),
                new Dimension3D(2), new Orientation3D(), Model3DFactory.getCubeModel()));

        renderstate.add(new BufferedRenderable(new Point3D(3, 2, -2),
                new Dimension3D(2), new Orientation3D(), Model3DFactory.getPyramidModel()));

        renderstate.add(new ConeRenderable(new Point3D(1, 1, -8),
                0.5f, 2, new Orientation3D(180, 0, 0), 10));

        renderstate.add(new SphereRenderable(new Point3D(2, 4, -4),
                new Orientation3D(), 2, 10, 10));

        Random rng = new Random();

        for(int i = 0; i < 1000; i++)
        {
            renderstate.add(new SphereRenderable(new Point3D(rng.nextFloat() * 250 - 125, rng.nextFloat() * 250 - 125, rng.nextFloat() * 250 - 125 ),
                    new Orientation3D(), 1, 10, 10));
        }

        gameModel.spawnShip( new Body<>(new BoundingBoxCollidable(new Point3D(10, 10, 10), new Dimension3D(0.2f, 0.2f, 1.0f)),
                new Ship(null, new ShipHull(10, 10, 10, Rarity.COMMON))));
    }

    public void notifyShipSpawn(Body<Ship> ship)
    {
        super.getRenderstate().add(new ShipRenderable(ship));
    }
    public void notifyProjSpawn(Body<Projectile> projectile) { super.getRenderstate().add(new ProjectileRenderable(projectile)); }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        super.reshape(drawable, x, y, width, height);
        controlstate.setCenterPt(new Point(width / 2, height / 2));
    }

    @Override
    public void display(GLAutoDrawable drawable)
    {
        gameModel.update();
        super.display(drawable);
    }
}
