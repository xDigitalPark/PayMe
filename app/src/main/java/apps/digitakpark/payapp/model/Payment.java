package apps.digitakpark.payapp.model;

public class Payment {

    private Long id;
    private String number;
    private Long date;
    private Double total;

    public Payment(Long id, String number, Long date, Double total) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.total = total;
    }

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
}
