package gameview.renderables;

import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import guiframework.gui3d.model3d.Model3DFactory;
import guiframework.gui3d.renderable.BufferedRenderable;
import guiframework.gui3d.renderable.CompositeRenderable;

import java.util.Random;

public class ExplosionRenderable extends CompositeRenderable
{
    public ExplosionRenderable(Point3D origin, int particleCount)
    {
        super(origin, new Dimension3D(), new Orientation3D());
        Random rand = new Random();
        for(int i = 0; i < particleCount; i++)
        {
            super.add(new ParticleRenderable(new Point3D(), new Orientation3D(rand.nextFloat() * 360, rand.nextFloat()*360, rand.nextFloat() * 360),
                    rand.nextFloat() * 0.2f + 0.2f, rand.nextFloat() * 5.0f));
        }
    }

    private class ParticleRenderable extends BufferedRenderable
    {
        private float fadeDist, velocity;

        public ParticleRenderable(Point3D origin, Orientation3D orientation, float velocity, float fadeDist)
        {
            super(origin, new Dimension3D(0.2f), orientation, Model3DFactory.getSparkModel());
            this.fadeDist = fadeDist;
            this.velocity = velocity;
        }

        @Override
        public void update()
        {
            super.translateForward(velocity);
            fadeDist -= velocity;
        }

        @Override
        public boolean expired()
        {
             return fadeDist <= 0;
        }
    }
}
