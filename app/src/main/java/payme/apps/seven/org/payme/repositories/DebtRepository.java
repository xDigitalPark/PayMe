package payme.apps.seven.org.payme.repositories;

import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public interface DebtRepository {
    void deleteDebtHeader(DebtHeader debtHeader, boolean removeChilds, boolean pushEvent);
}
