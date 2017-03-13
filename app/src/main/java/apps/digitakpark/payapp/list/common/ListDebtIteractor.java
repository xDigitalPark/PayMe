package apps.digitakpark.payapp.list.common;

import apps.digitakpark.payapp.model.DebtHeader;

public interface ListDebtIteractor {
    void doRetrieveListHeader(boolean mine);
    void doDeleteDebtHeader(DebtHeader debtHeader);
}
