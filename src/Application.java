import guiframework.InterfacePanel;
import guiframework.Uberstate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Application
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::createAndShowGUI);
    }

    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame();
        frame.setSize(1280, 720);

        frame.setTitle("Senior Project");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Uberstate uberstate = new Uberstate();

        InterfacePanel panel = new InterfacePanel(uberstate);
        panel.setSize(1280, 720);
        panel.setBackground(Color.BLACK);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.validate();
        frame.setVisible(true);

        frame.addKeyListener(panel);

    }
}
