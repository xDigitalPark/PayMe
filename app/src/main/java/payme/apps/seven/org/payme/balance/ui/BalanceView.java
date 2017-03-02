package payme.apps.seven.org.payme.balance.ui;

import java.util.List;
import payme.apps.seven.org.payme.model.Balance;

public interface BalanceView {
    int CLOSE_DEBT = 0x01;
    void onLoadBalanceList(List<Balance> balanceList);
    void onLoadTotal(Double total);
    void onBalanceSelected(Balance balance);
    void onBalanceArchived(Balance balance);
}
