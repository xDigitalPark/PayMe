package apps.digitakpark.payapp.list.tocharge;

import org.greenrobot.eventbus.Subscribe;

import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.list.common.ListDebtIteractorImpl;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.events.ToChargeDebtListEvent;
import apps.digitakpark.payapp.list.common.ListDebtIteractor;
import apps.digitakpark.payapp.list.common.ListDebtPresenter;
import apps.digitakpark.payapp.list.common.ui.ListDebtView;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;

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
