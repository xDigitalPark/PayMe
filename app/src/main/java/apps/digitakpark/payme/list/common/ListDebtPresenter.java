package apps.digitakpark.payme.list.common;

import apps.digitakpark.payme.model.DebtHeader;

public interface ListDebtPresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveDebtHeadersAction(boolean mine);
    void sendDeleteDebtHeaderAction(DebtHeader debtHeader);
}
