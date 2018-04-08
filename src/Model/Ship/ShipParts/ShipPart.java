package Model.Ship.ShipParts;
import Model.Items.Item;
import Utility.Rarity;

public abstract class ShipPart extends Item
{
    private Rarity rarity;

    protected ShipPart(String name, int value, String attributes, Rarity rarity)
    {
        super(name, value, attributes);
        this.rarity = rarity;
    }

    public void update() {}

    public Rarity getRarity() { return rarity; }
    public void setRarity(Rarity rarity) {this.rarity = rarity; }
}
