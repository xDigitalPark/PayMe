package payme.apps.seven.org.payme.repositories;

import payme.apps.seven.org.payme.model.Balance;
import payme.apps.seven.org.payme.model.Contact;
import payme.apps.seven.org.payme.model.DebtHeader;

public interface DebtLookupRepository {
    DebtHeader lookupDebtHeader(String number, boolean mine);
    Balance lookupBalance(String number);
    Contact lookupContact(String number);

}
