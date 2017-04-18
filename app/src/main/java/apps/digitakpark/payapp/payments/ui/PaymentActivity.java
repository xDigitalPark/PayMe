package apps.digitakpark.payapp.payments.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.detail.ui.DebtDetailedActivity;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.model.Payment;
import apps.digitakpark.payapp.payments.PaymentsPresenter;
import apps.digitakpark.payapp.payments.PaymentsPresenterImpl;
import apps.digitakpark.payapp.payments.adapters.PaymentAdapter;
import apps.digitakpark.payapp.payments.ui.PaymentView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private PaymentAdapter adapter;
    private String currency;
    private String number;
    private Long debtId;
    private boolean mine;


    private PaymentsPresenter presenter;

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
        adapter = new PaymentAdapter(paymentList, currency, mine);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.presenter = new PaymentsPresenterImpl(this);
        presenter.onCreate();
    }

    @Override
    protected void onStart() {
        this.presenter.sendRetrievePaymentsAction(number, mine, debtId);
        super.onStart();
    }


    @OnClick(R.id.payment_activity_add_payment_positive)
    public void showPaymentDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_payment, null);

        final RadioGroup paymentMineRadio = (RadioGroup) promptsView.findViewById(R.id.dialog_payment_radiogroup);
        RadioButton radioPay = (RadioButton) promptsView.findViewById(R.id.dialog_payment_not_mine);
        RadioButton radioLend= (RadioButton) promptsView.findViewById(R.id.dialog_payment_mine);

        if(mine) {
            radioPay.setText(getString(R.string.payment_pagas));
            radioLend.setText(getString(R.string.payment_te_presta));
        } else {
            radioPay.setText(getString(R.string.payment_te_paga));
            radioLend.setText(getString(R.string.payment_le_prestas));
        }

        final EditText paymentTotal = (EditText) promptsView.findViewById(R.id.dialog_payment_total);

        // FIXME: No debe de exceder el monto de la deuda
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Registrar")
                .setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if(paymentTotal.getText().toString().isEmpty())
                                    return;
                                Double amount = Double.parseDouble(paymentTotal.getText().toString());
                                if (amount <= 0)
                                    return;

                                boolean lend = false;
                                lend = (paymentMineRadio
                                        .getCheckedRadioButtonId() != R.id.dialog_payment_mine);
                                doPayment(amount, lend);
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void doPayment(Double total, boolean lend) {
        Payment payment = new Payment();
        payment.setTotal(total);
        payment.setDate(new Date().getTime());
        payment.setTotal( (lend?-1:1) * total);
        payment.setNumber(number);
        payment.setMine(mine);
        payment.setDebtId(debtId);
        presenter.sendRegisterPayment(payment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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


    @Override
    public void prepopActivity(Bundle extras) {

        setTitle(extras.getString(DebtDetailedActivity.DEBT_CONCEPT));
        String name = extras.getString(DebtDetailedActivity.DEBT_NAME);
        String dateFmt = PaymeApplication.getFormatters().formatDate(extras.getLong(DebtDetailedActivity.DEBT_DATE));
        String totalFmt = PaymeApplication.getFormatters().formatMoney(extras.getDouble(DebtDetailedActivity.DEBT_TOTAL));

        debtId = extras.getLong(DebtDetailedActivity.DEBT_ID);
        currency = extras.getString(DebtDetailedActivity.DEBT_CURRENCY);
        number = extras.getString(DebtDetailedActivity.DEBT_NUMBER);

        paymentActivityConceptEdittext.setText(name);
        paymentActivityTotalEdittext.setText(currency + " " + totalFmt);

        Long dateLimit = extras.getLong(DebtDetailedActivity.DEBT_LIMIT);
        if (dateLimit != null & dateLimit > 0) {
            String limitFmt = PaymeApplication.getFormatters().formatDate(dateLimit);
            paymentActivityItemDateLimitText.setText(limitFmt);
            paymentActivityItemDateText.setText(dateFmt);
        }

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
        this.adapter.changeDataSet(paymentList);
    }

    @Override
    public void onPaymentCreated() {
        presenter.sendRetrievePaymentsAction(number, mine, debtId);
    }
}
