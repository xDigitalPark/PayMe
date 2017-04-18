package apps.digitakpark.payapp.repositories;

import apps.digitakpark.payapp.model.Payment;

public interface PaymentRepository {
    void getPayments(String number, boolean mine, Long debtId);
    void registerPayment(Payment payment);
}
