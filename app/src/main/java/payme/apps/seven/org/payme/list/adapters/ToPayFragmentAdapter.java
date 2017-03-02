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

public class ToPayFragmentAdapter extends RecyclerView.Adapter<ToPayFragmentAdapter.MyPayVH> {

    private List<DebtHeader> debtHeaderList;
    private OnClickDebtHeaderListener listener;

    public ToPayFragmentAdapter(List<DebtHeader> debtHeaderList, OnClickDebtHeaderListener listener) {
        this.debtHeaderList = debtHeaderList;
        this.listener = listener;
    }

    @Override
    public MyPayVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_to_pay_item, parent, false);
        return new MyPayVH(itemView);
    }

    @Override
    public void onBindViewHolder(MyPayVH holder, int position) {
        final DebtHeader debtHeader = this.debtHeaderList.get(position);
        holder.nameEditText.setText(debtHeader.getName());
        holder.totalEditText.setText(debtHeader.getCurrency() + " " + debtHeader.getTotal());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(debtHeader);
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

    public class MyPayVH extends RecyclerView.ViewHolder {
        public TextView nameEditText;
        public TextView totalEditText;
        public View view;

        public MyPayVH(View view) {
            super(view);
            this.view = view;
            this.nameEditText = (TextView) view.findViewById(R.id.fragment_to_pay_item_name_edittext);
            this.totalEditText = (TextView) view.findViewById(R.id.fragment_to_pay_item_total_edittext);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onClick();
//                }
//            });
        }
    }

}
