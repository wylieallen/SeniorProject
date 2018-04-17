package Model.Ship.ShipParts;
import Model.Items.Item;
import Model.Ship.Ship;
import Utility.Rarity;

public abstract class ShipPart extends Item
{
    private Rarity rarity;

    protected ShipPart(String name, int value, Rarity rarity)
    {
        super(name, value);
        this.rarity = rarity;
    }

    public void update() {}

    public Rarity getRarity() { return rarity; }
    public void setRarity(Rarity rarity) {this.rarity = rarity; }

    public abstract void equip(Ship ship);

    @Override
    public boolean isShipPart(){return true;}
}
