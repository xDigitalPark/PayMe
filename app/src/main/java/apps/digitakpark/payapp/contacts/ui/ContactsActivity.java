package apps.digitakpark.payapp.contacts.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import apps.digitakpark.payapp.contacts.ContactPresenter;
import apps.digitakpark.payapp.contacts.ContactPresenterImpl;
import apps.digitakpark.payapp.contacts.adapters.ContactsActivityAdapter;
import apps.digitakpark.payapp.contacts.ui.ContactsView;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.model.Contact;
import butterknife.OnClick;
import payme.pe.apps.digitakpark.payme.R;



public class ContactsActivity extends AppCompatActivity implements ContactsView {

    public static final int PICK_CONTACT = 0x01;
    RecyclerView recyclerView;
    ContactsActivityAdapter adapter;
    private List<Contact> contactsList;
    private ContactPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.contactsList = new ArrayList<>();
        this.adapter = new ContactsActivityAdapter(this.contactsList);
        this.recyclerView = (RecyclerView)findViewById(R.id.activity_contacts_recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.contacts_activity_add_contact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickContactActivity();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        this.presenter = new ContactPresenterImpl(this);
        this.presenter.onCreate();
    }

    @Override
    protected void onStart() {
        presenter.sendRetrieveContactList();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
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
    public void onLoadContactList(List<Contact> contactList) {
        adapter.changeDataSet(contactList);
    }

    @Override
    public void showPickContactActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
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
                    presenter.doSendAddContact(number, name);
                } else {
                    showMessage(getString(R.string.create_debt_activity_invalid_contact_selection));
                }
            }
        }
    }
    public void showMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void showContactAdded() {
        Snackbar.make(recyclerView, "Contact Added!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
