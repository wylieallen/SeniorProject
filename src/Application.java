import gameview.GameUberstate;
import gameview.drawstate.OverworldUberstate;
import guiframework.InterfacePanel;
import gameview.drawstate.TradingPostUberstate;
import guiframework.gui3d.Renderstate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Application
{
    private static final int WIDTH = 1800, HEIGHT = 1000;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::createAndShowGUI);
    }

    private static void createAndShowGUI()
    {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        //frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );

        frame.setTitle("Senior Project");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Renderstate renderstate = new Renderstate(WIDTH, HEIGHT);

        TradingPostUberstate tpUberstate = new TradingPostUberstate(renderstate);
        OverworldUberstate overworldUberstate = new OverworldUberstate(renderstate);
        GameUberstate gameUberstate = new GameUberstate(renderstate, new Point(WIDTH / 2, HEIGHT / 2));

        InterfacePanel panel = new InterfacePanel(gameUberstate);
        panel.setSize(WIDTH, HEIGHT);
        panel.setBackground(Color.BLACK);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.validate();
        frame.setVisible(true);

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
