package payme.apps.seven.org.payme.lib.data;

import android.content.ContentValues;
import android.database.Cursor;

public interface DatabaseAdapter {

    String CONTACT_TABLE = "CONTACTS";
    String CONTACT_ID = "id";
    String CONTACT_NUMBER = "number";
    String CONTACT_NAME = "name";

    String DEBT_HEADER_TABLE_TOCHARGE = "DEBT_HEADER_TOCHARGE";
    String DEBT_HEADER_TABLE_TOPAY = "DEBT_HEADER_TOPAY";
    String DEBT_HEADER_TABLE_COL_NUMBER = "number";
    String DEBT_HEADER_TABLE_COL_NAME = "name";
    String DEBT_HEADER_TABLE_COL_CURRENCY = "currency";
    String DEBT_HEADER_TABLE_COL_TOTAL = "total";
    String DEBT_HEADER_TABLE_COL_MINE = "mine";

    String DEBT_TABLE_TOCHARGE = "DEBT_TOCHARGE";
    String DEBT_TABLE_TOPAY = "DEBT_tOPAY"; // FIXME: CHANGE NAME
    String DEBT_TABLE_COL_NUMBER = "number";
    String DEBT_TABLE_COL_CONCEPT = "concept";
    String DEBT_TABLE_COL_CURRENCY = "currency";
    String DEBT_TABLE_COL_TOTAL = "total";
    String DEBT_TABLE_COL_DATE = "date";
    String DEBT_TABLE_COL_MINE = "mine";
    String DEBT_TABLE_COL_DATE_LIMIT = "date_limit";

    String BALANCE_TABLE = "DEBT_BALANCE";
    String BALANCE_TABLE_COL_NUMBER = "number";
    String BALANCE_TABLE_COL_NAME = "name";
    String BALANCE_TABLE_COL_CURRENCY = "currency";
    String BALANCE_TABLE_COL_MY_TOTAL = "mytotal";
    String BALANCE_TABLE_COL_PARTY_TOTAL = "partytotal";
    String BALANCE_TABLE_COL_TOTAL = "total";
    String BALANCE_TABLE_COL_MINE = "mine";

    boolean insertData(String table, ContentValues data);
    boolean deleteData(String table, String clause, String id);
    boolean updateData(String table, ContentValues data, String clause, String id);
    void executeSQL(String sql);
    int countData(String table, String clause);
    Cursor retrieveData(String query);
}
