package payme.apps.seven.org.payme.repositories;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import payme.apps.seven.org.payme.PaymeApplication;
import payme.apps.seven.org.payme.lib.data.DatabaseAdapter;
import payme.apps.seven.org.payme.model.Balance;
import payme.apps.seven.org.payme.model.Contact;
import payme.apps.seven.org.payme.model.DebtHeader;

public class DebtLookupRepositoryImpl implements DebtLookupRepository {
    private DatabaseAdapter database;

    public DebtLookupRepositoryImpl() {
        this.database = PaymeApplication.getDatabaseInstance();
    }

    @Override
    public DebtHeader lookupDebtHeader(String number, boolean mine) {
        String table = (!mine)?DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE:DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY;
        String query = "SELECT name, number, currency, total, mine FROM " + table +
                " WHERE mine = " + (mine?1:0) + " and number = '" + number + "'";
        Cursor cursor = database.retrieveData(query);
        if (cursor != null) {
            List<DebtHeader> debtHeaderList = new ArrayList<>();
            while (cursor.moveToNext()) {
                debtHeaderList.add(new DebtHeader(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getString(cursor.getColumnIndex("currency")),
                        cursor.getDouble(cursor.getColumnIndex("total")),
                        cursor.getInt(cursor.getColumnIndex("mine")) != 0
                ));
            }
            if (debtHeaderList.size() == 1) {
                return debtHeaderList.get(0);
            }
        }
        return null;
    }

    @Override
    public Balance lookupBalance(String number) {
        String query = "SELECT number, name, currency, mytotal, partytotal, total, mine FROM " + DatabaseAdapter.BALANCE_TABLE +
                " WHERE number = '" + number + "'";
        Cursor cursor = database.retrieveData(query);
        if (cursor != null) {
            List<Balance> balanceList = new ArrayList<>();
            while (cursor.moveToNext()) {
                balanceList.add(new Balance(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getString(cursor.getColumnIndex("currency")),
                        cursor.getDouble(cursor.getColumnIndex("partytotal")),
                        cursor.getDouble(cursor.getColumnIndex("mytotal")),
                        cursor.getDouble(cursor.getColumnIndex("total")),
                        cursor.getInt(cursor.getColumnIndex("mine")) != 0
                ));
            }
            if (balanceList.size() == 1) {
                return balanceList.get(0);
            }
        }
        return null;
    }


    @Override
    public Contact lookupContact(String number) {
        String query = "SELECT id, number, name FROM " + DatabaseAdapter.CONTACT_TABLE +
                " WHERE number = '" + number + "'";

        Cursor cursor = database.retrieveData(query);
        if (cursor != null) {
            List<Contact> contactList = new ArrayList<>();
            while (cursor.moveToNext()) {
                contactList.add(new Contact(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("number")),
                        cursor.getString(cursor.getColumnIndex("name"))
                ));
            }
            if (contactList.size() == 1) {
                return contactList.get(0);
            }
        }
        return null;
    }
}
