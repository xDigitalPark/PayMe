package payme.apps.seven.org.payme.create.adapters;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import payme.apps.seven.org.payme.model.Contact;

/**
 * Created by murruer on 3/11/2017.
 */

public class ContactFilter extends Filter {

    ContactsArrayAdapter adapter;
    List<Contact> originalList;
    List<Contact> filteredList;

    public ContactFilter(ContactsArrayAdapter adapter, List<Contact> originalList) {
        super();
        this.adapter = adapter;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults results = new FilterResults();

        if (constraint == null || constraint.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            // Your filtering logic goes in here
            for (final Contact contact : originalList) {
                if (contact.getName().toLowerCase().contains(filterPattern)) {
                    filteredList.add(contact);
                }
            }
        }
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.filteredContacts.clear();
        adapter.filteredContacts.addAll((List) results.values);
        adapter.notifyDataSetChanged();
    }

}