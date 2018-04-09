package Model.Ship;

public class ShipStats {

    private int maxHealth;
    private int maxShield;
    private int maxFuel;
    private double maxSpeed;
    private int currentHealth;
    private int currentShield;
    private int currentFuel;
    private double currentSpeed;

    //Used for AI
    private double detectRange;

    public ShipStats(int maxHealth){
        this.maxHealth = maxHealth;
        this.maxShield = 0;
        this.maxFuel = 0;
        this.maxSpeed = 0;
        this.currentHealth = this.maxHealth;
        detectRange = 100;
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

    public void scaleMaxSpeed(double speedFactor){
        this.maxSpeed = maxSpeed*speedFactor;
        this.currentSpeed = currentSpeed*speedFactor;
    }

    public int getMaxHealth(){ return maxHealth; }

    public int getMaxShield(){
        return maxShield;
    }

    public int getMaxFuel(){
        return maxFuel;
    }

    public double getMaxSpeed(){
        return maxSpeed;
    }

    public void modifyCurrentHealth(int amount){
        if (amount < 0 && currentShield > 0){
            modifyCurrentShield(amount);
        }
        else {
            currentHealth += amount;
            if (currentHealth > maxHealth){
                currentHealth = maxHealth;
            }
        }
    }

    public void modifyCurrentShield(int amount){
        currentShield += amount;
        if (currentShield > maxShield){
            currentShield = maxShield;
        }

        if (currentShield < 0){
            amount = currentShield;
            currentShield = 0;
            modifyCurrentHealth(amount);
        }
    }

    public void modifyCurrentFuel(int amount){
        currentFuel += amount;
        if (currentFuel > maxFuel){
            currentFuel = maxFuel;
        }
    }

    public void modifyCurrentSpeed(double amount){
        currentSpeed += amount;

        if (currentSpeed > maxSpeed){
            currentSpeed = maxSpeed;
        }
        if (currentSpeed < -maxSpeed){
            currentSpeed = -maxSpeed;
        }
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

    public double getCurrentSpeed(){ return currentSpeed; }

    public double getDetectRange() {
        return detectRange;
    }

    public void setDetectRange(double detectRange) {
        this.detectRange = detectRange;
    }

    // End Getter/Setters

    public boolean isAlive(){
        if (currentHealth > 0){
            return true;
        }
        return false;
    }



    
}
