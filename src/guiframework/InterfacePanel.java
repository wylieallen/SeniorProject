package guiframework;

import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import guiframework.control.ClickableControlstate;
import guiframework.control.Controlstate;
import guiframework.gui2d.Drawstate;
import guiframework.gui3d.Renderstate;

import java.awt.*;
import java.awt.event.*;

public class InterfacePanel extends GLJPanel
{
    private Uberstate uberstate;
    private Uberstate overworld, tradingpost, inflight;

    public InterfacePanel(Uberstate uberstate)
    {
        this.uberstate = uberstate;
        // instantiate overworld, tradingpost, inflight...

        this.addGLEventListener(uberstate);

        new FPSAnimator(this, 60, true).start();

        this.addMouseListener(new MouseListener()
        {
           public void mouseEntered(MouseEvent e)
           {
                //System.out.println("Mouse entered at " + e.getPoint());
           }

           public void mousePressed(MouseEvent e)
           {
                System.out.println("Mouse button " + e.getButton() + " pressed at " + e.getPoint());
                uberstate.parseMousePress(e.getButton());
           }

           public void mouseClicked(MouseEvent e)
           {
                //System.out.println("Mouse button " + e.getButton() + " clicked at " + e.getPoint());
           }

           public void mouseReleased(MouseEvent e)
           {
                System.out.println("Mouse button " + e.getButton() + " released at " + e.getPoint());
               uberstate.parseMouseRelease(e.getButton());
           }

           public void mouseExited(MouseEvent e)
           {
                //System.out.println("Mouse exited at " + e.getPoint());
           }
        });

        this.addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                //System.out.println("Mouse button " + e.getButton() + " dragged to " + e.getPoint());
                this.mouseMoved(e);
            }

            public void mouseMoved(MouseEvent e)
            {
                //System.out.println("Mouse moved to " + e.getPoint());
                uberstate.parseMouseMove(e.getPoint());
            }
        });

        this.addKeyListener(new KeyListener()
        {
            public void keyPressed(KeyEvent e)
            {
                //System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " pressed");
                uberstate.parseKeyPress(e.getKeyCode());
            }

            public void keyReleased(KeyEvent e)
            {
                //System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " released");
                uberstate.parseKeyRelease(e.getKeyCode());
            }

            public void keyTyped(KeyEvent e)
            {
                //System.out.println("Key " + e.getKeyChar() + " with keycode " + e.getKeyCode() + " typed");
            }
        });
    }

    public void switchToOverworld() { this.uberstate = overworld; }

    public void changeSize() { uberstate.changeSize(getSize()); }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        uberstate.draw((Graphics2D) g);
    }

}
