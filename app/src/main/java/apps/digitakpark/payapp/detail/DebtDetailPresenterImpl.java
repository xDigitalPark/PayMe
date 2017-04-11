package apps.digitakpark.payapp.detail;

import org.greenrobot.eventbus.Subscribe;

import apps.digitakpark.payapp.detail.ui.DebtDetailView;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.events.DebtDetailEvent;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;

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
            case DebtDetailEvent.DEBT_DETAIL_RELINKED_OK:
                view.updateContactInfo(event.getData());
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

    @Override
    public void sendChangeContactLink(String currentNumber, boolean mine, String number, String name) {
        iteractor.doChangeContactLink(currentNumber, mine, number, name);
    }
}
