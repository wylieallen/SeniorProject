import guiframework.ImageFactory;
import guiframework.InterfacePanel;
import guiframework.TradingPostUberstate;
import guiframework.Uberstate;
import guiframework.clickable.Button;

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

        TradingPostUberstate tpUberstate = new TradingPostUberstate();
//        Button exampleButton = new Button(new Point(256, 256),
//                ImageFactory.makeBorderedRect(128, 128, Color.WHITE, Color.GRAY),
//                ImageFactory.makeBorderedRect(128, 128, Color.RED, Color.GRAY),
//                ImageFactory.makeBorderedRect(128, 128, Color.ORANGE, Color.GRAY),
//                () -> {},
//                () -> {},
//                () -> {},
//                () -> {});
//
//        uberstate.addClickable(exampleButton);
//        uberstate.addOverlay(exampleButton);

        InterfacePanel panel = new InterfacePanel(tpUberstate);
        panel.setSize(1280, 720);
        panel.setBackground(Color.BLACK);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.validate();
        frame.setVisible(true);

        frame.addKeyListener(panel);
    }
}
