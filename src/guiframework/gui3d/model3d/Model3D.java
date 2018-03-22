package guiframework.gui3d.model3d;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;

import java.nio.FloatBuffer;

public class Model3D
{
    private int vbo;
    private float[] vertexData;

    public Model3D(GL4 gl, int vbo, float[] vertexData)
    {
        this.vbo = vbo;
        this.vertexData = vertexData;

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = Buffers.newDirectFloatBuffer(vertexData);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, buffer.limit()*4, buffer, GL.GL_STATIC_DRAW);
    }

    public float[] getVertexData() { return vertexData; }
    public int getVBO() { return vbo; }
}
