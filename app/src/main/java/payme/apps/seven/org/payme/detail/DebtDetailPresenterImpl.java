package payme.apps.seven.org.payme.detail;

import org.greenrobot.eventbus.Subscribe;

import payme.apps.seven.org.payme.detail.ui.DebtDetailView;
import payme.apps.seven.org.payme.events.DebtDetailEvent;
import payme.apps.seven.org.payme.lib.events.EventBus;
import payme.apps.seven.org.payme.lib.events.GreenRobotEventBus;
import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public class DebtDetailPresenterImpl implements DebtDetailPresenter {
    private EventBus event;
    private DebtDetailView view;
    private DebtDetailIteractor iteractor;

    public DebtDetailPresenterImpl(DebtDetailView view) {
        this.event = GreenRobotEventBus.getInstance();
        this.view = view;
        this.iteractor = new DebtDetailIteractorImpl();
    }

    @Override
    public void onCreate() {
        event.register(this);
    }

    @Override
    public void onDestroy() {
        event.unregister(this);
    }

    @Override
    public void sendRetrieveDebtsAction(String number, boolean mine) {
        iteractor.doRetrieveDebtList(number, mine);
    }

    @Subscribe
    @Override
    public void receiveEvent(DebtDetailEvent event) {
        if (view == null)
            return;

        switch (event.getStatus()) {
            case DebtDetailEvent.DEBT_DETAIL_OK:
                view.onLoadDebtList(event.getDebtList());
                view.onLoadTotal(event.getTotal());
                break;
            case DebtDetailEvent.DEBT_DELETED_OK:
                view.onDebtDeleted(event.getDebtId());
                break;
            case DebtDetailEvent.DEBT_HEADER_DELETED_OK:
                view.onDebtHeaderDeleted();
                break;
        }
    }

    @Override
    public void sendCloseDebtAction(Debt debt) {
        iteractor.doCloseDebt(debt);
    }

    @Override
    public void sendDeleteDebtAction(Debt debt) {
        iteractor.doDeleteDebt(debt);
    }

    @Override
    public void sendDeleteDetHeaderAction(DebtHeader debtHeader) {
        iteractor.doDeleteDebtHeader(debtHeader);
    }
}
