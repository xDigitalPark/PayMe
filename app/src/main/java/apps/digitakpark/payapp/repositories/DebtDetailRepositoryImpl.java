package apps.digitakpark.payapp.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.events.DebtDetailEvent;
import apps.digitakpark.payapp.events.PaymentEvent;
import apps.digitakpark.payapp.lib.data.DatabaseAdapter;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.Balance;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.model.Payment;

public class DebtDetailRepositoryImpl implements DebtDetailRespository {

    private DatabaseAdapter database;
    private EventBus eventBus;
    private DebtLookupRepository debtLookupRepository;

    public DebtDetailRepositoryImpl() {
        this.database = PaymeApplication.getDatabaseInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
        this.debtLookupRepository = new DebtLookupRepositoryImpl();
    }

    @Override
    public void getDebts(String number, boolean mine) {
        String table = (!mine)?DatabaseAdapter.DEBT_TABLE_TOCHARGE:DatabaseAdapter.DEBT_TABLE_TOPAY;
        String query = "SELECT id, concept, number, currency, total, date, mine, date_limit FROM " + table +" " +
                "WHERE mine = " + (mine?1:0) + " and number = '" + number + "'";
        Cursor cursor = database.retrieveData(query);
        List<Debt> debtList = new ArrayList<>();
        if (cursor != null) {
            Debt debt = null;
            Double total = 0D;
            while(cursor.moveToNext()) {
                debt = new Debt(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getString(cursor.getColumnIndex("concept")),
                        cursor.getString(cursor.getColumnIndex("currency")),
                        cursor.getLong(cursor.getColumnIndex("date")),
                        cursor.getDouble(cursor.getColumnIndex("total")),
                        cursor.getInt(cursor.getColumnIndex("mine")) != 0,
                        cursor.getLong(cursor.getColumnIndex("date_limit"))
                );
                debtList.add(debt);
                total = total + debt.getTotal();
            }
            DebtDetailEvent event = new DebtDetailEvent();
            event.setStatus(DebtDetailEvent.DEBT_DETAIL_OK);
            event.setMessage("OK");
            event.setDebtList(debtList);
            event.setTotal(total);
            eventBus.post(event);

        }
    }

    @Override
    public void deleteDebt(Debt debt) {
        this.deleteDebt(debt, false);
    }

    @Override
    public void deleteDebt(Debt debt, boolean fromPayment) {
        String table = !debt.isMine()?DatabaseAdapter.DEBT_TABLE_TOCHARGE:DatabaseAdapter.DEBT_TABLE_TOPAY;
        int mine = debt.isMine()?1:0;
        boolean debtDeleted = database.deleteData(table, "id = ?", debt.getId().toString()),
                paymentsDeleted = database.deleteData(DatabaseAdapter.PAYMENTS_TABLE, "debt_id = ? AND mine = ?", new String[] {debt.getId().toString(), ""+mine }),
                debtHeaderUpdated = updateDebtHeader(debt),
                balanceUpdated = updateBalance(debt);

        if (debtDeleted && debtHeaderUpdated && balanceUpdated && paymentsDeleted) {
            if (!fromPayment) {
                DebtDetailEvent event = new DebtDetailEvent();
                event.setStatus(DebtDetailEvent.DEBT_DELETED_OK);
                event.setMessage("OK");
                event.setDebtId(debt.getId());
                eventBus.post(event);
            } else {
                PaymentEvent event = new PaymentEvent();
                event.setStatus(PaymentEvent.DEBT_DELETE_OK);
                event.setMessage("OK");
                eventBus.post(event);
            }
        } else {
            // TODO: send Errors
        }
    }

    private boolean updateDebtHeader(Debt debt) {
        String table = !debt.isMine()?DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE:DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY;
        DebtHeader debtHeader = debtLookupRepository.lookupDebtHeader(debt.getNumber(), debt.isMine());
        if (debtHeader != null) {
            Double debtHeaderTotal = debtHeader.getTotal();
            debtHeaderTotal = debtHeaderTotal - debt.getTotal();
            ContentValues data = new ContentValues();
            data.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_TOTAL, debtHeaderTotal);
            return this.database.updateData(table, data, "number = ? ", debtHeader.getNumber());

        }
        return false;
    }

    private boolean updateBalance(Debt debt) {
        Balance balance = debtLookupRepository.lookupBalance(debt.getNumber());
        if (balance != null) {
            if (debt.isMine()) {
                balance.setMyTotal(balance.getMyTotal() - debt.getTotal());
            } else {
                balance.setPartyTotal(balance.getPartyTotal() - debt.getTotal());
            }
            balance.setTotal(balance.getPartyTotal() - balance.getMyTotal());
            ContentValues data = new ContentValues();
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_MY_TOTAL, balance.getMyTotal());
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_PARTY_TOTAL, balance.getPartyTotal());
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_TOTAL, balance.getTotal());
            return this.database.updateData(DatabaseAdapter.BALANCE_TABLE, data, "number = ?", debt.getNumber());
        }
        return false;
    }

    private void sendEvent(Long id) {
        DebtDetailEvent event = new DebtDetailEvent();
        event.setStatus(DebtDetailEvent.DEBT_DELETED_OK);
        event.setMessage("OK");
        event.setDebtId(id);
        eventBus.post(event);
    }
}
