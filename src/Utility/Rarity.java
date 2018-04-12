package Utility;

public enum Rarity {
    COMMON (100),
    RARE (250),
    EPIC (500),
    LEGENDARY (1000);

    private final int value;
    Rarity(int value){
        this.value = value;
    }

    public int value() { return value; }
}
