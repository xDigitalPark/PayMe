package apps.digitakpark.payapp.list.common;

import apps.digitakpark.payapp.model.DebtHeader;

public interface ListDebtPresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveDebtHeadersAction(boolean mine);
    void sendDeleteDebtHeaderAction(DebtHeader debtHeader);
}
