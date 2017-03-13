package apps.digitakpark.payapp.create;

import org.greenrobot.eventbus.Subscribe;

import apps.digitakpark.payapp.create.ui.CreateDebtView;
import apps.digitakpark.payapp.events.CreateDebtEvent;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;

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
