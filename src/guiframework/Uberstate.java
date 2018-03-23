package guiframework;

import guiframework.gui2d.Drawstate;
import guiframework.gui3d.Renderstate;

public class Uberstate
{
    private Drawstate drawstate;
    private Renderstate renderstate;

    public Uberstate(Drawstate drawstate, Renderstate renderstate)
    {
        this.drawstate = drawstate;
        this.renderstate = renderstate;
    }


}
