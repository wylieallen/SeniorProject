package guiframework.gui3d.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShaderParser
{
    public static String[] readShaderSource(String filename)
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
