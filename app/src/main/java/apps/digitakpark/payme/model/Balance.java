package apps.digitakpark.payme.model;

/**
 * Created by MURRUER on 2/12/2017.
 */
public class Balance {
    private String name;
    private String number;
    private String currency;
    private Double partyTotal;
    private Double myTotal;
    private boolean mine;
    private Double total;

    public Balance() {
    }

    public Balance(String name, String number, String currency, Double partyTotal, Double myTotal, Double total, boolean mine) {
        this.name = name;
        this.number = number;
        this.currency = currency;
        this.partyTotal = partyTotal;
        this.myTotal = myTotal;
        this.total = total;
        this.mine = mine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPartyTotal() {
        return partyTotal;
    }

    public void setPartyTotal(Double partyTotal) {
        this.partyTotal = partyTotal;
    }

    public Double getMyTotal() {
        return myTotal;
    }

    public void setMyTotal(Double myTotal) {
        this.myTotal = myTotal;
    }

    public boolean isMine() {
        return mine;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        Balance balance = (Balance) obj;
        return this.number.equals(balance.getNumber());
    }

}
