package apps.digitakpark.payme.list.common.ui;

import java.util.List;

import apps.digitakpark.payme.model.DebtHeader;

public interface ListDebtView {
    int TO_CHARGE_CLOSE_DEBT = 0x01;
    int TO_PAY_CLOSE_DEBT = 0x02;
    void onLoadDebtHeaderList(List<DebtHeader> debtHeaderList);
    void onLoadTotal(Double total);
    void onDebtHeaderSelected(DebtHeader debtHeader);
    void onHeaderDeleted();
}
