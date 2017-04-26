package apps.digitakpark.payapp.payments;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.model.Payment;
import apps.digitakpark.payapp.repositories.DebtDetailRepositoryImpl;
import apps.digitakpark.payapp.repositories.DebtDetailRespository;
import apps.digitakpark.payapp.repositories.DebtRepository;
import apps.digitakpark.payapp.repositories.DebtRepositoryImpl;
import apps.digitakpark.payapp.repositories.PaymentRepository;
import apps.digitakpark.payapp.repositories.PaymentRepositoryImpl;

class PaymentIteractorImpl implements PaymentIteractor {

    private PaymentRepository repository;
    private DebtRepository debtRepository;
    private DebtDetailRespository debtDetailRespository;

    public PaymentIteractorImpl() {
        this.repository = new PaymentRepositoryImpl();
        this.debtRepository = new DebtRepositoryImpl();
        this.debtDetailRespository = new DebtDetailRepositoryImpl();
    }

    @Override
    public void doRetrievePaymenttList(String number, boolean mine, Long debtId) {
        this.repository.getPayments(number, mine, debtId);
    }

    @Override
    public void doRegisterPayment(Payment payment, Double currentTotal, Double newTotal) {
        this.repository.registerPayment(payment, currentTotal, newTotal);
    }

    @Override
    public void doCloseDebtHeader(Debt debt) {
        this.debtDetailRespository.deleteDebt(debt, true);
    }

    @Override
    public void doCloseDebtHeader(DebtHeader debtHeader) {
        this.debtRepository.deleteDebtHeader(debtHeader, true, true, true);
    }
}
