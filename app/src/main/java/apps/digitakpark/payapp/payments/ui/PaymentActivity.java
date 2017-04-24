package apps.digitakpark.payapp.payments.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.create.ui.CreateDebtActivity;
import apps.digitakpark.payapp.detail.ui.DebtDetailedActivity;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.model.Payment;
import apps.digitakpark.payapp.payments.PaymentsPresenter;
import apps.digitakpark.payapp.payments.PaymentsPresenterImpl;
import apps.digitakpark.payapp.payments.adapters.PaymentAdapter;
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
    @BindView(R.id.payments_empty_view)
    TextView paymentsEmptyView;

    private PaymentAdapter adapter;

    // debt data
    private String currency;
    private String number;
    private String name;
    private String concept;
    private Long debtId;
    private boolean mine;
    private Double totalDebt;
    private PaymentsPresenter presenter;
    private Long date;
    private Long dateLimit;

    public static final int DEBT_UPDATED_RESULT = 0x01;

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

        final EditText paymentTotal = (EditText) promptsView.findViewById(R.id.dialog_payment_total);

        // FIXME: No debe de exceder el monto de la deuda
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Registrar Pago")
                .setPositiveButton("Guardar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // do nothing
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (paymentTotal.getText().toString().isEmpty())
                            return;
                        Double amount = Double.parseDouble(paymentTotal.getText().toString());
                        if (amount <= 0) {
                            paymentTotal.setError(getString(R.string.create_debt_activity_invalid_total_amount));
                            return;
                        }

                        if (amount > totalDebt) {
                            paymentTotal.setError(getString(R.string.create_debt_activity_unbound_total_amount));
                            return;
                        }

                        doPayment(amount);
                        alertDialog.dismiss();
                    }
                });
            }
        });

        alertDialog.show();
    }

    @Override
    public void doPayment(Double total) {
        Payment payment = new Payment();
        payment.setTotal(total);
        payment.setDate(new Date().getTime());
        payment.setTotal(-1 * total);
        payment.setNumber(number);
        payment.setMine(mine);
        payment.setDebtId(debtId);
        Double newValue = 0d;
        newValue = totalDebt - total;
        presenter.sendRegisterPayment(payment, totalDebt, newValue);
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
            navigateToEditDebt();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DEBT_UPDATED_RESULT) {
            if (data != null)
                prepopActivity(data.getExtras());
        }
    }

    private void navigateToEditDebt() {
        Intent intent = new Intent(getApplicationContext(), CreateDebtActivity.class);
        intent.putExtra("debt_id", debtId);
        intent.putExtra("debt_mine", mine);
        intent.putExtra("debt_number", number);
        intent.putExtra("debt_name", name);
        intent.putExtra("debt_edit", true);
        intent.putExtra("debt_total", totalDebt);
        intent.putExtra("debt_concept", concept);
        intent.putExtra("debt_date", date);
        intent.putExtra("debt_limit", dateLimit);
        intent.putExtra("from_payments", true);
        startActivityForResult(intent, DEBT_UPDATED_RESULT);

    }

    @Override
    public void prepopActivity(Bundle extras) {

        // concept
        concept = extras.getString(DebtDetailedActivity.DEBT_CONCEPT);
        setTitle(concept);

        // date
        date = extras.getLong(DebtDetailedActivity.DEBT_DATE);
        String dateFmt = PaymeApplication.getFormatters().formatDate(date);
        paymentActivityItemDateText.setText(dateFmt);

        // total
        totalDebt = extras.getDouble(DebtDetailedActivity.DEBT_TOTAL);
        String totalFmt = PaymeApplication.getFormatters().formatMoney(totalDebt);
        currency = extras.getString(DebtDetailedActivity.DEBT_CURRENCY);
        paymentActivityTotalEdittext.setText(currency + " " + totalFmt);
        mine = extras.getBoolean(DebtDetailedActivity.DEBT_MINE);
        if (mine) {
            paymentActivityTotalEdittext.setTextColor(getResources().getColor(R.color.negativeDebt));
        } else {
            paymentActivityTotalEdittext.setTextColor(getResources().getColor(R.color.positiveDebt));
        }

        // limit
        dateLimit = extras.getLong(DebtDetailedActivity.DEBT_LIMIT);
        if (dateLimit != null & dateLimit > 0) {
            String limitFmt = PaymeApplication.getFormatters().formatDate(dateLimit);
            paymentActivityItemDateLimitText.setText(limitFmt);
            paymentActivityItemDateLimitText.setVisibility(View.VISIBLE);
            activityDebtDetailItemDateLimitIcon.setVisibility(View.VISIBLE);
        } else {
            paymentActivityItemDateLimitText.setVisibility(View.GONE);
            activityDebtDetailItemDateLimitIcon.setVisibility(View.GONE);
        }

        if (extras.containsKey("from_payments")) return;

        debtId = extras.getLong(DebtDetailedActivity.DEBT_ID);
        number = extras.getString(DebtDetailedActivity.DEBT_NUMBER);

        // name
        name = extras.getString(DebtDetailedActivity.DEBT_NAME);
        paymentActivityConceptEdittext.setText(name);

    }

    @Override
    public void onLoadPaymentList(List<Payment> paymentList) {
        this.adapter.changeDataSet(paymentList);
        if (paymentList.size() == 0) {
            paymentsEmptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            paymentsEmptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onPaymentCreated(Double amount) {
        adapter.changeDataSet(new ArrayList<Payment>());
        presenter.sendRetrievePaymentsAction(number, mine, debtId);
        totalDebt += amount;
        String totalFmt = PaymeApplication.getFormatters().formatMoney(totalDebt);
        paymentActivityTotalEdittext.setText(currency + " " + totalFmt);
    }
}
