package Model.TradingPost;

public class BountyMission {
    private int currencyValue;
    private int enemyCount;
    private String enemyType;
    private boolean completed;
    private int currentCount;

    public BountyMission(int cv,int ec, String et) {
        currencyValue = cv;
        enemyCount = ec;
        enemyType = et;
        currentCount = 0;
        completed = false;
    }

    public void increaseBounty(){
        currentCount++;
        if (currentCount >= enemyCount){
            completed = true;
        }
    }

    public int getCurrencyValue() {return currencyValue;}

    public int getEnemyCount() {
        return enemyCount;
    }

    public String getEnemyType() {
        return enemyType;
    }

    public void completeMission() {
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
