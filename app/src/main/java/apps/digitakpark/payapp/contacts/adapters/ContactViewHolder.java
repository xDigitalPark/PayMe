package apps.digitakpark.payapp.contacts.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
import apps.digitakpark.payapp.contacts.ui.ContactsView;
import payme.pe.apps.digitakpark.payme.R;

public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

    public TextView nameEditText;
    public View view;

    public ContactViewHolder(View view) {
        super(view);
        this.nameEditText = (TextView) view.findViewById(R.id.activity_contacts_name);
        this.view = view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(0, ContactsView.DELETE_CONTACT, 0, R.string.common_delete);
    }

}
