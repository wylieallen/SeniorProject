package gameview;

import Model.GameModel;
import Model.Items.LootChest;
import Model.Map.Zones.Asteroid;
import Model.Pilot.Player;
import Model.Ship.Ship;
import Model.Ship.ShipBuilder.ShipBuilder;
import Model.Ship.ShipParts.Projectile.Projectile;
import Model.physics.Body;
import Model.physics.CollisionObserver;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.Rarity;
import com.jogamp.opengl.GLAutoDrawable;
import gameview.controlstate.PilotingControlstate;
import gameview.observers.spawn.SpawnObserver;
import gameview.renderables.*;
import gameview.renderables.debug.*;
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

public class GameUberstate extends Uberstate implements SpawnObserver, CollisionObserver
{
    private TransitionObserver transitionObserver;
    private PilotingControlstate controlstate;
    private GameModel gameModel;
    private Body<Ship> playerShip;

    public GameUberstate(TransitionObserver transitionObserver, Renderstate renderstate, Point centerPt, Player newPlayer, boolean initialized)
    {
        super(new Drawstate(), renderstate, new ClickableControlstate());

        this.transitionObserver = transitionObserver;

        //TODO make player & zoneID passed in by GameUberstate constructor
        int zoneID = 6;

        gameModel = new GameModel(newPlayer, zoneID);
        gameModel.run();
        playerShip = gameModel.getPlayerShip();
        gameModel.add((SpawnObserver) this);
        gameModel.add((CollisionObserver) this);

        super.setControlstate(this.controlstate = new PilotingControlstate(playerShip.get(), centerPt));

        bindKeyPress(KeyEvent.VK_W, () -> playerShip.get().setAccelerating(true));
        bindKeyPress(KeyEvent.VK_S, () -> playerShip.get().setDecelerating(true));
        bindKeyPress(KeyEvent.VK_SPACE, () -> playerShip.get().setBreaking(true));
        bindKeyPress(KeyEvent.VK_UP, () -> playerShip.get().setPitchingDown(true));
        bindKeyPress(KeyEvent.VK_DOWN, () -> playerShip.get().setPitchingUp(true));
        bindKeyPress(KeyEvent.VK_LEFT, () -> playerShip.get().setYawingLeft(true));
        bindKeyPress(KeyEvent.VK_RIGHT, () -> playerShip.get().setYawingRight(true));
        bindKeyPress(KeyEvent.VK_I, () -> System.out.println(playerShip.get().getInventory().toString()));
        bindKeyPress(KeyEvent.VK_SHIFT, () -> playerShip.get().activateSpecial());
        bindKeyPress(KeyEvent.VK_F, ()-> playerShip.get().setFiring2(true));

        bindKeyRelease(KeyEvent.VK_W, () -> playerShip.get().setAccelerating(false));
        bindKeyRelease(KeyEvent.VK_S, () -> playerShip.get().setDecelerating(false));
        bindKeyRelease(KeyEvent.VK_SPACE, () -> playerShip.get().setBreaking(false));
        bindKeyRelease(KeyEvent.VK_UP, () -> playerShip.get().setPitchingDown(false));
        bindKeyRelease(KeyEvent.VK_DOWN, () -> playerShip.get().setPitchingUp(false));
        bindKeyRelease(KeyEvent.VK_LEFT, () -> playerShip.get().setYawingLeft(false));
        bindKeyRelease(KeyEvent.VK_RIGHT, () -> playerShip.get().setYawingRight(false));
        bindKeyRelease(KeyEvent.VK_SHIFT, () -> playerShip.get().deactivateSpecial());
        bindKeyRelease(KeyEvent.VK_E, () -> playerShip.get().toggleShieldActivated());
        bindKeyRelease(KeyEvent.VK_F, ()-> playerShip.get().setFiring2(false));
        bindKeyRelease(KeyEvent.VK_ESCAPE, () -> transitionObserver.switchToOverworld());
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
        renderstate.add(new MinpointRenderable(playerShip));
        renderstate.add(new MaxpointRenderable(playerShip));

        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                for(int k = 0; k < 2; k++)
                {
                    renderstate.add(new CollidableCornerRenderable(playerShip, i, j, k));
                }
            }
        }

        renderstate.setCamera(new ThirdPersonCamera(playerRenderable, 16));
/*
        renderstate.add( new BufferedRenderable(new Point3D(0, -2, 0),
                new Dimension3D(2), new Orientation3D(), Model3DFactory.getCubeModel()));

        renderstate.add(new BufferedRenderable(new Point3D(3, 2, -2),
                new Dimension3D(2), new Orientation3D(), Model3DFactory.getPyramidModel()));

        renderstate.add(new ConeRenderable(new Point3D(1, 1, -8),
                0.5f, 2, new Orientation3D(180, 0, 0), 10));*/

        renderstate.add(new SphereRenderable(new Point3D(0, 0, 0),
                new Orientation3D(), 2, 10, 10));

        Random rng = new Random();

        /*
        for(int i = 0; i < 1000; i++)
        {
            renderstate.add(new SphereRenderable(new Point3D(rng.nextFloat() * 250 - 125, rng.nextFloat() * 250 - 125, rng.nextFloat() * 250 - 125 ),
                    new Orientation3D(), 1, 10, 10));
        }
        */
        gameModel.spawnEnemies();
        gameModel.spawnLootChests();
        gameModel.spawnAsteroids();
    }
    public void notifyAsteroidToShip(Body<Asteroid> asteroid, Body<Ship> ship){ }
    public void notifyShipToLoot(Body<Ship> ship, Body<LootChest> loot) {}
    public void notifyShipToShip(Body<Ship> a, Body<Ship> b)
    {
        getRenderstate().add(new ExplosionRenderable(a.getCenter().getMidpoint(b.getCenter()), 100));
    }
    public void notifyShipToProj(Body<Ship> ship, Body<Projectile> projectile)
    {
        if (ship.get() != projectile.get().getProjectileSource().getActiveShip()) {
            getRenderstate().add(new ExplosionRenderable(projectile.getCenter(), 150));
        }
    }
    public void notifyProjToProj(Body<Projectile> a, Body<Projectile> b)
    {
        getRenderstate().add(new ExplosionRenderable(a.getCenter().getMidpoint(b.getCenter()), 20));
    }

    public void notifyShipSpawn(Body<Ship> ship)
    {
        super.getRenderstate().add(new ShipRenderable(ship));
    }
    public void notifyProjSpawn(Body<Projectile> projectile) { super.getRenderstate().add(new ProjectileRenderable(projectile)); }
    public void notifyLootSpawn(Body<LootChest> lootChest) { super.getRenderstate().add(new LootRenderable(lootChest));}
    public void notifyAsteroidSpawn(Body<Asteroid> asteroid) {super.getRenderstate().add(new AsteroidRenderable(asteroid));}

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
        if(gameModel.gameIsOver())
        {
            transitionObserver.switchToOverworld();
        }
        super.display(drawable);
    }
}
