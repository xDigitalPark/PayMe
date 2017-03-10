package payme.apps.seven.org.payme.create.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import payme.apps.seven.org.payme.PaymeApplication;
import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.create.CreateDebtPresenter;
import payme.apps.seven.org.payme.create.CreateDebtPresenterImpl;
import payme.apps.seven.org.payme.events.DebtEvent;
import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public class CreateDebtActivity extends AppCompatActivity implements CreateDebtView {

    public static final int PICK_CONTACT = 1001;
    @BindView(R.id.activity_create_debt_total)
    EditText activityCreateDebtTotal;
    @BindView(R.id.activity_create_debt_concept)
    EditText activityCreateDebtConcept;
    @BindView(R.id.activity_create_debt_layout)
    View view;
    @BindView(R.id.activity_create_debt_date)
    TextView dateTextView;
    @BindView(R.id.activity_create_debt_username)
    AutoCompleteTextView usernameTextView;
    @BindView(R.id.activity_create_debt_radiogroup)
    RadioGroup activityCreateDebtRadiogroup;
    @BindView(R.id.activity_create_debt_not_mine)
    RadioButton activityCreateDebtNotMine;
    @BindView(R.id.activity_create_debt_mine)
    RadioButton activityCreateDebtMine;
    @BindView(R.id.activity_create_debt_limit_date)
    TextView activityCreateDebtLimitDate;
    @BindView(R.id.activity_create_debt_currency)
    Spinner activityCreateDebtCurrency;
    @BindView(R.id.activity_create_debt_date_clean)
    ImageView activityCreateDebtDateClean;
    @BindView(R.id.activity_create_debt_limit_clean)
    ImageView activityCreateDebtLimitClean;


    private Debt debt = new Debt();
    private DebtHeader debtHeader = new DebtHeader();
    private CreateDebtPresenter presenter;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datePickerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_debt);
        ButterKnife.bind(this);
        presenter = new CreateDebtPresenterImpl(this);
        setTitle(getString(R.string.create_deb_activity_title));
        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };
        Bundle extras = getIntent().getExtras();
        prepopActivity(extras);

        usernameTextView.setAdapter(getEmailAddressAdapter(this));
        activityCreateDebtCurrency.setAdapter(getCurrencieList(this));
        activityCreateDebtCurrency.setSelection(0);

        presenter.onCreate();
    }

    private ArrayAdapter<String> getCurrencieList(Context context) {
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, new String[]{"S/."});
    }

    private ArrayAdapter<String> getEmailAddressAdapter(Context context) {
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, new String[]{"erikson", "sayury"});
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_debt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create_debt) {
            createNewDebt();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_CONTACT:
                onPickedContact(data.getData());
        }
    }

    @Override
    public void prepopActivity(Bundle extras) {
        boolean mine = extras.getBoolean("debt_mine");
        if (mine) {
            activityCreateDebtMine.setChecked(true);
        } else {
            activityCreateDebtNotMine.setChecked(true);
        }
        if (extras.containsKey("debt_name")) {
            String name = extras.getString("debt_name");
            String number = extras.getString("debt_number");
            usernameTextView.setText(name);
            this.debt.setNumber(number);
            this.debtHeader.setNumber(number);
            this.debtHeader.setName(name);
        }
    }

    @Override
    public void createNewDebt() {
        if (validateViewForm()) {
            presenter.sendNewDebtCreationAction(this.debtHeader, this.debt);
        }
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showProgressBar() {
        // TODO: Implement show Progressbar.
    }

    @Override
    public void hideProgressBar() {
        // TODO: Implement hide Progressbar.
    }

    @Override
    public boolean validateViewForm() {

        if (usernameTextView.getText().toString().equals("")) {
            usernameTextView.setError("Invalid Contact Name");
            usernameTextView.setFocusable(true);
        }

        if (activityCreateDebtTotal.getText().toString().equals("")) {
            activityCreateDebtTotal.setError(getString(R.string.create_debt_activity_invalid_total_amount));
            activityCreateDebtTotal.setFocusable(true);
            return false;
        }
        if (activityCreateDebtConcept.getText().toString().equals("")) {
            activityCreateDebtConcept.setError(getString(R.string.create_debt_activity_invalid_concept));
            activityCreateDebtConcept.setFocusable(true);
            return false;
        }
        Double total = Double.parseDouble(activityCreateDebtTotal.getText().toString());
        String concept = activityCreateDebtConcept.getText().toString();
        if (total == 0) {
            activityCreateDebtTotal.setError(getString(R.string.create_debt_activity_invalid_total_amount));
            activityCreateDebtTotal.setFocusable(true);
            return false;
        }
        if (concept.length() <= 2) {
            activityCreateDebtConcept.setError(getString(R.string.create_debt_activity_invalid_concept));
            activityCreateDebtConcept.setFocusable(true);
            return false;
        }
        boolean mine = (activityCreateDebtRadiogroup.getCheckedRadioButtonId()
                == R.id.activity_create_debt_mine);
        //activityCreateDebtCurrency
        String currency = activityCreateDebtCurrency.getSelectedItem().toString();
        debt.setConcept(activityCreateDebtConcept.getText().toString());
        debt.setTotal(total);
        debt.setCurrency(currency);
        debt.setMine(mine);
        debt.setDate(myCalendar.getTimeInMillis());

        debtHeader.setTotal(total);
        debtHeader.setCurrency(currency);
        debtHeader.setMine(mine);

        if (debt.getNumber() == null) {
            String nameKey = usernameTextView.getText().toString().trim();
            debt.setNumber(nameKey);
            debtHeader.setNumber(nameKey);
            debtHeader.setName(usernameTextView.getText().toString());
        }

        return true;
    }

    @Override
    public void onDebtCreated(DebtEvent event) {
//        showMessage(event.getMessage());
        finish();
    }

    @Override
    public void updateContactInfo(String number, String name) {
        debt.setNumber(number);
        debtHeader.setNumber(number);
        debtHeader.setName(name);
        usernameTextView.setText(name);
    }

    @OnClick(R.id.activity_create_debt_date)
    @Override
    public void showDatePickerDialog() {
        new DatePickerDialog(CreateDebtActivity.this, datePickerListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                    String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    updateContactInfo(number, name);
                } else {
                    showMessage(getString(R.string.create_debt_activity_invalid_contact_selection));
                }
            }
        }
    }

    @OnClick(R.id.activity_create_debt_avatar)
    @Override
    public void showPickContactActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    public void updateDateLabel() {
        dateTextView.setText(PaymeApplication.getFormatters().formatDate(myCalendar.getTimeInMillis()));
    }

    @Override
    public void showMessage(String message) {
        hideKeyboard();
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
