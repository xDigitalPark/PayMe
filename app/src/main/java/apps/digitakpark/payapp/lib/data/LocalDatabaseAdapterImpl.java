package apps.digitakpark.payapp.lib.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseAdapterImpl  extends SQLiteOpenHelper implements DatabaseAdapter {

    public static final String DATABASE_NAME = "PayMe.db";

    public LocalDatabaseAdapterImpl(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // DEBT HEADER
        db.execSQL("CREATE TABLE " + DEBT_HEADER_TABLE_TOCHARGE + " (number TEXT PRIMARY KEY, name TEXT, currency TEXT, total REAL, mine NUMERIC)");
        db.execSQL("CREATE TABLE " + DEBT_HEADER_TABLE_TOPAY + " (number TEXT PRIMARY KEY, name TEXT, currency TEXT, total REAL, mine NUMERIC)");
        // DEBT
        db.execSQL("CREATE TABLE " + DEBT_TABLE_TOCHARGE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT, concept TEXT, currency TEXT, total REAL, date INT, mine NUMERIC, date_limit INT)");
        db.execSQL("CREATE TABLE " + DEBT_TABLE_TOPAY + " (id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT, concept TEXT, currency TEXT, total REAL, date INT, mine NUMERIC, date_limit INT)");
        // BALANCE
        db.execSQL("CREATE TABLE " + BALANCE_TABLE + " (number TEXT, name TEXT, currency TEXT, mytotal REAL, partytotal REAL, total REAL, mine NUMERIC)");
        // CONTACTS
        db.execSQL("CREATE TABLE " + CONTACT_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT, name TEXT)");
        // PAYMENTS
        db.execSQL("CREATE TABLE " + PAYMENTS_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT, date INT, total REAL, mine NUMERIC, debt_id INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS " + DEBT_HEADER_TABLE_TOCHARGE);
//        db.execSQL("DROP TABLE IF EXISTS " + DEBT_HEADER_TABLE_TOPAY);
//        db.execSQL("DROP TABLE IF EXISTS " + DEBT_TABLE_TOCHARGE);
//        db.execSQL("DROP TABLE IF EXISTS " + DEBT_TABLE_TOPAY);
//        db.execSQL("DROP TABLE IF EXISTS " + BALANCE_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        db.execSQL("CREATE TABLE " + PAYMENTS_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT, date INT, total REAL, mine NUMERIC, debt_id INT)");
        onCreate(db);
    }

    @Override
    public boolean insertData(String table, ContentValues data) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(table, null, data) != 0;
    }

    @Override
    public Long insertDataAndGetId(String table, ContentValues data) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(table, null, data);
    }

    @Override
    public Cursor retrieveData(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    @Override
    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    @Override
    public boolean updateData(String table, ContentValues data, String clause, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(table, data, clause, new String[] {id}) != 0;
    }

    @Override
    public boolean deleteData(String table, String clause, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, clause, new String[]{id}) != 0;
    }

    @Override
    public boolean deleteData(String table, String clause, String[] values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, clause, values) != -1;
    }

    @Override
    public int countData(String table, String clause) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + table + " ";
        Integer count = 0;
        if (clause.length() > 0) {
            query += "WHERE " + clause;
        }
        Cursor cursor = retrieveData(query);
        count = cursor.getCount();
        cursor.close();
       return count;
    }
}
