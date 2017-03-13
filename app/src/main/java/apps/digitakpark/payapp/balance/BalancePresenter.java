package apps.digitakpark.payapp.balance;

import apps.digitakpark.payapp.events.BalanceEvent;
import apps.digitakpark.payapp.model.Balance;

public interface BalancePresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveBalancesAction();
    void sendArchiveDebtBalance(Balance balance);
    void receiveBalanceListResponse(BalanceEvent event);
}
