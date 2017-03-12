package apps.digitakpark.payme.detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import apps.digitakpark.payme.PaymeApplication;
import apps.digitakpark.payme.model.Debt;
import payme.pe.apps.digitakpark.payme.R;

public class DebtDetailActivityAdapter extends RecyclerView.Adapter<DetailViewHolder> {

    private List<Debt> debtList;
    private Debt selectedDebt;

    public DebtDetailActivityAdapter(List<Debt> debtList) {
        this.debtList = debtList;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_debt_detail_item, parent, false);
        DetailViewHolder holder = new DetailViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        final Debt debt = this.debtList.get(position);
        String date = PaymeApplication.getFormatters().formatDate(debt.getDate());
        holder.conceptEditText.setText(debt.getConcept());
        holder.totalEditText.setText(debt.getCurrency() + " " + debt.getTotal());
        holder.dateTextView.setText(date);
        if (debt.getLimit() != null && debt.getLimit() != 0) {
            holder.limitDateTextView.setText(PaymeApplication.getFormatters().formatDate(debt.getLimit()));
            holder.limitDateTextView.setVisibility(View.VISIBLE);
            holder.notifImageView.setVisibility(View.VISIBLE);
        }
        if (debt.isMine()) {
            holder.totalEditText.setTextColor(holder.view.getResources().getColor(R.color.negativeDebt));
        } else {
            holder.totalEditText.setTextColor(holder.view.getResources().getColor(R.color.positiveDebt));
        }
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectedDebt = debt;
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.debtList.size();
    }

    public void changeDataSet(List<Debt> debtList) {
        this.debtList = debtList;
        notifyDataSetChanged();
    }

    public Debt getSelectedDebt() {
        return this.selectedDebt;
    }

    public void removeItem(Long id) {
        Debt debt = new Debt();
        debt.setId(id);
        int index = debtList.indexOf(debt);
        if (index != -1) {
            debtList.remove(index);
            notifyDataSetChanged();
        }
    }

    public Double getTotal() {
        Double total = 0D;
        for(Debt debt: this.debtList) {
            total += debt.getTotal();
        }
        return total;
    }

}
