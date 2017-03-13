package apps.digitakpark.payapp.list.topay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import apps.digitakpark.payapp.balance.listeners.OnTabSelectedListener;
import apps.digitakpark.payapp.detail.ui.DebtDetailActivity;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.list.adapters.ToPayFragmentAdapter;
import apps.digitakpark.payapp.list.common.ListDebtPresenter;
import apps.digitakpark.payapp.list.common.ui.ListDebtView;
import apps.digitakpark.payapp.list.common.ui.OnClickDebtHeaderListener;
import apps.digitakpark.payapp.model.DebtHeader;
import butterknife.BindView;
import butterknife.ButterKnife;
import payme.pe.apps.digitakpark.payme.R;

public class ToPayFragment extends Fragment implements ListDebtView, OnClickDebtHeaderListener, OnTabSelectedListener {
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
        this.adapter = new ToPayFragmentAdapter(this.debtHeaderList, this);
        this.presenter = new ToPayDebtPresenterImpl(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new DividerItemDecorator(getActivity(), DividerItemDecorator.VERTICAL_LIST));
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
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
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
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == ListDebtView.TO_PAY_CLOSE_DEBT) {
            onDebtHeaderSelected(adapter.getSelectedDebtHeader());
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDebtHeaderSelected(DebtHeader debtHeader) {
        presenter.sendDeleteDebtHeaderAction(debtHeader);
    }

    @Override
    public void onHeaderDeleted() {
        presenter.sendRetrieveDebtHeadersAction(true);
    }

    @Override
    public void onResumeFragment() {
        presenter.sendRetrieveDebtHeadersAction(true);
    }
}
