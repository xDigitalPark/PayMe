package payme.apps.seven.org.payme.balance;

import payme.apps.seven.org.payme.events.BalanceEvent;
import payme.apps.seven.org.payme.model.Balance;

public interface BalancePresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveBalancesAction();
    void sendArchiveDebtBalance(Balance balance);
    void receiveBalanceListResponse(BalanceEvent event);
}
