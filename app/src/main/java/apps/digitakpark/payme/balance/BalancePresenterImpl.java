package apps.digitakpark.payme.balance;

import org.greenrobot.eventbus.Subscribe;

import apps.digitakpark.payme.balance.ui.BalanceView;
import apps.digitakpark.payme.events.BalanceEvent;
import apps.digitakpark.payme.lib.events.EventBus;
import apps.digitakpark.payme.lib.events.GreenRobotEventBus;
import apps.digitakpark.payme.model.Balance;

public class BalancePresenterImpl implements BalancePresenter {

    private EventBus eventBus;
    private BalanceView view;
    private BalanceIteractor iteractor;

    public BalancePresenterImpl(BalanceView view) {
        this.view = view;
        this.iteractor = new BalanceIteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        this.eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.eventBus.unregister(this);
    }

    @Override
    public void sendRetrieveBalancesAction() {
        iteractor.doRetrieveBalances();
    }

    @Subscribe
    @Override
    public void receiveBalanceListResponse(BalanceEvent event) {
        if (view != null){

            if (event.getStatus() == BalanceEvent.BALANCE_OK) {
                view.onLoadBalanceList(event.getBalanceList());
                view.onLoadTotal(event.getTotal());
            } else if (event.getStatus() == BalanceEvent.BALANCE_DELETED_OK) {
                view.onBalanceArchived(event.getBalance());
            }
        }
    }

    @Override
    public void sendArchiveDebtBalance(Balance balance) {
        iteractor.doArchiveDebtBalance(balance);
    }
}
