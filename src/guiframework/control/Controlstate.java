package guiframework.control;

import guiframework.gui3d.renderable.AbstractRenderable;
import guiframework.util.AbstractFunction;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Controlstate
{
    private Map<Integer, AbstractFunction> keyPressMap, keyReleaseMap, mousePressMap, mouseReleaseMap;
    private Point mousePt;

    public Controlstate()
    {
        mousePt = new Point();
        keyPressMap = new HashMap<>();
        keyReleaseMap = new HashMap<>();
        mousePressMap = new HashMap<>();
        mouseReleaseMap = new HashMap<>();
    }

    public void parseKeyPress(int keycode) { keyPressMap.getOrDefault(keycode, AbstractFunction.NULL).execute(); }
    public void parseKeyRelease(int keycode) { keyReleaseMap.getOrDefault(keycode, AbstractFunction.NULL).execute(); }
    public void parseMousePress(int buttoncode) { mousePressMap.getOrDefault(buttoncode, AbstractFunction.NULL).execute(); }
    public void parseMouseRelease(int buttoncode) { mouseReleaseMap.getOrDefault(buttoncode, AbstractFunction.NULL).execute(); }

    public void parseMouseMove(Point point) { mousePt.setLocation(point); }

    public void bindKeyPress(int keycode, AbstractFunction function) { keyPressMap.put(keycode, function); }
    public void bindKeyRelease(int keycode, AbstractFunction function) { keyReleaseMap.put(keycode, function); }
    public void bindMousePress(int buttoncode, AbstractFunction function) { mousePressMap.put(buttoncode, function); }
    public void bindMouseRelease(int buttoncode, AbstractFunction function) { mouseReleaseMap.put(buttoncode, function); }

    public Point getMousePt() { return mousePt; }
}
