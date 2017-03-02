package payme.apps.seven.org.payme.events;

import java.util.List;

import payme.apps.seven.org.payme.model.Balance;

public class BalanceEvent {

    public static final short BALANCE_OK = 0x01;
    public static final short BALANCE_ERROR = 0x02;
    public static short BALANCE_DELETED_OK = 0x03;

    private short status;
    private List<Balance> balanceList;
    private String message;
    private Double total;
    private Balance balance;

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public List<Balance> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<Balance> balanceList) {
        this.balanceList = balanceList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }
}
