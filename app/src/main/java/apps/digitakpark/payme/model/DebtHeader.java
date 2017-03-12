package apps.digitakpark.payme.model;


public class DebtHeader {

    private String name;
    private String number;
    private String currency;
    private Double total;
    private boolean mine;

    public DebtHeader() {}

    public DebtHeader(String name, String number, String currency, Double total, boolean mine) {
        this.name = name;
        this.number = number;
        this.currency = currency;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
