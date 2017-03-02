package payme.apps.seven.org.payme.balance.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.balance.ui.BalanceView;
import payme.apps.seven.org.payme.detail.ui.DebtDetailView;

public class BalanceViewHolder extends RecyclerView.ViewHolder
        implements View.OnCreateContextMenuListener
{

    public TextView nameEditText;
    public TextView totalEditText;
    public View view;

    public BalanceViewHolder(View view) {
        super(view);
        this.view = view;
        this.nameEditText = (TextView) view.findViewById(R.id.fragment_balance_item_name_edittext);
        this.totalEditText = (TextView) view.findViewById(R.id.fragment_balance_item_total_edittext);
        this.view.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(0, BalanceView.CLOSE_DEBT, 0, R.string.action_archive_debt_title);
    }
}
