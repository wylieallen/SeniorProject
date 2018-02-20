package Model.TradingPost;

public class BountyMission {
    private int currencyValue;
    private String enemyType;
    private boolean completed;

    public BountyMission(int cv, String et) {
        currencyValue = cv;
        enemyType = et;
        completed = false;
    }

    public int getCurrencyValue() {return currencyValue;}

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
