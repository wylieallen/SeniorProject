package guiframework.gui3d.model3d;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import graphicslib3D.Vertex3D;
import guiframework.gui3d.util.importing.ImportedModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Model3DFactory
{
    private static final int VAO_COUNT = 1;
    private static final int VBO_COUNT = 8;

    private static final int vao[] = new int[VAO_COUNT];
    private static final int vbo[] = new int[VBO_COUNT];

    private static Model3D cubeModel;
    private static Model3D pyrModel;
    private static Model3D feisarModel;
    private static Model3D projectileModel;

    public static void initializeBuffers(GL4 gl)
    {
        gl.glGenVertexArrays(vao.length, vao, 0);
        gl.glBindVertexArray(vao[0]);

        gl.glGenBuffers(vbo.length, vbo, 0);

        int texObject = loadTexture("resources/bison.jpg");

        cubeModel = new Model3D(new Texture3D(gl, vbo[2], cube_tex_vertices, texObject), new Mesh3D(gl, vbo[0], cube_vertices));
        pyrModel = new Model3D(new Texture3D(gl, vbo[3], pyr_tex_vertices, texObject), new Mesh3D(gl, vbo[1], pyr_vertices));


        feisarModel = makeModel(gl, vbo[4], vbo[5], "resources/h2f2/mat.png", "resources/h2f2/h2f3.obj");//new Model3D(new Texture3D(gl, vbo[4], feisar_tex_vertices, feisarTexObject), new Mesh3D(gl, vbo[5], feisar_vertices));
        projectileModel = makeModel(gl, vbo[6], vbo[7], "resources/Projectile_Spiky.png", "resources/Projectile_Spiky.obj");

    }

    private static Model3D makeModel(GL4 gl, int texVBO, int meshVBO, String texPath, String meshPath)
    {
        int textureObject = loadTexture(texPath);//"resources/maps/diffuse.bmp");
        ImportedModel importedModel = new ImportedModel(meshPath);//Feisar_Tris.obj");

        List<Float> textureVertexList = new ArrayList<>();
        for(Vertex3D vertex : importedModel.getVertices())
        {
            textureVertexList.add((float) vertex.getS());
            textureVertexList.add((float) vertex.getT());
        }

        float[] tex_vertices = new float[textureVertexList.size()];
        for(int i = 0; i < textureVertexList.size(); i++)
        {
            tex_vertices[i] = textureVertexList.get(i);
        }

        List<Float> meshVertexList = new ArrayList<>();
        for(Vertex3D vertex : importedModel.getVertices())
        {
            meshVertexList.add((float) vertex.getX());
            meshVertexList.add((float) vertex.getY());
            meshVertexList.add((float) vertex.getZ());
        }

        float[] mesh_vertices = new float[meshVertexList.size()];
        for(int i = 0; i < meshVertexList.size(); i++)
        {
            mesh_vertices[i] = meshVertexList.get(i);
        }

        return new Model3D(new Texture3D(gl, texVBO, tex_vertices, textureObject), new Mesh3D(gl, meshVBO, mesh_vertices));
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
    public static Model3D getFeisarModel() { return feisarModel; }
    public static Model3D getProjectileModel() { return projectileModel; }

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
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,

            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
    };
}
