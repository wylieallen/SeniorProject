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

    public ShipStats(int maxHealth){
        this.maxHealth = maxHealth;
        this.maxShield = 0;
        this.maxFuel = 0;
        this.maxSpeed = 0;
        this.currentHealth = this.maxHealth;
    }


    //Getters and Setters
    public void setMaxHealth(int value){
        maxHealth = value;
    }

    public void setMaxShield(int maxShield){
        this.maxShield = maxShield;
        this.currentShield = this.maxShield;
    }

    public void setMaxFuel(int maxFuel){
        this.maxFuel = maxFuel;
        this.currentFuel = this.maxFuel;
    }

    public void setMaxSpeed(int maxSpeed){
        this.maxSpeed = maxSpeed;
        this.currentSpeed = 0;
    }

    public int getMaxHealth(){ return maxHealth; }

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
