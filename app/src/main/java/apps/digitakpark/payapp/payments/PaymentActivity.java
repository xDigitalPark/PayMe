package apps.digitakpark.payapp.payments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.create.ui.CreateDebtActivity;
import apps.digitakpark.payapp.detail.adapters.DebtDetailActivityAdapter;
import apps.digitakpark.payapp.detail.ui.DebtDetailedActivity;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.model.Payment;
import apps.digitakpark.payapp.payments.adapters.PaymentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import payme.pe.apps.digitakpark.payme.R;

public class PaymentActivity extends AppCompatActivity implements PaymentView {


    @BindView(R.id.payment_activity_concept_edittext)
    TextView paymentActivityConceptEdittext;
    @BindView(R.id.payment_activity_total_edittext)
    TextView paymentActivityTotalEdittext;
    @BindView(R.id.payment_activity_item_date_limit_text)
    TextView paymentActivityItemDateLimitText;
    @BindView(R.id.activity_debt_detail_item_date_limit_icon)
    ImageView activityDebtDetailItemDateLimitIcon;
    @BindView(R.id.payment_activity_item_date_text)
    TextView paymentActivityItemDateText;
    @BindView(R.id.payment_activity_recyclerview)
    RecyclerView recyclerView;

    PaymentAdapter adapter;
    String currency;
    boolean mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        prepopActivity(extras);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment(1L, "2323", (new Date().getTime()), 23.4D));
        paymentList.add(new Payment(1L, "2323", (new Date().getTime()), 2.4D));
        paymentList.add(new Payment(1L, "2323", (new Date().getTime()), -3.4D));
        paymentList.add(new Payment(1L, "2323", (new Date().getTime()), 45.4D));
        paymentList.add(new Payment(1L, "2323", (new Date().getTime()), -23.4D));
        adapter = new PaymentAdapter(paymentList, currency, mine);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit_debt) {
//            onEditDebtOptionSelected();
        }
        return super.onOptionsItemSelected(item);
    }

//    public void onEditDebtOptionSelected() {
//        Intent intent = new Intent(getApplicationContext(), CreateDebtActivity.class);
//        intent.putExtra("debt_id", debt.getId());
//        intent.putExtra("debt_mine", mine);
//        intent.putExtra("debt_number", number);
//        intent.putExtra("debt_name", name);
//        intent.putExtra("debt_edit", true);
//        intent.putExtra("debt_total", debt.getTotal());
//        intent.putExtra("debt_concept", debt.getConcept());
//        intent.putExtra("debt_date", debt.getDate());
//        intent.putExtra("debt_limit", debt.getLimit());
//        startActivity(intent);
//    }



    @Override
    public void prepopActivity(Bundle extras) {

        setTitle(extras.getString(DebtDetailedActivity.DEBT_CONCEPT));
        String name = extras.getString(DebtDetailedActivity.DEBT_NAME);
        currency = extras.getString(DebtDetailedActivity.DEBT_CURRENCY);
        String totalFmt = PaymeApplication.getFormatters().formatMoney(extras.getDouble(DebtDetailedActivity.DEBT_TOTAL));
        String dateFmt = PaymeApplication.getFormatters().formatDate(extras.getLong(DebtDetailedActivity.DEBT_DATE));
        Long dateLimit = extras.getLong(DebtDetailedActivity.DEBT_LIMIT);
        paymentActivityConceptEdittext.setText(name);
        paymentActivityTotalEdittext.setText(currency + " " + totalFmt);
        paymentActivityItemDateLimitText.setText(extras.getString(DebtDetailedActivity.DEBT_LIMIT));
        paymentActivityItemDateText.setText(dateFmt);

        mine = extras.getBoolean(DebtDetailedActivity.DEBT_MINE);
        if (mine) {
            paymentActivityTotalEdittext.setTextColor(getResources().getColor(R.color.negativeDebt));
        } else {
            paymentActivityTotalEdittext.setTextColor(getResources().getColor(R.color.positiveDebt));
        }

        if (dateLimit != null && dateLimit != 0) {
            String limitFmt = PaymeApplication.getFormatters().formatDate(dateLimit);
            paymentActivityItemDateLimitText.setText(limitFmt);
            paymentActivityItemDateLimitText.setVisibility(View.VISIBLE);
            activityDebtDetailItemDateLimitIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadPaymentList(List<Payment> paymentList) {

    }
}
