package payme.apps.seven.org.payme.repositories;

import payme.apps.seven.org.payme.PaymeApplication;
import payme.apps.seven.org.payme.events.DebtDetailEvent;
import payme.apps.seven.org.payme.events.ToChargeDebtListEvent;
import payme.apps.seven.org.payme.events.ToPayDebtListEvent;
import payme.apps.seven.org.payme.lib.data.DatabaseAdapter;
import payme.apps.seven.org.payme.lib.events.EventBus;
import payme.apps.seven.org.payme.lib.events.GreenRobotEventBus;
import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public class DebtRepositoryImpl implements DebtRepository {

    private DatabaseAdapter database;
    private EventBus eventBus;

    public DebtRepositoryImpl() {
        this.database = PaymeApplication.getDatabaseInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void deleteDebtHeader(DebtHeader debtHeader, boolean removeChilds) {
        String table = !debtHeader.isMine()?DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE:DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY;
        boolean debtHeaderDeleted = this.database.deleteData(table, "number = ?", debtHeader.getNumber()),
            childsDeleted = true,
            balanceUpdated = true; // TODO UPDATE BALANCE
        if(debtHeaderDeleted==true && removeChilds == true) {
            String debtTable = !debtHeader.isMine()?DatabaseAdapter.DEBT_TABLE_TOCHARGE:DatabaseAdapter.DEBT_TABLE_TOPAY;
            String query = "DELETE FROM " + debtTable + " WHERE number = '" + debtHeader.getNumber() + "'";
            database.executeSQL(query);
            int count = database.countData(debtTable, "number = '" + debtHeader.getNumber() + "'");
            childsDeleted = (count == 0);
            if (childsDeleted) {

            }
        }

        if (removeChilds == false && debtHeaderDeleted == true) {
            DebtDetailEvent event = new DebtDetailEvent();
            event.setStatus(DebtDetailEvent.DEBT_HEADER_DELETED_OK);
            event.setMessage("OK");
            eventBus.post(event);
            return;
        }

        if (debtHeaderDeleted && childsDeleted && balanceUpdated) {
            if (!debtHeader.isMine()) {
                ToChargeDebtListEvent event = new ToChargeDebtListEvent();
                event.setStatus(ToChargeDebtListEvent.DEBT_HEADER_DELETED_OK);
                event.setMessage("OK");
                eventBus.post(event);
            } else {
                ToPayDebtListEvent event = new ToPayDebtListEvent();
                event.setStatus(ToPayDebtListEvent.DEBT_HEADER_DELETED_OK);
                event.setMessage("OK");
                eventBus.post(event);
            }

        }
    }

    private boolean updateBalanceInDeleteDebtHeaderAction() {
        return true;
    }


}
