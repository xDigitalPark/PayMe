package apps.digitakpark.payapp.create;

import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.events.CreateDebtEvent;

public interface CreateDebtPresenter {
    void sendNewDebtCreationAction(DebtHeader debtHeader, Debt debt);
    void sendEditDebtAction(DebtHeader debtHeader, Debt debt, Double editPreTotal);
    void receiveNewDebtCreationResponse(CreateDebtEvent event);
    void sendRetrieveContactList();
    void onCreate();
    void onDestroy();
    void onResume();
    void onPaused();
}
