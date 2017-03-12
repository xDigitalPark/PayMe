package apps.digitakpark.payme.list.common;

import apps.digitakpark.payme.repositories.DebtRepository;
import apps.digitakpark.payme.model.DebtHeader;
import apps.digitakpark.payme.repositories.DebtRepositoryImpl;
import apps.digitakpark.payme.repositories.ListDebtRepository;
import apps.digitakpark.payme.repositories.ListDebtRepositoryImpl;

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
