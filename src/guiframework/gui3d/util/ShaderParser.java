package guiframework.gui3d.util;

import com.jogamp.opengl.GL4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

public class ShaderParser
{
    private static final String vertexPath = "v.shader";
    private static final String fragmentPath = "f.shader";

    public static int createShaderProgram(GL4 gl)
    {
        String vShaderSource[] = readShaderSource(vertexPath);

        int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
        gl.glShaderSource(vShader, vShaderSource.length, vShaderSource, null, 0);
        gl.glCompileShader(vShader);

        String fShaderSource[] = readShaderSource(fragmentPath);

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

    private static String[] readShaderSource(String filename)
    {
        List<String> lines = new ArrayList<>();
        Scanner sc;
        try
        {
            sc = new Scanner(new File(filename));
            while(sc.hasNext())
            {
                lines.add(sc.nextLine());
            }
        }
        catch(IOException e)
        {
            System.out.println("FAILED TO LOAD SHADER: " + filename);
            e.printStackTrace();
        }

        String[] program = new String[lines.size()];

        for(int i = 0; i < lines.size(); i++)
        {
            program[i] = lines.get(i) + "\n";
        }

        return program;
    }
}
