package payme.apps.seven.org.payme.list.common;

import payme.apps.seven.org.payme.model.DebtHeader;

public interface ListDebtIteractor {
    void doRetrieveListHeader(boolean mine);
    void doDeleteDebtHeader(DebtHeader debtHeader);
}
