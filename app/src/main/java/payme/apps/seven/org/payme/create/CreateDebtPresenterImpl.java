package payme.apps.seven.org.payme.create;

import org.greenrobot.eventbus.Subscribe;

import payme.apps.seven.org.payme.events.CreateDebtEvent;
import payme.apps.seven.org.payme.create.ui.CreateDebtView;
import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;
import payme.apps.seven.org.payme.lib.events.EventBus;
import payme.apps.seven.org.payme.lib.events.GreenRobotEventBus;

public class CreateDebtPresenterImpl implements CreateDebtPresenter {

    private CreateDebtView view;
    private CreateDebtIteractor interactor;
    private EventBus eventBus;

    public CreateDebtPresenterImpl(CreateDebtView view) {
        this.view = view;
        this.interactor = new CreateDebtIteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void onResume() {
        // will be used with firebase
    }

    @Override
    public void onPaused() {
        // will be used with firebase
    }

    @Override
    public void sendNewDebtCreationAction(DebtHeader debtHeader, Debt debt) {
        if (view != null) {
            view.hideKeyboard();
            view.showProgressBar();
            interactor.doCreateDebt(debtHeader, debt);
        }
    }

    @Override
    @Subscribe
    public void receiveNewDebtCreationResponse(CreateDebtEvent event) {
        switch (event.getStatus()) {
            case CreateDebtEvent.DEBT_CREATED:
                if (view != null) {
                    view.hideProgressBar();
                    view.onDebtCreated(event);
                }
                break;
            case CreateDebtEvent.CONTACT_LIST_OK:
                if (view != null) {
                    view.onContactListLoaded(event.getContactList());
                }
        }
    }

    @Override
    public void sendRetrieveContactList() {
        interactor.doRetrieveContactList();
    }
}
