package apps.digitakpark.payme.model;

/**
 * Created by MURRUER on 2/12/2017.
 */
public class Debt {
    private Long id;
    private String number;
    private String concept;
    private String currency;
    private Long date;
    private Long limit;
    private Double total;
    private boolean mine;

    public Debt() {
    }

    public Debt(Long id, String number, String concept, String currency, Long date, Double total, boolean mine, Long limit) {
        this.id = id;
        this.number = number;
        this.concept = concept;
        this.currency = currency;
        this.date = date;
        this.limit = limit;
        this.total = total;
        this.mine = mine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
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

    @Override
    public boolean equals(Object obj) {
        Debt debt = (Debt) obj;
        return this.id == debt.getId();
    }

    @Override
    public int hashCode() {
        return this.getId().intValue();
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
