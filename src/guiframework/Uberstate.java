package guiframework;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import guiframework.control.ClickableControlstate;
import guiframework.gui2d.Drawstate;
import guiframework.gui3d.Renderstate;
import guiframework.util.AbstractFunction;

import java.awt.*;

public class Uberstate implements GLEventListener
{
    private Drawstate drawstate;
    private final Renderstate renderstate;
    private ClickableControlstate controlstate;

    public Uberstate(Drawstate drawstate, Renderstate renderstate, ClickableControlstate controlstate)
    {
        this.drawstate = drawstate;
        this.renderstate = renderstate;
        this.controlstate = controlstate;
    }

    public void init(GLAutoDrawable drawable)
    {
        renderstate.init(drawable);
    }

    public void display(GLAutoDrawable drawable)
    {
        renderstate.display(drawable);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        renderstate.reshape(drawable, x, y, width, height);
        drawstate.changeSize(new Dimension(width, height));
    }

    public void dispose(GLAutoDrawable drawable)
    {
        renderstate.dispose(drawable);
    }

    public void draw(Graphics2D g2d)
    {
        drawstate.update();
        drawstate.draw(g2d);
    }

    public void parseKeyPress(int keycode) { controlstate.parseKeyPress(keycode); }
    public void parseKeyRelease(int keycode) { controlstate.parseKeyRelease(keycode);}
    public void parseMousePress(int buttoncode) { controlstate.parseMousePress(buttoncode); }
    public void parseMouseRelease(int buttoncode) { controlstate.parseMouseRelease(buttoncode); }
    public void parseMouseMove(Point point) { controlstate.parseMouseMove(point); }

    public void bindKeyPress(int keycode, AbstractFunction function) { controlstate.bindKeyPress(keycode, function); }
    public void bindKeyRelease(int keycode, AbstractFunction function) { controlstate.bindKeyRelease(keycode, function); }
    public void bindMousePress(int buttoncode, AbstractFunction function) { controlstate.bindMousePress(buttoncode, function); }
    public void bindMouseRelease(int buttoncode, AbstractFunction function) { controlstate.bindMouseRelease(buttoncode, function); }

    public Renderstate getRenderstate() { return renderstate; }
    public ClickableControlstate getControlstate() { return controlstate; }
    public Drawstate getDrawstate() { return drawstate; }

    public void setDrawstate(Drawstate drawstate) { this.drawstate = drawstate; }
    public void setControlstate(ClickableControlstate controlstate) { this.controlstate = controlstate; }

    public void changeSize(Dimension size)
    {
        drawstate.changeSize(size);
    }
}
