package guiframework.gui3d;

import Model.Pilot.Pilot;
import Model.Ship.Ship;
import Model.Ship.ShipParts.ShipHull;
import Utility.Geom3D.Dimension3D;
import Utility.Geom3D.Orientation3D;
import Utility.Geom3D.Point3D;
import Utility.Rarity;
import com.jogamp.opengl.*;
import gameview.ShipRenderable;
import graphicslib3D.Matrix3D;
import guiframework.gui3d.Renderable.BufferedRenderable;
import guiframework.gui3d.Renderable.Renderable;
import guiframework.gui3d.Renderable.glut.ConeRenderable;
import guiframework.gui3d.Renderable.glut.SphereRenderable;
import guiframework.gui3d.camera.Camera;
import guiframework.gui3d.camera.ThirdPersonCamera;
import guiframework.gui3d.model3d.Model3D;
import guiframework.gui3d.util.MatrixStack;
import guiframework.gui3d.util.ShaderParser;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

public class Renderstate implements GLEventListener
{
    private Point centerPt;
    private int width, height;

    private int renderingProgram;
    private int vao[];
    private int vbo[];

    private Camera camera;

    private Ship playerShip;

    private boolean pivotEnabled = false;

    private Matrix3D projMat;

    private Renderable cube, pyramid;

    private Set<Renderable> renderables;

    public Renderstate(int width, int height)
    {
        this.camera = new Camera();

        this.width = width;
        this.height = height;

        this.vao = new int[1];
        this.vbo = new int[2];
        this.width = width;
        this.height = height;

        this.playerShip = new Ship(null, new ShipHull(10, 10, 10, Rarity.COMMON));
        playerShip.moveForward(-5);

        centerPt = new Point(width / 2, height / 2);

        this.renderables = new HashSet<>();

        // instantiate collisionmanager etc

    }

    @Override
    public void init(GLAutoDrawable drawable)
    {
        Random rng = new Random();

        GL4 gl = (GL4) GLContext.getCurrentGL();
        renderingProgram = createShaderProgram();

        setupVertices();

        float aspect = (float) width / (float) height;
        projMat = makePerspectiveMatrix(60.0f, aspect, 0.1f, 1000.0f);

        gl.glEnable(GL4.GL_DEPTH_TEST);
        gl.glDepthFunc(GL4.GL_LEQUAL);

        for(int i = 0; i < 1000; i++)
        {
            renderables.add(new SphereRenderable(new Point3D(rng.nextFloat() * 250 - 125, rng.nextFloat() * 250 - 125, rng.nextFloat() * 250 - 125 ),
                    new Orientation3D(), 1, 10, 10));
        }
    }

    private Matrix3D makePerspectiveMatrix(float fovy, float aspect, float n, float f)
    {
        float q = 1.0f / ((float) Math.tan(Math.toRadians(0.5f * fovy)));
        float A = q / aspect;
        float B = (n + f) / ( n - f );
        float C = (2.0f * n * f) / (n - f);

        Matrix3D r = new Matrix3D();

        r.setElementAt(0, 0, A);
        r.setElementAt(1, 1, q);
        r.setElementAt(2, 2, B);
        r.setElementAt(3, 2, -1.0f);
        r.setElementAt(2, 3, C);
        r.setElementAt(3, 3, 0.0f);

        return r;
    }

    private int createShaderProgram()
    {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        String vShaderSource[] = ShaderParser.readShaderSource("v.shader");

        int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
        gl.glShaderSource(vShader, vShaderSource.length, vShaderSource, null, 0);
        gl.glCompileShader(vShader);

        String fShaderSource[] = ShaderParser.readShaderSource("f.shader");

        int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
        gl.glShaderSource(fShader, fShaderSource.length, fShaderSource, null, 0);
        gl.glCompileShader(fShader);

        int vfprogram = gl.glCreateProgram();
        gl.glAttachShader(vfprogram, vShader);
        gl.glAttachShader(vfprogram, fShader);
        gl.glLinkProgram(vfprogram);

        gl.glDeleteShader(vShader);
        gl.glDeleteShader(fShader);

        return vfprogram;
    }

