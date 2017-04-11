package apps.digitakpark.payapp.contacts.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import apps.digitakpark.payapp.model.Contact;
import payme.pe.apps.digitakpark.payme.R;

public class ContactsActivityAdapter extends RecyclerView.Adapter<ContactViewHolder>{

    private List<Contact> contactList;
    private Contact selectedContact;

    public ContactsActivityAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contacts_item, parent, false);
        ContactViewHolder holder = new ContactViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact contact = this.contactList.get(position);
        holder.nameEditText.setText(contact.getName());
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectedContact = contact;
                return false;
            }
        });
    }

    public void changeDataSet(List<Contact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public Contact getSelectedContact() {
        return selectedContact;
    }
}
