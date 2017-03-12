package payme.apps.seven.org.payme.create.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.model.Contact;

public class ContactsArrayAdapter extends ArrayAdapter<Contact> {

    private final List<Contact> contacts;
    public List<Contact> filteredContacts = new ArrayList<>();

    public ContactsArrayAdapter(@NonNull Context context, @NonNull List<Contact> objects) {
        super(context, 0, objects);
        contacts = objects;
    }

    @Override
    public int getCount() {
        return filteredContacts.size();
    }

    @Override
    public Filter getFilter() {
        return new ContactFilter(this, contacts);
    }

    @Nullable
    @Override
    public Contact getItem(int position) {
        return filteredContacts.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item from filtered list.
        Contact contact = filteredContacts.get(position);

        // Inflate your custom row layout as usual.
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.activity_create_debt_contact_item, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.activity_create_debt_contact_item_name);
        tvName.setText(contact.getName());
//        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.row_icon);
//        ivIcon.setImageResource(dog.drawable);

        return convertView;
    }

}
