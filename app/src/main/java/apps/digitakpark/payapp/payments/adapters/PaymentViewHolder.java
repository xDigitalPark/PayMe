package apps.digitakpark.payapp.payments.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import apps.digitakpark.payapp.list.common.ui.ListDebtView;
import payme.pe.apps.digitakpark.payme.R;

public class PaymentViewHolder extends RecyclerView.ViewHolder {
    public TextView dateEditText;
    public TextView totalEditText;
    public TextView transactionEditText;
    public View view;

    public PaymentViewHolder(View view) {
        super(view);
        this.view = view;
        this.dateEditText = (TextView) view.findViewById(R.id.activity_payment_detail_item_date_edittext);
        this.totalEditText = (TextView) view.findViewById(R.id.activity_payment_detail_item_total_edittext);
        this.transactionEditText = (TextView) view.findViewById(R.id.activity_payment_transaction_edittext);
    }

}