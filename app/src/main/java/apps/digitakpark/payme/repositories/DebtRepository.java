package apps.digitakpark.payme.repositories;

import apps.digitakpark.payme.model.DebtHeader;

public interface DebtRepository {
    void deleteDebtHeader(DebtHeader debtHeader, boolean removeChilds, boolean pushEvent);
}
