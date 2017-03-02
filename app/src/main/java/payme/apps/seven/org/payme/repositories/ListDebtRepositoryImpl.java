package payme.apps.seven.org.payme.repositories;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import payme.apps.seven.org.payme.PaymeApplication;
import payme.apps.seven.org.payme.lib.data.DatabaseAdapter;
import payme.apps.seven.org.payme.events.ToChargeDebtListEvent;
import payme.apps.seven.org.payme.events.ToPayDebtListEvent;
import payme.apps.seven.org.payme.model.DebtHeader;
import payme.apps.seven.org.payme.lib.events.EventBus;
import payme.apps.seven.org.payme.lib.events.GreenRobotEventBus;

public class ListDebtRepositoryImpl implements ListDebtRepository {

    private EventBus eventBus;
    private DatabaseAdapter database;

    public ListDebtRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.database = PaymeApplication.getDatabaseInstance();
    }

    @Override
    public void getDebtHeaders(boolean mine) {
        String table = (!mine)?DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE:DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY;
        String query = "SELECT name, number, currency, total, mine FROM " + table +" " +
                "WHERE mine = " + (mine?1:0);
        Cursor cursor = database.retrieveData(query);
        if (cursor != null) {
            List<DebtHeader> debtHeaderList = new ArrayList<>();
            Double total = 0D;
            DebtHeader debtHeader = null;
            while(cursor.moveToNext()) {
                debtHeader = new DebtHeader(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getString(cursor.getColumnIndex("currency")),
                        cursor.getDouble(cursor.getColumnIndex("total")),
                        cursor.getInt(cursor.getColumnIndex("mine")) != 0
                );
                debtHeaderList.add(debtHeader);
                total += debtHeader.getTotal();
            }
            // push event with data.
            if (!mine) {
                ToChargeDebtListEvent event = new ToChargeDebtListEvent();
                event.setStatus(ToChargeDebtListEvent.DEBT_LIST_OK);
                event.setMessage("OK");
                event.setDebtHeader(debtHeaderList);
                event.setTotal(total);
                eventBus.post(event);
            } else {
                ToPayDebtListEvent event = new ToPayDebtListEvent();
                event.setStatus(ToPayDebtListEvent.DEBT_LIST_OK);
                event.setMessage("OK");
                event.setDebtHeader(debtHeaderList);
                event.setTotal(total);
                eventBus.post(event);
            }
        }
    }
}
