package payme.apps.seven.org.payme.list.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.list.common.ui.OnClickDebtHeaderListener;
import payme.apps.seven.org.payme.model.DebtHeader;

public class ToChargeFragmentAdapter extends RecyclerView.Adapter<ToChargeViewHolder> {

    private OnClickDebtHeaderListener listener;
    private List<DebtHeader> debtHeaderList;
    private DebtHeader selectedDebtHeader;

    public ToChargeFragmentAdapter(List<DebtHeader> debtHeaderList, OnClickDebtHeaderListener listener) {
        this.debtHeaderList = debtHeaderList;
        this.listener = listener;
    }

    @Override
    public ToChargeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_to_charge_item, parent, false);
        return new ToChargeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToChargeViewHolder holder, int position) {
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

    public void changeDataSet(List<DebtHeader> debtHeaderList) {
        this.debtHeaderList = debtHeaderList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.debtHeaderList.size();
    }

    public DebtHeader getSelectedDebtHeader() {
        return selectedDebtHeader;
    }
}
