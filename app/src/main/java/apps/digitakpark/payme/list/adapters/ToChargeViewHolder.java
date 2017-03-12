package apps.digitakpark.payme.list.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import payme.pe.apps.digitakpark.payme.R;
import apps.digitakpark.payme.list.common.ui.ListDebtView;

public class ToChargeViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

    public TextView nameEditText;
    public TextView totalEditText;
    public View view;

    public ToChargeViewHolder(View view) {
        super(view);
        this.view = view;
        this.nameEditText = (TextView) view.findViewById(R.id.fragment_to_charge_item_name_edittext);
        this.totalEditText = (TextView) view.findViewById(R.id.fragment_to_charge_item_total_edittext);
        this.view.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(0, ListDebtView.TO_CHARGE_CLOSE_DEBT, 0, R.string.fragment_list_close_debt);

    }
}