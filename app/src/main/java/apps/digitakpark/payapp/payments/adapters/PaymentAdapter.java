package apps.digitakpark.payapp.payments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.list.adapters.ToChargeViewHolder;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.model.Payment;
import payme.pe.apps.digitakpark.payme.R;


public class PaymentAdapter extends RecyclerView.Adapter<PaymentViewHolder> {

    private List<Payment> paymentList;
    private String currency;
    private boolean mine;

    public PaymentAdapter(List<Payment> paymentList, String currency, boolean mine) {
        this.paymentList = paymentList;
        this.currency = currency;
        this.mine = mine;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_payment_detail_item, parent, false);
        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        final Payment payment = this.paymentList.get(position);
        Double total = Math.abs(payment.getTotal());
        String totalFmt = PaymeApplication.getFormatters().formatMoney(total);
        holder.dateEditText.setText(PaymeApplication.getFormatters().formatDate(payment.getDate()));
        holder.totalEditText.setText(this.currency + " " + totalFmt);


        if (mine) {
            if (payment.getTotal() > 0) {

                holder.totalEditText.setTextColor(holder.view.getResources().getColor(R.color.negativeDebt));
                holder.transactionEditText.setText(R.string.payment_te_presta);
                holder.transactionEditText.setTextColor(holder.view.getResources().getColor(R.color.negativeDebt));
                holder.dateEditText.setTextColor(holder.view.getResources().getColor(R.color.negativeDebt));
            } else {
                holder.transactionEditText.setText(R.string.payment_pagas);
            }
        } else {
            if (payment.getTotal() > 0) {
                holder.totalEditText.setTextColor(holder.view.getResources().getColor(R.color.negativeDebt));
                holder.transactionEditText.setText(R.string.payment_le_prestas);
                holder.transactionEditText.setTextColor(holder.view.getResources().getColor(R.color.negativeDebt));
                holder.dateEditText.setTextColor(holder.view.getResources().getColor(R.color.negativeDebt));
            } else {
                holder.transactionEditText.setText(R.string.payment_te_paga);
            }
        }



    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public void changeDataSet(List<Payment> paymentList) {
        this.paymentList = paymentList;
        notifyDataSetChanged();
    }
}
