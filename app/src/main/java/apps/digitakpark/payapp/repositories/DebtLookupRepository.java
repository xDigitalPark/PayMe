package apps.digitakpark.payapp.repositories;

import apps.digitakpark.payapp.model.Balance;
import apps.digitakpark.payapp.model.Contact;
import apps.digitakpark.payapp.model.DebtHeader;

public interface DebtLookupRepository {
    DebtHeader lookupDebtHeader(String number, boolean mine);
    Balance lookupBalance(String number);
    Contact lookupContact(String number);

}
