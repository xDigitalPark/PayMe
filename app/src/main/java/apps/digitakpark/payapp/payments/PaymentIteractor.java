package apps.digitakpark.payapp.payments;

import apps.digitakpark.payapp.model.Payment;

public interface PaymentIteractor {
    void doRetrievePaymenttList(String number, boolean mine, Long debtId);

    void doRegisterPayment(Payment payment);
}
