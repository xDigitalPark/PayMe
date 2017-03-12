package apps.digitakpark.payme.list.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import apps.digitakpark.payme.list.common.ui.OnClickDebtHeaderListener;
import apps.digitakpark.payme.model.DebtHeader;
import payme.pe.apps.digitakpark.payme.R;

public class ToPayFragmentAdapter extends RecyclerView.Adapter<ToPayViewHolder> {

    private List<DebtHeader> debtHeaderList;
    private OnClickDebtHeaderListener listener;
    private DebtHeader selectedDebtHeader;

    public ToPayFragmentAdapter(List<DebtHeader> debtHeaderList, OnClickDebtHeaderListener listener) {
        this.debtHeaderList = debtHeaderList;
        this.listener = listener;
    }

    @Override
    public ToPayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_to_pay_item, parent, false);
        return new ToPayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToPayViewHolder holder, int position) {
        final DebtHeader debtHeader = this.debtHeaderList.get(position);
        holder.nameEditText.setText(debtHeader.getName());
        holder.totalEditText.setText(debtHeader.getCurrency() + " " + debtHeader.getTotal());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(debtHeader);
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectedDebtHeader = debtHeader;
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.debtHeaderList.size();
    }

    public void changeDataSet(List<DebtHeader> debtHeaderList) {
        this.debtHeaderList = debtHeaderList;
        notifyDataSetChanged();
    }

    public DebtHeader getSelectedDebtHeader() {
        return selectedDebtHeader;
    }
}
