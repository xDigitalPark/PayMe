package payme.apps.seven.org.payme.list.common;

import payme.apps.seven.org.payme.model.DebtHeader;
import payme.apps.seven.org.payme.repositories.DebtRepository;
import payme.apps.seven.org.payme.repositories.DebtRepositoryImpl;
import payme.apps.seven.org.payme.repositories.ListDebtRepository;
import payme.apps.seven.org.payme.repositories.ListDebtRepositoryImpl;

public class ListDebtIteractorImpl implements ListDebtIteractor {

    private ListDebtRepository repository;
    private DebtRepository debtRepository;

    public ListDebtIteractorImpl() {
        this.repository = new ListDebtRepositoryImpl();
        this.debtRepository = new DebtRepositoryImpl();
    }

    @Override
    public void doRetrieveListHeader(boolean mine) {
        repository.getDebtHeaders(mine);
    }

    @Override
    public void doDeleteDebtHeader(DebtHeader debtHeader) {
        debtRepository.deleteDebtHeader(debtHeader, true, true);
    }
}
