package apps.digitakpark.payapp.payments.ui;

import android.os.Bundle;

import java.util.List;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.Payment;

public interface PaymentView {
    void prepopActivity(Bundle extras);
    void onLoadPaymentList(List<Payment> paymentList);
    void doPayment(Double total);
    void onPaymentCreated(Double total);
    void onDebtDeteled();
}
