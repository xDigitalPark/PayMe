package apps.digitakpark.payme.repositories;

import apps.digitakpark.payme.model.Balance;
import apps.digitakpark.payme.model.Contact;
import apps.digitakpark.payme.model.DebtHeader;

public interface DebtLookupRepository {
    DebtHeader lookupDebtHeader(String number, boolean mine);
    Balance lookupBalance(String number);
    Contact lookupContact(String number);

}
