package guiframework.gui3d.model3d;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.File;

public class Model3DFactory
{
    private static final int VAO_COUNT = 1;
    private static final int VBO_COUNT = 4;

    private static final int vao[] = new int[VAO_COUNT];
    private static final int vbo[] = new int[VBO_COUNT];

    private static Model3D cubeModel;
    private static Model3D pyrModel;

    public static void initializeBuffers(GL4 gl)
    {
        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);

        gl.glGenBuffers(vbo.length, vbo, 0);

        int texObject = loadTexture("resources/bison.jpg");

        cubeModel = new Model3D(new Texture3D(gl, vbo[2], cube_tex_vertices, texObject), new Mesh3D(gl, vbo[0], cube_vertices));
        pyrModel = new Model3D(new Texture3D(gl, vbo[3], pyr_tex_vertices, texObject), new Mesh3D(gl, vbo[1], pyr_vertices));
    }

    private static int loadTexture(String filepath)
    {
        Texture tex = null;
        try
        {
            tex = TextureIO.newTexture(new File(filepath), false);
            return tex.getTextureObject();
        }
        catch(Exception e) { e.printStackTrace(); }
        return -1;
    }

    public static Model3D getCubeModel() { return cubeModel; }
    public static Model3D getPyramidModel() { return pyrModel; }

    private static final float[] pyr_vertices = {
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

    private static final float[] pyr_tex_vertices = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.5f, 1.0f,

            0.0f, 0.0f,
            1.0f, 0.0f,
            0.5f, 1.0f,

            0.0f, 0.0f,
            1.0f, 0.0f,
            0.5f, 1.0f,

            0.0f, 0.0f,
            1.0f, 0.0f,
            0.5f, 1.0f,

            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f,

            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f
    };

    private static final float[] cube_vertices = {
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

    private static final float[] cube_tex_vertices = {
            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,

            0.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,
    };
}
