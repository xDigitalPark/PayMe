package payme.apps.seven.org.payme.list.topay;

import org.greenrobot.eventbus.Subscribe;

import payme.apps.seven.org.payme.events.ToPayDebtListEvent;
import payme.apps.seven.org.payme.list.common.ListDebtIteractor;
import payme.apps.seven.org.payme.list.common.ListDebtIteractorImpl;
import payme.apps.seven.org.payme.list.common.ListDebtPresenter;
import payme.apps.seven.org.payme.list.common.ui.ListDebtView;
import payme.apps.seven.org.payme.lib.events.EventBus;
import payme.apps.seven.org.payme.lib.events.GreenRobotEventBus;
import payme.apps.seven.org.payme.model.DebtHeader;

public class ToPayDebtPresenterImpl implements ListDebtPresenter {

    private ListDebtView view;
    private ListDebtIteractor iteractor;
    private EventBus eventBus;

    public ToPayDebtPresenterImpl(ListDebtView view) {
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
    public void receiveDebtHeadersListResponse(ToPayDebtListEvent event) {
        switch (event.getStatus())
        {
            case ToPayDebtListEvent.DEBT_LIST_OK:
                if (view != null) {
                    view.onLoadDebtHeaderList(event.getDebtHeader());
                    view.onLoadTotal(event.getTotal());
                }
        }
    }

    @Override
    public void sendDeleteDebtHeaderAction(DebtHeader debtHeader) {

    }
}
