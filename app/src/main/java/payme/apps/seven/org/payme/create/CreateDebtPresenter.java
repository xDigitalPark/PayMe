package payme.apps.seven.org.payme.create;

import payme.apps.seven.org.payme.events.DebtEvent;
import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public interface CreateDebtPresenter {
    void sendNewDebtCreationAction(DebtHeader debtHeader, Debt debt);
    void receiveNewDebtCreationResponse(DebtEvent event);

    void onCreate();
    void onDestroy();
    void onResume();
    void onPaused();
}
