package apps.digitakpark.payme.balance.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import payme.pe.apps.digitakpark.payme.R;
import apps.digitakpark.payme.balance.ui.BalanceView;

public class BalanceViewHolder extends RecyclerView.ViewHolder
        implements View.OnCreateContextMenuListener
{

    public TextView nameEditText;
    public TextView totalEditText;
    public TextView myTotalText;
    public TextView partyTotalText;
    public View view;

    public BalanceViewHolder(View view) {
        super(view);
        this.view = view;
        this.nameEditText = (TextView) view.findViewById(R.id.fragment_balance_item_name_edittext);
        this.totalEditText = (TextView) view.findViewById(R.id.fragment_balance_item_total_edittext);
        this.myTotalText = (TextView) view.findViewById(R.id.fragment_balance_mytotal_edittext);
        this.partyTotalText = (TextView) view.findViewById(R.id.fragment_balance_party_total_edittext);
        this.view.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(0, BalanceView.CLOSE_DEBT, 0, R.string.action_archive_debt_title);
    }
}
