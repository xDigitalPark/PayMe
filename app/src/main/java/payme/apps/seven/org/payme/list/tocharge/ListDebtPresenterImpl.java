package payme.apps.seven.org.payme.list.tocharge;

import org.greenrobot.eventbus.Subscribe;
import payme.apps.seven.org.payme.events.ToChargeDebtListEvent;
import payme.apps.seven.org.payme.list.common.ListDebtIteractor;
import payme.apps.seven.org.payme.list.common.ListDebtIteractorImpl;
import payme.apps.seven.org.payme.list.common.ListDebtPresenter;
import payme.apps.seven.org.payme.list.common.ui.ListDebtView;
import payme.apps.seven.org.payme.lib.events.EventBus;
import payme.apps.seven.org.payme.lib.events.GreenRobotEventBus;
import payme.apps.seven.org.payme.model.DebtHeader;

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
