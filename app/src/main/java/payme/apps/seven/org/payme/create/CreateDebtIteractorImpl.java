package payme.apps.seven.org.payme.create;

import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;
import payme.apps.seven.org.payme.repositories.ContactRepository;
import payme.apps.seven.org.payme.repositories.ContactRepositoryImpl;
import payme.apps.seven.org.payme.repositories.CreateDebtRepository;
import payme.apps.seven.org.payme.repositories.CreateDebtRepositoryImpl;

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
