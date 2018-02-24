package Utility;

import java.util.Random;

public class RandomNumberGenerator{

    private Random rng;

    public RandomNumberGenerator(){
        rng = new Random();
    }

    public int getRarityRandomInRange(int baseValue, int offset, Rarity rarity){
        double multiplier;
        switch (rarity){
            case COMMON:
                multiplier = 1;
                break;
            case RARE:
                multiplier = 1.5;
                break;
            case EPIC:
                multiplier = 2;
                break;
            case LEGENDARY:
                multiplier = 3;
                break;
            default:
                multiplier = 1;
                break;
        }
        double random = rng.nextInt(offset)*multiplier + baseValue;
        return (int) random;
    }

}
