package Utility.Geom3D;

public class Dimension3D
{
    private float width, height, length;

    public Dimension3D(float width, float height, float length)
    {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public Dimension3D(Dimension3D original) { this(original.width, original.height, original.length); }
    public Dimension3D(float size) { this(size, size, size); }
    public Dimension3D() { this(0); }

    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getLength() { return length; }
}
