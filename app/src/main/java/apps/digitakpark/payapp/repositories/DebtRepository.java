package apps.digitakpark.payapp.repositories;

import apps.digitakpark.payapp.model.DebtHeader;

public interface DebtRepository {
    void deleteDebtHeader(DebtHeader debtHeader, boolean removeChilds, boolean pushEvent);
    void changeDebtContactLink(String currentNumber, boolean mine, String number, String name);
}
