package payme.apps.seven.org.payme.balance.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.model.Balance;
import payme.apps.seven.org.payme.model.Debt;

public class BalanceFragmentAdapter extends RecyclerView.Adapter<BalanceViewHolder> {

    private List<Balance> balanceList;
    private Balance selectedBalance;

    public BalanceFragmentAdapter(List<Balance> balanceList) {
        this.balanceList = balanceList;
    }

    @Override
    public BalanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_balance_item, parent, false);
        return new BalanceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BalanceViewHolder holder, int position) {
        final Balance debt = this.balanceList.get(position);
        holder.nameEditText.setText(debt.getName());
        holder.totalEditText.setText(debt.getCurrency() + " " + debt.getTotal());
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectedBalance = debt;
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.balanceList.size();
    }

    public void changeDataSet(List<Balance> balanceList) {
        this.balanceList = balanceList;
        notifyDataSetChanged();
    }

    public Balance getSelectedBalance() {
        return selectedBalance;
    }

    public void removeItem(String number) {
        Balance balance = new Balance();
        balance.setNumber(number);
        int index = balanceList.indexOf(balance);
        if (index != -1) {
            balanceList.remove(index);
            notifyDataSetChanged();
        }
    }
}
