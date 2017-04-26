package apps.digitakpark.payapp.payments;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.model.Payment;

public interface PaymentIteractor {
    void doRetrievePaymenttList(String number, boolean mine, Long debtId);

    void doRegisterPayment(Payment payment, Double currentTotal, Double newTotal);

    void doCloseDebtHeader(DebtHeader debtHeader);

    void doCloseDebtHeader(Debt debt);

}
