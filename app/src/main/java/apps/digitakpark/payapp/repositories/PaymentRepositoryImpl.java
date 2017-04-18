package apps.digitakpark.payapp.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.events.PaymentEvent;
import apps.digitakpark.payapp.lib.data.DatabaseAdapter;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.Payment;

public class PaymentRepositoryImpl implements PaymentRepository {

    private EventBus eventBus;
    private DatabaseAdapter database;

    public PaymentRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.database = PaymeApplication.getDatabaseInstance();
    }

    @Override
    public void registerPayment(Payment payment) {
        ContentValues paymentData = new ContentValues();
        paymentData.put(DatabaseAdapter.PAYMENTS_TABLE_COL_DATE, payment.getDate());
        paymentData.put(DatabaseAdapter.PAYMENTS_TABLE_COL_TOTAL, payment.getTotal());
        paymentData.put(DatabaseAdapter.PAYMENTS_TABLE_COL_NUMBER, payment.getNumber());
        paymentData.put(DatabaseAdapter.PAYMENTS_TABLE_COL_MINE, payment.isMine());
        paymentData.put(DatabaseAdapter.PAYMENTS_TABLE_COL_DEBT_ID, payment.getDebtId());
        boolean success = database.insertData(DatabaseAdapter.PAYMENTS_TABLE, paymentData);
        if (success){
            PaymentEvent event = new PaymentEvent();
            event.setStatus(PaymentEvent.PAYMENT_CREATED_OK);
            event.setMessage("OK");
            eventBus.post(event);
        }
    }

    @Override
    public void getPayments(String number, boolean mine, Long debtId) {
        String query = "SELECT id, date, total FROM " +
                DatabaseAdapter.PAYMENTS_TABLE +
                " WHERE number='" + number + "' AND mine = " + (mine?1:0) + " AND debt_id = " + debtId +
                " order by date desc";

        Cursor cursor = database.retrieveData(query);
        List<Payment> paymentsList = new ArrayList<>();
        if (cursor != null) {
            Payment debt = null;
            Double total = 0D;
            while(cursor.moveToNext()) {
                debt = new Payment(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getLong(cursor.getColumnIndex("date")),
                        cursor.getDouble(cursor.getColumnIndex("total"))
                );
                paymentsList.add(debt);
                total = total + debt.getTotal();
            }
            PaymentEvent event = new PaymentEvent();
            event.setStatus(PaymentEvent.PAYMENT_LIST_OK);
            event.setMessage("OK");
            event.setPaymentList(paymentsList);
            event.setTotal(total);
            eventBus.post(event);

        }
    }

}
