package apps.digitakpark.payapp.payments;

import apps.digitakpark.payapp.events.DebtDetailEvent;
import apps.digitakpark.payapp.events.PaymentEvent;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.model.Payment;

public interface PaymentsPresenter {
    void onCreate();
    void onDestroy();
    void sendRetrievePaymentsAction(String number, boolean mine, Long debtId);
    void receiveEvent(PaymentEvent event);
    void sendDeleteDebtAction(Debt debt);
    void sendCloseDebt(DebtHeader debtHeader);
    void sendRegisterPayment(Payment payment, Double currentTotal, Double newTotal);
}