    private void setupVertices()
    {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        float[ ] cube_vertices =
                {
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, -1.0f,

                        1.0f, -1.0f, -1.0f,
                        1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,

                        1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,

                        1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, -1.0f,

                        1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,

                        -1.0f, -1.0f, 1.0f,
                        -1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f,

                        -1.0f, -1.0f, 1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,

                        -1.0f, -1.0f, -1.0f,
                        -1.0f, 1.0f, -1.0f,
                        -1.0f, 1.0f, 1.0f,

                        -1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, 1.0f,
                        1.0f, -1.0f, -1.0f,

                        1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, -1.0f,
                        -1.0f, -1.0f, 1.0f,

                        -1.0f, 1.0f, -1.0f,
                        1.0f, 1.0f, -1.0f,
                        1.0f, 1.0f, 1.0f,

                        1.0f, 1.0f, 1.0f,
                        -1.0f, 1.0f, 1.0f,
                        -1.0f, 1.0f, -1.0f
                };

        float[] pyr_vertices = {
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                0.0f, 1.0f, 0.0f,

                1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, -1.0f,
                0.0f, 1.0f, 0.0f,

                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                0.0f, 1.0f, 0.0f,

                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, 1.0f,
                0.0f, 1.0f, 0.0f,

                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,

                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f
        };

        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);

        gl.glGenBuffers(vbo.length, vbo, 0);

        Model3D cubeModel = new Model3D(gl, vbo[0], cube_vertices);
        Model3D pyrModel = new Model3D(gl, vbo[1], pyr_vertices);

        this.cube = new BufferedRenderable(new Point3D(0, -2, 0),
                new Dimension3D(2), new Orientation3D(), cubeModel);

        this.pyramid = new BufferedRenderable(new Point3D(3, 2, -2),
                new Dimension3D(2), new Orientation3D(), pyrModel);

        renderables.add(cube);
        renderables.add(pyramid);

        ConeRenderable cone = new ConeRenderable(new Point3D(1, 1, -8), 0.5f, 2, new Orientation3D(180, 0, 0), 10);

        ShipRenderable shipRenderable = new ShipRenderable(playerShip);

        renderables.add(new SphereRenderable(new Point3D(2, 4, -4), new Orientation3D(), 2, 10, 10));
        renderables.add(cone);
        renderables.add(shipRenderable);

        camera = new ThirdPersonCamera(shipRenderable, 4);
    }


    @Override
    public void display(GLAutoDrawable drawable)
    {
        // update model
        playerShip.update();

        // update view
        camera.update();

        // render view
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        gl.glUseProgram(renderingProgram);

        int proj_loc = gl.glGetUniformLocation(renderingProgram, "proj_matrix");
        gl.glUniformMatrix4fv(proj_loc, 1, false, projMat.getFloatValues(), 0);

        Matrix3D viewMat = new Matrix3D();
        viewMat.rotate(camera.getPitch(), 0, 0);
        viewMat.rotate(0, camera.getYaw(), 0);
        viewMat.rotate(0, 0, camera.getRoll());
        viewMat.translate(-camera.getX(), -camera.getY(), -camera.getZ());

        MatrixStack.initialize(viewMat);

        int mv_loc = gl.glGetUniformLocation(renderingProgram, "mv_matrix");

        Set<Renderable> expireds = new HashSet<>();

        for(Renderable r : renderables)
        {
            r.update();
            if(r.expired()) expireds.add(r);
            else r.render(gl, mv_loc);
        }

        renderables.removeAll(expireds);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        this.width = width;
        this.height = height;
        centerPt = new Point(width / 2, height / 2);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}
}
