package apps.digitakpark.payme.create;

import apps.digitakpark.payme.model.Debt;
import apps.digitakpark.payme.model.DebtHeader;
import apps.digitakpark.payme.events.CreateDebtEvent;

public interface CreateDebtPresenter {
    void sendNewDebtCreationAction(DebtHeader debtHeader, Debt debt);
    void receiveNewDebtCreationResponse(CreateDebtEvent event);
    void sendRetrieveContactList();
    void onCreate();
    void onDestroy();
    void onResume();
    void onPaused();
}
