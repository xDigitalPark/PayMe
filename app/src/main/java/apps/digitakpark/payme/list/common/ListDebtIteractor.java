package apps.digitakpark.payme.list.common;

import apps.digitakpark.payme.model.DebtHeader;

public interface ListDebtIteractor {
    void doRetrieveListHeader(boolean mine);
    void doDeleteDebtHeader(DebtHeader debtHeader);
}
