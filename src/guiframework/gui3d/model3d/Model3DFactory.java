package guiframework.gui3d.model3d;

import com.jogamp.opengl.GL4;

public class Model3DFactory
{
    private static final int VAO_COUNT = 1;
    private static final int VBO_COUNT = 2;

    private static final int vao[] = new int[VAO_COUNT];
    private static final int vbo[] = new int[VBO_COUNT];

    private static Model3D cubeModel;
    private static Model3D pyrModel;

    public static void initializeBuffers(GL4 gl)
    {
        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);

        gl.glGenBuffers(vbo.length, vbo, 0);

        cubeModel = new Model3D(gl, vbo[0], cube_vertices);
        pyrModel = new Model3D(gl, vbo[1], pyr_vertices);
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
}
