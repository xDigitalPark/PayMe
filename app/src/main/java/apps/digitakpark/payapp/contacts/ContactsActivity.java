package apps.digitakpark.payapp.contacts;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import apps.digitakpark.payapp.contacts.adapters.ContactsActivityAdapter;
import apps.digitakpark.payapp.contacts.ui.ContactsView;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.model.Contact;
import payme.pe.apps.digitakpark.payme.R;



public class ContactsActivity extends AppCompatActivity implements ContactsView {

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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
    public void onLoadContactList(List<Contact> contactList) {
        adapter.changeDataSet(contactList);
    }

    @Override
    public void showPickContactActivity() {

    }

    @Override
    public void onPickedContact(Uri contactData) {

    }
}
