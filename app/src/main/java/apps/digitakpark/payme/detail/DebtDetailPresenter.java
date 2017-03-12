package apps.digitakpark.payme.detail;

import apps.digitakpark.payme.events.DebtDetailEvent;
import apps.digitakpark.payme.model.Debt;
import apps.digitakpark.payme.model.DebtHeader;

public interface DebtDetailPresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveDebtsAction(String number, boolean mine);
    void sendCloseDebtAction(Debt debt);
    void sendDeleteDebtAction(Debt debt);
    void sendDeleteDetHeaderAction(DebtHeader debtHeader);
    void receiveEvent(DebtDetailEvent event);
}
