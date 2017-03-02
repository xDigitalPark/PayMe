package payme.apps.seven.org.payme.detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.detail.ui.DebtDetailView;

public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
{
    public TextView conceptEditText;
    public TextView totalEditText;
    public View view;

    public DetailViewHolder(View view) {
        super(view);
        this.view = view;
        this.conceptEditText = (TextView) view.findViewById(R.id.activity_debt_detail_item_concept_edittext);
        this.totalEditText = (TextView) view.findViewById(R.id.activity_debt_detail_item_total_edittext);
        this.view.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(0, DebtDetailView.CLOSE_DEBT, 0, R.string.fragment_list_close_debt);
        menu.add(0, DebtDetailView.DELETE_DEBT, 0, R.string.fragment_list_delete_debt);
    }
}