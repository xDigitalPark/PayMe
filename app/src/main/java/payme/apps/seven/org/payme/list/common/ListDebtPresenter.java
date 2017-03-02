package payme.apps.seven.org.payme.list.common;

import payme.apps.seven.org.payme.model.DebtHeader;

public interface ListDebtPresenter {
    void onCreate();
    void onDestroy();
    void sendRetrieveDebtHeadersAction(boolean mine);
    void sendDeleteDebtHeaderAction(DebtHeader debtHeader);
}
