package apps.digitakpark.payme.list.tocharge;

import org.greenrobot.eventbus.Subscribe;

import apps.digitakpark.payme.lib.events.EventBus;
import apps.digitakpark.payme.list.common.ListDebtIteractorImpl;
import apps.digitakpark.payme.model.DebtHeader;
import apps.digitakpark.payme.events.ToChargeDebtListEvent;
import apps.digitakpark.payme.list.common.ListDebtIteractor;
import apps.digitakpark.payme.list.common.ListDebtPresenter;
import apps.digitakpark.payme.list.common.ui.ListDebtView;
import apps.digitakpark.payme.lib.events.GreenRobotEventBus;

public class ListDebtPresenterImpl implements ListDebtPresenter {

    private ListDebtView view;
    private ListDebtIteractor iteractor;
    private EventBus eventBus;

    public ListDebtPresenterImpl(ListDebtView view) {
        this.view = view;
        this.iteractor = new ListDebtIteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void sendRetrieveDebtHeadersAction(boolean mine) {
        iteractor.doRetrieveListHeader(mine);
    }

    @Subscribe
    public void receiveDebtHeadersListResponse(ToChargeDebtListEvent event) {
        if (view == null) return;
        switch (event.getStatus())
        {
            case ToChargeDebtListEvent.DEBT_LIST_OK:
                view.onLoadDebtHeaderList(event.getDebtHeader());
                view.onLoadTotal(event.getTotal());
                break;
            case ToChargeDebtListEvent.DEBT_HEADER_DELETED_OK:
                view.onHeaderDeleted();
                break;

        }
    }

    @Override
    public void sendDeleteDebtHeaderAction(DebtHeader debtHeader) {
        iteractor.doDeleteDebtHeader(debtHeader);
    }
}
