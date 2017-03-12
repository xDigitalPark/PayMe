package payme.apps.seven.org.payme.create;

import payme.apps.seven.org.payme.events.CreateDebtEvent;
import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public interface CreateDebtPresenter {
    void sendNewDebtCreationAction(DebtHeader debtHeader, Debt debt);
    void receiveNewDebtCreationResponse(CreateDebtEvent event);
    void sendRetrieveContactList();
    void onCreate();
    void onDestroy();
    void onResume();
    void onPaused();
}
