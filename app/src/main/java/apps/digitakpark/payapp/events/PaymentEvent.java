package apps.digitakpark.payapp.events;

import java.util.List;
import java.util.Map;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.Payment;

public class PaymentEvent {

    public static final short PAYMENT_LIST_OK = 0x01;
    public static final short PAYMENT_CREATED_OK = 0x02;
    public static final short DEBT_DELETE_OK = 0x03;

    private short status;
    private List<Payment> paymentList;
    private String message;
    private Double total;
    private Long paymentId;

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
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

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
