package apps.digitakpark.payapp.payments;
import org.greenrobot.eventbus.Subscribe;

import apps.digitakpark.payapp.events.PaymentEvent;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.Payment;
import apps.digitakpark.payapp.payments.ui.PaymentView;

public class PaymentsPresenterImpl implements PaymentsPresenter {
    private EventBus event;
    private PaymentView view;
    private PaymentIteractor iteractor;

    
    public PaymentsPresenterImpl(PaymentView view) {
        this.event = GreenRobotEventBus.getInstance();
        this.view = view;
        this.iteractor = new PaymentIteractorImpl();
    }

    @Override
    public void onCreate() {
        event.register(this);
    }

    @Override
    public void onDestroy() {
        event.unregister(this);
    }

    @Override
    public void sendRetrievePaymentsAction(String number, boolean mine, Long debtId) {
        iteractor.doRetrievePaymenttList(number, mine, debtId);
    }

    @Subscribe
    @Override
    public void receiveEvent(PaymentEvent event) {
        if (view == null)
            return;
        switch (event.getStatus()) {
            case PaymentEvent.PAYMENT_LIST_OK:
                view.onLoadPaymentList(event.getPaymentList());
                break;
            case PaymentEvent.PAYMENT_CREATED_OK:
                view.onPaymentCreated(event.getTotal());
                break;
        }
    }

    @Override
    public void sendRegisterPayment(Payment payment, Double currentTotal, Double newTotal) {
        iteractor.doRegisterPayment(payment, currentTotal, newTotal);
    }
}
