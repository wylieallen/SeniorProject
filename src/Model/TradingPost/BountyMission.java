package Model.TradingPost;

public class BountyMission {
    private int currencyValue;
    private int enemyCount;
    private String enemyType;
    private boolean completed;

    public BountyMission(int cv,int ec, String et) {
        currencyValue = cv;
        enemyCount = ec;
        enemyType = et;
        completed = false;
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
