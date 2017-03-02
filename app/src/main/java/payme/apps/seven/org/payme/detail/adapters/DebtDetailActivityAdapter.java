package payme.apps.seven.org.payme.detail.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.model.Debt;

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
        holder.conceptEditText.setText(debt.getConcept());
        holder.totalEditText.setText(debt.getCurrency() + " " + debt.getTotal());
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
