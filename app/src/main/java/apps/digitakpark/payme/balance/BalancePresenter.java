package apps.digitakpark.payme.balance;

import apps.digitakpark.payme.events.BalanceEvent;
import apps.digitakpark.payme.model.Balance;

public interface BalancePresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveBalancesAction();
    void sendArchiveDebtBalance(Balance balance);
    void receiveBalanceListResponse(BalanceEvent event);
}
