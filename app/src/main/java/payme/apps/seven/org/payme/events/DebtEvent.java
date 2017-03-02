package payme.apps.seven.org.payme.events;

import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public class DebtEvent {

    public static final short DEBT_CREATED = 0x01;
    public static final short DEBT_CREATION_ERROR = 0x02;

    private short status;
    private Debt debt;
    private DebtHeader debtHeader;
    private String message;

    public DebtHeader getDebtHeader() {
        return debtHeader;
    }

    public void setDebtHeader(DebtHeader debtHeader) {
        this.debtHeader = debtHeader;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public payme.apps.seven.org.payme.model.Debt getDebt() {
        return debt;
    }

    public void setDebt(payme.apps.seven.org.payme.model.Debt debt) {
        debt = debt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
