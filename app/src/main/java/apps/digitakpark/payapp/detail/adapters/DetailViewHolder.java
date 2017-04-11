package apps.digitakpark.payapp.detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import apps.digitakpark.payapp.detail.ui.DebtDetailView;
import payme.pe.apps.digitakpark.payme.R;

public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
{
    public TextView conceptEditText;
    public TextView totalEditText;
    public TextView dateTextView;
    public TextView limitDateTextView;
    public ImageView notifImageView;
    public View view;

    public DetailViewHolder(View view) {
        super(view);
        this.view = view;
        this.conceptEditText = (TextView) view.findViewById(R.id.activity_debt_detail_item_concept_edittext);
        this.totalEditText = (TextView) view.findViewById(R.id.activity_debt_detail_item_total_edittext);
        this.dateTextView = (TextView) view.findViewById(R.id.activity_debt_detail_item_date_text);
        this.limitDateTextView = (TextView) view.findViewById(R.id.activity_debt_detail_item_date_limit_text);
        this.notifImageView = (ImageView) view.findViewById(R.id.activity_debt_detail_item_date_limit_icon);
        this.view.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(0, DebtDetailView.CLOSE_DEBT, 0, R.string.fragment_list_close_debt);
        menu.add(0, DebtDetailView.EDIT_DEBT, 0, R.string.fragment_list_edit_debt);
        menu.add(0, DebtDetailView.DELETE_DEBT, 0, R.string.fragment_list_delete_debt);
    }
}