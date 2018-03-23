package guiframework;

import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import guiframework.gui2d.Drawstate;
import guiframework.gui3d.Renderstate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfacePanel extends GLJPanel implements KeyListener
{
    private Drawstate activeState;
    private Renderstate activeRenderstate;

    public InterfacePanel(Drawstate activeState, Renderstate renderstate)
    {
        this.activeState = activeState;
        this.activeRenderstate = renderstate;

        this.addGLEventListener(renderstate);

        new FPSAnimator(this, 60, true).start();

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
                //System.out.println("Mouse button " + e.getButton() + " dragged to " + e.getPoint());
                activeState.parseMouseDrag(e);
            }

            public void mouseMoved(MouseEvent e)
            {
                //System.out.println("Mouse moved to " + e.getPoint());
                activeState.parseMouseMove(e);
            }
        });
    }

    public void keyPressed(KeyEvent e)
    {
        System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " pressed");
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_W:
                activeRenderstate.getPlayerShip().setAccelerating(true);
                break;

            case KeyEvent.VK_S:
                activeRenderstate.getPlayerShip().setDecelerating(true);
                break;

            case KeyEvent.VK_LEFT:
                activeRenderstate.getPlayerShip().setYawingLeft(true);
                break;

            case KeyEvent.VK_RIGHT:
                activeRenderstate.getPlayerShip().setYawingRight(true);
                break;

            case KeyEvent.VK_UP:
                activeRenderstate.getPlayerShip().setPitchingDown(true);
                break;

            case KeyEvent.VK_DOWN:
                activeRenderstate.getPlayerShip().setPitchingUp(true);
                break;

        }
        activeState.parseKeyPress(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e)
    {
        System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " released");
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_W:
                activeRenderstate.getPlayerShip().setAccelerating(false);
                break;

            case KeyEvent.VK_S:
                activeRenderstate.getPlayerShip().setDecelerating(false);
                break;

            case KeyEvent.VK_LEFT:
                activeRenderstate.getPlayerShip().setYawingLeft(false);
                break;

            case KeyEvent.VK_RIGHT:
                activeRenderstate.getPlayerShip().setYawingRight(false);
                break;

            case KeyEvent.VK_UP:
                activeRenderstate.getPlayerShip().setPitchingDown(false);
                break;

            case KeyEvent.VK_DOWN:
                activeRenderstate.getPlayerShip().setPitchingUp(false);
                break;
        }
        activeState.parseKeyRelease(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e)
    {
        System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " typed");
    }

    public void changeSize()
    {
        activeState.changeSize(this.getSize());
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        activeState.update();
        activeState.draw((Graphics2D) g);
    }

}
