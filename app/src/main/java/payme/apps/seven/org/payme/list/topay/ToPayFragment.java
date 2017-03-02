package payme.apps.seven.org.payme.list.topay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.detail.ui.DebtDetailActivity;
import payme.apps.seven.org.payme.list.adapters.ToPayFragmentAdapter;
import payme.apps.seven.org.payme.list.common.ListDebtPresenter;
import payme.apps.seven.org.payme.list.common.ui.OnClickDebtHeaderListener;
import payme.apps.seven.org.payme.list.common.ui.ListDebtView;
import payme.apps.seven.org.payme.model.DebtHeader;

public class ToPayFragment extends Fragment implements ListDebtView, OnClickDebtHeaderListener {
    @BindView(R.id.fragment_to_pay_total)
    TextView fragmentToPayTotal;
    private List<DebtHeader> debtHeaderList;

    @BindView(R.id.fragment_topay_recyclerview)
    RecyclerView recyclerView;
    ToPayFragmentAdapter adapter;
    private ListDebtPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_pay, container, false);
        ButterKnife.bind(this, view);
        presenter = new ToPayDebtPresenterImpl(this);
        this.adapter = new ToPayFragmentAdapter(this.debtHeaderList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        presenter.onCreate();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        presenter.sendRetrieveDebtHeadersAction(true);
        super.onStart();
    }

    @Override
    public void onClick(DebtHeader debtHeader) {
        Intent intent = new Intent(getActivity(), DebtDetailActivity.class);
        intent.putExtra("debt_name", debtHeader.getName());
        intent.putExtra("debt_number", debtHeader.getNumber());
        intent.putExtra("debt_mine", debtHeader.isMine());
        startActivity(intent);
    }

    @Override
    public void onLoadDebtHeaderList(List<DebtHeader> debtHeaderList) {
        adapter.changeDataSet(debtHeaderList);
    }

    @Override
    public void onLoadTotal(Double total) {
        fragmentToPayTotal.setText("Total: S/." + total);
    }

    @Override
    public void onDebtHeaderSelected(DebtHeader debtHeader) {

    }

    @Override
    public void onHeaderDeleted() {

    }
}
