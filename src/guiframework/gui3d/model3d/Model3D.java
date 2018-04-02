package guiframework.gui3d.model3d;

import com.jogamp.opengl.GL4;

public class Model3D
{
    private Texture3D texture;
    private Mesh3D mesh;

    public Model3D(Texture3D texture, Mesh3D mesh)
    {
        this.texture = texture;
        this.mesh = mesh;
    }

    public void render(GL4 gl)
    {
        mesh.bind(gl);
        texture.bind(gl);
        mesh.render(gl);
    }
}
