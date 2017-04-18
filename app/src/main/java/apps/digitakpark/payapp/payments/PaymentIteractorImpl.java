package apps.digitakpark.payapp.payments;

import apps.digitakpark.payapp.model.Payment;
import apps.digitakpark.payapp.repositories.PaymentRepository;
import apps.digitakpark.payapp.repositories.PaymentRepositoryImpl;

class PaymentIteractorImpl implements PaymentIteractor {

    private PaymentRepository repository;

    public PaymentIteractorImpl() {
        this.repository = new PaymentRepositoryImpl();

    }

    @Override
    public void doRetrievePaymenttList(String number, boolean mine, Long debtId) {
        this.repository.getPayments(number, mine, debtId);
    }

    @Override
    public void doRegisterPayment(Payment payment) {
        this.repository.registerPayment(payment);
    }
}
