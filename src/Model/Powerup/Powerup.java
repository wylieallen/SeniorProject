package Model.Powerup;

import Model.Ship.*;

public abstract class Powerup {
    private int useValue;

    public abstract int getUseValue();
    public abstract void setUseValue(int uv);
    public abstract void Use(Ship s);
}
