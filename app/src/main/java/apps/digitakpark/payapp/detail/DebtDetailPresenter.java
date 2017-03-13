package apps.digitakpark.payapp.detail;

import apps.digitakpark.payapp.events.DebtDetailEvent;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;

public interface DebtDetailPresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveDebtsAction(String number, boolean mine);
    void sendCloseDebtAction(Debt debt);
    void sendDeleteDebtAction(Debt debt);
    void sendDeleteDetHeaderAction(DebtHeader debtHeader);
    void receiveEvent(DebtDetailEvent event);
}
