package apps.digitakpark.payapp.repositories;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.events.ToChargeDebtListEvent;
import apps.digitakpark.payapp.events.ToPayDebtListEvent;
import apps.digitakpark.payapp.lib.data.DatabaseAdapter;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.DebtHeader;

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
