package apps.digitakpark.payapp.contacts;

import apps.digitakpark.payapp.repositories.ContactRepository;
import apps.digitakpark.payapp.repositories.ContactRepositoryImpl;

class ContactsIteractorImpl implements ContactsIteractor {

    private ContactRepository repository;

    public ContactsIteractorImpl() {
        repository = new ContactRepositoryImpl();
    }

    @Override
    public void doRetrieveContactsList() {
        repository.getContactsList();
    }
}
