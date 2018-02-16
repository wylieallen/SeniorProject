package Model.TradingPost;

public class Wallet {
    int currencyBalance;

    public Wallet(int currencyBalance){
        this.currencyBalance = currencyBalance;
    }

    public int getCurrencyBalance() {return currencyBalance;}

    public void setCurrencyBalance(int currencyBalance) {this.currencyBalance = currencyBalance;}

    public void increaseCurrencyBalance(int increaseBalance) {currencyBalance += increaseBalance;}

    public void decreaseCurrencyBalance(int decreaseBalance) {currencyBalance -= decreaseBalance;}
}
