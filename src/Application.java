import guiframework.InterfacePanel;
import gameview.drawstate.OverworldDrawstate;
import gameview.drawstate.TradingPostDrawstate;
import guiframework.gui2d.Drawstate;
import guiframework.gui3d.Renderstate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Application
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::createAndShowGUI);
    }

    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame();
        frame.setSize(1800, 1000);
        //frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );

        frame.setTitle("Senior Project");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        TradingPostDrawstate tpUberstate = new TradingPostDrawstate();
        OverworldDrawstate overworldUberstate = new OverworldDrawstate();
//        Button exampleButton = new Button(new Point(256, 256),
//                ImageFactory.makeBorderedRect(128, 128, Color.WHITE, Color.GRAY),
//                ImageFactory.makeBorderedRect(128, 128, Color.RED, Color.GRAY),
//                ImageFactory.makeBorderedRect(128, 128, Color.ORANGE, Color.GRAY),
//                () -> {},
//                () -> {},
//                () -> {},
//                () -> {});
//
//        drawstate.addClickable(exampleButton);
//        drawstate.addOverlay(exampleButton);

        Drawstate drawstate = new Drawstate();

        InterfacePanel panel = new InterfacePanel(drawstate, new Renderstate(1800, 1000));
        panel.setSize(1800, 1000);
        panel.setBackground(Color.BLACK);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.validate();
        frame.setVisible(true);

        panel.addKeyListener(panel);

        frame.addComponentListener(new ComponentListener()
        {
            public void componentResized(ComponentEvent e)
            {
                panel.changeSize();
            }

            public void componentMoved(ComponentEvent e) { }

            public void componentHidden(ComponentEvent e) { }

            public void componentShown(ComponentEvent e) { }
        });
    }
}
