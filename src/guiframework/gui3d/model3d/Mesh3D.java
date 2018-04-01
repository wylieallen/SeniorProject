package guiframework.gui3d.model3d;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;

import java.nio.FloatBuffer;

public class Mesh3D
{
    private int vbo;
    private float[] vertexData;
    private int vertexCount;

    public Mesh3D(GL4 gl, int vbo, float[] vertexData)
    {
        this.vbo = vbo;
        this.vertexData = vertexData;
        this.vertexCount = vertexData.length / 3;

        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = Buffers.newDirectFloatBuffer(vertexData);
        gl.glBufferData(GL.GL_ARRAY_BUFFER, buffer.limit()*4, buffer, GL.GL_STATIC_DRAW);
    }

    public void bind(GL4 gl)
    {
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo);
        gl.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, 0, 0);
        gl.glEnableVertexAttribArray(0);
    }

    public void render(GL4 gl)
    {
        gl.glDrawArrays(GL4.GL_TRIANGLES, 0, vertexCount);
    }
}
