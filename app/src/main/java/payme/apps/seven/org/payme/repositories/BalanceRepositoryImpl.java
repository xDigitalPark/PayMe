package payme.apps.seven.org.payme.repositories;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import payme.apps.seven.org.payme.PaymeApplication;
import payme.apps.seven.org.payme.lib.data.DatabaseAdapter;
import payme.apps.seven.org.payme.model.Balance;
import payme.apps.seven.org.payme.events.BalanceEvent;
import payme.apps.seven.org.payme.lib.events.EventBus;
import payme.apps.seven.org.payme.lib.events.GreenRobotEventBus;

public class BalanceRepositoryImpl implements  BalanceRepository {

    private EventBus eventBus;
    private DatabaseAdapter database;

    public BalanceRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.database = PaymeApplication.getDatabaseInstance();
    }

    @Override
    public void getBalances() {
        String query = "SELECT number, name, currency, mytotal, partytotal, total, mine " +
                "FROM " + DatabaseAdapter.BALANCE_TABLE;
        Cursor cursor = database.retrieveData(query);
        if (cursor != null) {
            List<Balance> balanceList = new ArrayList<>();
            Balance balance = null;
            Double total = 0D;
            while (cursor.moveToNext()) {
                balance = new Balance(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getString(cursor.getColumnIndex("currency")),
                        cursor.getDouble(cursor.getColumnIndex("partytotal")),
                        cursor.getDouble(cursor.getColumnIndex("mytotal")),
                        cursor.getDouble(cursor.getColumnIndex("total")),
                        cursor.getInt(cursor.getColumnIndex("mine")) != 0
                );
                balanceList.add(balance);
                total = total + balance.getTotal();
            }
            BalanceEvent event = new BalanceEvent();
            event.setStatus(BalanceEvent.BALANCE_OK);
            event.setMessage("OK");
            event.setBalanceList(balanceList);
            event.setTotal(total);
            eventBus.post(event);
        }
    }

    @Override
    public void closeBalance(String number, boolean mine) {
        // TODO CLOSE BALANCE IMPL
    }

    @Override
    public void deleteBalance(Balance balance) {
        boolean deleted = this.database.deleteData(DatabaseAdapter.BALANCE_TABLE, "number = ?", balance.getNumber());
        if (deleted) {
            BalanceEvent event = new BalanceEvent();
            event.setStatus(BalanceEvent.BALANCE_DELETED_OK);
            event.setMessage("OK");
            event.setBalance(balance);
            eventBus.post(event);
        }
    }
}
