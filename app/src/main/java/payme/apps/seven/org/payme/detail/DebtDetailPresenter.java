package payme.apps.seven.org.payme.detail;

import payme.apps.seven.org.payme.events.DebtDetailEvent;
import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public interface DebtDetailPresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveDebtsAction(String number, boolean mine);
    void sendCloseDebtAction(Debt debt);
    void sendDeleteDebtAction(Debt debt);
    void sendDeleteDetHeaderAction(DebtHeader debtHeader);
    void receiveEvent(DebtDetailEvent event);
}
