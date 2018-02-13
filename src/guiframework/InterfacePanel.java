package guiframework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfacePanel extends JPanel implements KeyListener {

    private Uberstate activeState;

    public InterfacePanel(Uberstate activeState)
    {
        this.activeState = activeState;

        this.addMouseListener(new MouseListener()
        {
           public void mouseEntered(MouseEvent e)
           {
                System.out.println("Mouse entered at " + e.getPoint());
           }

           public void mousePressed(MouseEvent e)
           {
                System.out.println("Mouse button " + e.getButton() + " pressed at " + e.getPoint());
                activeState.parseMousePress(e);
           }

           public void mouseClicked(MouseEvent e)
           {
                System.out.println("Mouse button " + e.getButton() + " clicked at " + e.getPoint());
           }

           public void mouseReleased(MouseEvent e)
           {
                System.out.println("Mouse button " + e.getButton() + " released at " + e.getPoint());
                activeState.parseMouseRelease(e);
           }

           public void mouseExited(MouseEvent e)
           {
                System.out.println("Mouse exited at " + e.getPoint());
           }
        });

        this.addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                System.out.println("Mouse button " + e.getButton() + " dragged to " + e.getPoint());
                activeState.parseMouseDrag(e);
            }

            public void mouseMoved(MouseEvent e)
            {
                System.out.println("Mouse moved to " + e.getPoint());
                activeState.parseMouseMove(e);
            }
        });
    }

    public void keyPressed(KeyEvent e)
    {
        System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " pressed");
        activeState.parseKeyPress(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e)
    {
        System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " released");
        activeState.parseKeyRelease(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e)
    {
        System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " typed");
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        activeState.draw((Graphics2D) g);
    }

}