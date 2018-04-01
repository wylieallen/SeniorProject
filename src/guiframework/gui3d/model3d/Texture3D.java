package guiframework.gui3d.model3d;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;

import java.nio.FloatBuffer;

public class Texture3D
{
    private int vbo;
    private float[] vertexData;
    private int textureObject;

    public Texture3D(GL4 gl, int vbo, float[] vertexData, int textureObject)
    {
        this.vbo = vbo;
        this.vertexData = vertexData;
        this.textureObject = textureObject;

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = Buffers.newDirectFloatBuffer(vertexData);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, buffer.limit()*4, buffer, GL.GL_STATIC_DRAW);
    }

    public void bind(GL4 gl)
    {
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
        gl.glVertexAttribPointer(1, 2, GL.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(1);
        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureObject);
    }

    public void render(GL4 gl)
    {

    }
}
