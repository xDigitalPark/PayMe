package apps.digitakpark.payapp.detail.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import apps.digitakpark.payapp.create.ui.CreateDebtActivity;
import apps.digitakpark.payapp.detail.DebtDetailPresenter;
import apps.digitakpark.payapp.detail.DebtDetailPresenterImpl;
import apps.digitakpark.payapp.detail.adapters.DebtDetailActivityAdapter;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.list.common.ui.OnClickDebtHeaderListener;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.payments.PaymentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import payme.pe.apps.digitakpark.payme.R;

import android.support.v7.app.AlertDialog;

public class DebtDetailedActivity extends AppCompatActivity implements DebtDetailView, OnClickDebtDetailListener {

    public static final int PICK_CONTACT_TO_LINK = 1002;

    @BindView(R.id.activity_debt_detailed_total)
    TextView activityDebtDetailedTotal;
    @BindView(R.id.activity_debt_detailed_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.activity_debt_detail_layout)
    View view;
    private String number;
    private String name;
    private Debt debt;
    private boolean mine;
    List<Debt> debtList = new ArrayList<>();
    private DebtDetailPresenter presenter;
    DebtDetailActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_detailed);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        prepopActivity(extras);

        this.adapter = new DebtDetailActivityAdapter(this.debtList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_debt_detailed_add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCreateDebtActivity(mine);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new DebtDetailPresenterImpl(this);
        presenter.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_debt_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_debt_detail_call) {
            callToNumber(this.debt.getNumber());
        } else if(item.getItemId() == R.id.action_debt_detail_link) {
            showPickContactActivity();
        } else if(item.getItemId() == R.id.action_debt_detail_share) {
            showShareAction();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(Debt debt) {
        navigateToPaymentsActivity(debt);
    }

    @Override
    public void navigateToPaymentsActivity(Debt debt) {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);
    }

    public  void showShareAction() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Lista de Deudas: \n*total:  S/.%s*\n", adapter.getTotal()));
        sb.append("-------------------------------\n");
        int i = 1;
        for(Debt debt: this.adapter.getDebtList()) {
            sb.append(String.format("*%d:* %s. *%s%s*\n", i++, debt.getConcept(), debt.getCurrency(), debt.getTotal()));
        }
        sb.append("-------------------------------\n");
        sb.append("_Generado con *Pay2Me*_.\n_Encuentrala en PlayStore._\n");
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sb.toString());
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.activity_debt_detail_share_menu_title)));
    }


    @Override
    protected void onStart() {
        this.presenter.sendRetrieveDebtsAction(this.number, this.mine);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void prepopActivity(Bundle extras) {
        name = extras.getString("debt_name");
        number = extras.getString("debt_number");
        mine = extras.getBoolean("debt_mine");
        setTitle(name);
        debt = new Debt();
        debt.setNumber(number);
        debt.setMine(mine);
    }

    @Override
    public void onLoadDebtList(List<Debt> debtList) {
        this.adapter.changeDataSet(debtList);
    }

    @Override
    public void onLoadTotal(Double total) {
        String title = mine?getString(R.string.activity_debt_detailed_ledebo):
                getString(R.string.activity_debt_detailed_medebe);

        activityDebtDetailedTotal.setText(title + "   |   S/. " + total);
    }

    @Override
    public void navigateToCreateDebtActivity(boolean mine) {
        Intent intent = new Intent(getApplicationContext(), CreateDebtActivity.class);
        intent.putExtra("debt_mine", mine);
        intent.putExtra("debt_number", number);
        intent.putExtra("debt_name", name);
        startActivity(intent);
    }

    @Override
    public void onDebtSelected(Debt debt, int actionId) {
        switch (actionId) {
            case CLOSE_DEBT:
                onCloseDebtOptionSelected(debt);
                break;
            case DELETE_DEBT:
                onDeleteDebtOptionSelected(debt);
                break;
            case EDIT_DEBT:
                onEditDebtOptionSelected(debt);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onDebtSelected(adapter.getSelectedDebt(), item.getItemId());
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCloseDebtOptionSelected(Debt debt) {
        presenter.sendCloseDebtAction(debt);
    }

    @Override
    public void onDeleteDebtOptionSelected(Debt debt) {
        presenter.sendDeleteDebtAction(debt);
    }

    @Override
    public void onEditDebtOptionSelected(Debt debt) {
        Intent intent = new Intent(getApplicationContext(), CreateDebtActivity.class);
        intent.putExtra("debt_id", debt.getId());
        intent.putExtra("debt_mine", mine);
        intent.putExtra("debt_number", number);
        intent.putExtra("debt_name", name);
        intent.putExtra("debt_edit", true);
        intent.putExtra("debt_total", debt.getTotal());
        intent.putExtra("debt_concept", debt.getConcept());
        intent.putExtra("debt_date", debt.getDate());
        intent.putExtra("debt_limit", debt.getLimit());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_CONTACT_TO_LINK:
                onPickedContact(data.getData());
        }
    }

    @Override
    public void onDebtDeleted(Long id) {
        adapter.removeItem(id);
        presenter.sendRetrieveDebtsAction(debt.getNumber(), debt.isMine());
        Double total = adapter.getTotal();
        if (total == 0) {
            DebtHeader debtHeader = new DebtHeader();
            debtHeader.setNumber(debt.getNumber());
            debtHeader.setMine(debt.isMine());
            presenter.sendDeleteDetHeaderAction(debtHeader);
        }
    }

    @Override
    public void onDebtHeaderDeleted() {
        finish();
    }

    @Override
    public void callToNumber(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    @Override
    public void onPickedContact(Uri contactData) {
        if (contactData != null) {
            Cursor c = managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    phones.moveToFirst();
                    String num = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    presenter.sendChangeContactLink(number, mine, num, name);
                } else {
                    showMessage(getString(R.string.create_debt_activity_invalid_contact_selection));
                }
            }
        }
    }

    @Override
    public void showPickContactActivity() {
        // Show alert before transfer
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("Transferir Cuenta?");
        builder.setMessage("Transfiere esta cuenta a otro contacto.");
        builder.setPositiveButton("Transferir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT_TO_LINK);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    @Override
    public void updateContactInfo(Map<String, String> data) {
        finish();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}
