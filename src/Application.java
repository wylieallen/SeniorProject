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

        JPanel panel = new JPanel();
        panel.setSize(1280, 720);
        panel.setBackground(Color.BLACK);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        //InterfacePanel panel = new InterfacePanel(new Dimension(1280, 720));

        frame.validate();
        frame.setVisible(true);
    }
}
