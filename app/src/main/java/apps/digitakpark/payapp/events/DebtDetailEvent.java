package apps.digitakpark.payapp.events;

import java.util.List;

import apps.digitakpark.payapp.model.Debt;

public class DebtDetailEvent {

    public static final short DEBT_DETAIL_OK = 0x01;
    public static final short DEBT_DETAIL_ERROR = 0x02;
    public static final short DEBT_DELETED_OK = 0x03;
    public static final short DEBT_HEADER_DELETED_OK = 0x04;

    private short status;
    private List<Debt> debtList;
    private String message;
    private Double total;
    private Long debtId;

    public static short getDebtDetailOk() {
        return DEBT_DETAIL_OK;
    }

    public static short getDebtDetailError() {
        return DEBT_DETAIL_ERROR;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public List<Debt> getDebtList() {
        return debtList;
    }

    public void setDebtList(List<Debt> debtList) {
        this.debtList = debtList;
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

    public Long getDebtId() {
        return debtId;
    }

    public void setDebtId(Long debtId) {
        this.debtId = debtId;
    }
}
