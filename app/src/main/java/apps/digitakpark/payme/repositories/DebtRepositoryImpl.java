package apps.digitakpark.payme.repositories;

import android.content.ContentValues;

import apps.digitakpark.payme.PaymeApplication;
import apps.digitakpark.payme.events.DebtDetailEvent;
import apps.digitakpark.payme.lib.data.DatabaseAdapter;
import apps.digitakpark.payme.lib.events.EventBus;
import apps.digitakpark.payme.model.DebtHeader;
import apps.digitakpark.payme.events.ToChargeDebtListEvent;
import apps.digitakpark.payme.events.ToPayDebtListEvent;
import apps.digitakpark.payme.lib.events.GreenRobotEventBus;
import apps.digitakpark.payme.model.Balance;

public class DebtRepositoryImpl implements DebtRepository {

    private DatabaseAdapter database;
    private EventBus eventBus;
    private DebtLookupRepository lookupRepository;

    public DebtRepositoryImpl() {
        this.database = PaymeApplication.getDatabaseInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
        this.lookupRepository = new DebtLookupRepositoryImpl();
    }

    @Override
    public void deleteDebtHeader(DebtHeader debtHeader, boolean removeChilds, boolean pushEvent) {
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
                balanceUpdated = updateBalanceInDeleteDebtHeaderAction(debtHeader, pushEvent);
            }
        }

        if (removeChilds == false && debtHeaderDeleted == true && pushEvent == true) {
            DebtDetailEvent event = new DebtDetailEvent();
            event.setStatus(DebtDetailEvent.DEBT_HEADER_DELETED_OK);
            event.setMessage("OK");
            eventBus.post(event);
            return;
        }

        if (debtHeaderDeleted && childsDeleted && balanceUpdated && pushEvent == true) {
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

    private boolean updateBalanceInDeleteDebtHeaderAction(DebtHeader debtHeader, boolean pushEvent) {
        Balance balance = lookupRepository.lookupBalance(debtHeader.getNumber());
        if (!debtHeader.isMine()) {
            balance.setPartyTotal(balance.getPartyTotal() - debtHeader.getTotal());
        } else {
            balance.setMyTotal(balance.getMyTotal() - debtHeader.getTotal());
        }
        balance.setTotal(balance.getPartyTotal()- balance.getMyTotal());
        if (balance.getTotal() != 0D) {
            ContentValues data = new ContentValues();
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_MY_TOTAL, balance.getMyTotal());
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_PARTY_TOTAL, balance.getPartyTotal());
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_TOTAL, balance.getTotal());
            return database.updateData(DatabaseAdapter.BALANCE_TABLE, data, "number = ?", debtHeader.getNumber());
        } else {
            if (pushEvent == true) {
                return database.deleteData(DatabaseAdapter.BALANCE_TABLE, "number = ?", debtHeader.getNumber());
            }
        }
        return true;
    }


}
