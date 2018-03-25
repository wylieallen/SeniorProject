package guiframework.gui3d;

import com.jogamp.opengl.*;
import graphicslib3D.Matrix3D;
import guiframework.gui3d.model3d.Model3DFactory;
import guiframework.gui3d.renderable.Renderable;
import guiframework.gui3d.camera.Camera;
import guiframework.gui3d.util.MatrixStack;
import guiframework.gui3d.util.ShaderParser;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;

public class Renderstate
{
    private Point centerPt;
    private int width, height;

    private int renderingProgram;

    private Camera camera;

    private Matrix3D projMat;

    private Set<Renderable> renderables;

    public Renderstate(int width, int height)
    {
        this.camera = new Camera();

        this.width = width;
        this.height = height;

        centerPt = new Point(width / 2, height / 2);

        this.renderables = new HashSet<>();
    }

    public void add(Renderable r) { renderables.add(r); }
    public void remove(Renderable r) { renderables.remove(r); }

    public void init(GLAutoDrawable drawable)
    {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        renderingProgram = ShaderParser.createShaderProgram(gl);

        Model3DFactory.initializeBuffers(gl);

        float aspect = (float) width / (float) height;
        projMat = makePerspectiveMatrix(60.0f, aspect, 1.0f, 1000.0f);

        gl.glEnable(GL4.GL_DEPTH_TEST);
        gl.glDepthFunc(GL4.GL_LEQUAL);
    }

    private Matrix3D makePerspectiveMatrix(float verticalFOV, float aspect, float minDraw, float maxDraw)
    {
        float q = 1.0f / ((float) Math.tan(Math.toRadians(0.5f * verticalFOV)));
        float A = q / aspect;
        float B = (minDraw + maxDraw) / ( minDraw - maxDraw );
        float C = (2.0f * minDraw * maxDraw) / (minDraw - maxDraw);

        Matrix3D r = new Matrix3D();

        r.setElementAt(0, 0, A);
        r.setElementAt(1, 1, q);
        r.setElementAt(2, 2, B);
        r.setElementAt(3, 2, -1.0f);
        r.setElementAt(2, 3, C);
        r.setElementAt(3, 3, 0.0f);

        return r;
    }

    public void display(GLAutoDrawable drawable)
    {
        camera.update();

        // render view
        // clear and initialize GL:
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        gl.glUseProgram(renderingProgram);

        // initialize matrices:
        int proj_loc = gl.glGetUniformLocation(renderingProgram, "proj_matrix");
        gl.glUniformMatrix4fv(proj_loc, 1, false, projMat.getFloatValues(), 0);

        MatrixStack.initialize(makeViewMatrix());

        int mv_loc = gl.glGetUniformLocation(renderingProgram, "mv_matrix");

        // update and render renderables:
        Set<Renderable> expireds = new HashSet<>();

        for(Renderable r : renderables)
        {
            r.update();
            if(r.expired()) expireds.add(r);
            else r.render(gl, mv_loc);
        }

        renderables.removeAll(expireds);
    }

    private Matrix3D makeViewMatrix()
    {
        Matrix3D viewMat = new Matrix3D();
        viewMat.rotate(camera.getPitch(), 0, 0);
        viewMat.rotate(0, camera.getYaw(), 0);
        viewMat.rotate(0, 0, camera.getRoll());
        viewMat.translate(-camera.getX(), -camera.getY(), -camera.getZ());
        return viewMat;
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        this.width = width;
        this.height = height;
        centerPt = new Point(width / 2, height / 2);
    }

    public void dispose(GLAutoDrawable drawable) {}

    public Camera getCamera()
    {
        return camera;
    }

    public void setCamera(Camera camera) { this.camera = camera; }
}
