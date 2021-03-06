package apps.digitakpark.payapp.events;

import java.util.List;

import apps.digitakpark.payapp.model.DebtHeader;

public class ToPayDebtListEvent {

    public static final short DEBT_LIST_OK = 0x01;
    public static final short DEBT_LIST_ERROR = 0x02;
    public static final short DEBT_HEADER_DELETED_OK = 0x04;

    private short status;
    private List<DebtHeader> debtHeader;
    private String message;
    private Double total;

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public List<DebtHeader> getDebtHeader() {
        return debtHeader;
    }

    public void setDebtHeader(List<DebtHeader> debtHeader) {
        this.debtHeader = debtHeader;
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
}
