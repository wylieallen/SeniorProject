import javax.swing.*;
import java.awt.*;

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

        InterfacePanel panel = new InterfacePanel(new Uberstate());
        panel.setSize(1280, 720);
        panel.setBackground(Color.BLACK);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.validate();
        frame.setVisible(true);

        frame.addKeyListener(panel);

    }
}
