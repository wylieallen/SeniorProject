package Model.Ship;

public class ShipStats {

    private int maxHealth;
    private int maxShield;
    private int maxFuel;
    private int maxSpeed;
    private int currentHealth;
    private int currentShield;
    private int currentFuel;
    private int currentSpeed;

    //Getters and Setters
    public void setMaxHealth(int value){
        maxHealth = value;
    }

    public void setMaxShield(int value){
        maxShield = value;
    }

    public void setMaxFuel(int value){
        maxFuel = value;
    }

    public void setMaxSpeed(int value){
        maxSpeed = value;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public int getMaxShield(){
        return maxShield;
    }

    public int getMaxFuel(){
        return maxFuel;
    }

    public int getMaxSpeed(){
        return maxSpeed;
    }

    public void modifyCurrentHealth(int amount){
        currentHealth += amount;
    }

    public void modifyCurrentShield(int amount){
        currentShield += amount;
    }

    public void modifyCurrentFuel(int amount){
        currentFuel += amount;
    }

    public void modifyCurrentSpeed(int amount){
        currentSpeed += amount;
    }

    public int getCurrentHealth(){
        return currentHealth;
    }

    public int getCurrentShield(){
        return currentShield;
    }

    public int getCurrentFuel(){
        return currentFuel;
    }

    public int getCurrentSpeed(){
        return currentSpeed;
    }

    // End Getter/Setters

    public boolean isAlive(){
        if (currentHealth > 0){
            return true;
        }
        return false;
    }



    
}
