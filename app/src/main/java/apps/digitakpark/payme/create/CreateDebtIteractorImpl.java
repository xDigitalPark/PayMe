package apps.digitakpark.payme.create;

import apps.digitakpark.payme.model.Debt;
import apps.digitakpark.payme.model.DebtHeader;
import apps.digitakpark.payme.repositories.ContactRepository;
import apps.digitakpark.payme.repositories.ContactRepositoryImpl;
import apps.digitakpark.payme.repositories.CreateDebtRepositoryImpl;
import apps.digitakpark.payme.repositories.CreateDebtRepository;

public class CreateDebtIteractorImpl implements CreateDebtIteractor {

    private CreateDebtRepository repository;
    private ContactRepository contactRepository;

    public CreateDebtIteractorImpl() {
        this.repository = new CreateDebtRepositoryImpl();
        this.contactRepository = new ContactRepositoryImpl();
    }

    @Override
    public void doCreateDebt(DebtHeader debtHeader, Debt debt) {
        repository.createDebt(debtHeader, debt);
    }

    @Override
    public void doRetrieveContactList() {
        contactRepository.getContacts();
    }
}
