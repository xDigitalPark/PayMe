package apps.digitakpark.payapp.create;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.repositories.ContactRepository;
import apps.digitakpark.payapp.repositories.ContactRepositoryImpl;
import apps.digitakpark.payapp.repositories.CreateDebtRepositoryImpl;
import apps.digitakpark.payapp.repositories.CreateDebtRepository;

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
