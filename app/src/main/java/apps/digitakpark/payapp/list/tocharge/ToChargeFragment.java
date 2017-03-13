package apps.digitakpark.payapp.list.tocharge;

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

import java.util.ArrayList;
import java.util.List;

import apps.digitakpark.payapp.detail.ui.DebtDetailActivity;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.list.adapters.ToChargeFragmentAdapter;
import apps.digitakpark.payapp.list.common.ui.OnClickDebtHeaderListener;
import apps.digitakpark.payapp.model.DebtHeader;
import butterknife.BindView;
import butterknife.ButterKnife;
import payme.pe.apps.digitakpark.payme.R;
import apps.digitakpark.payapp.list.common.ListDebtPresenter;
import apps.digitakpark.payapp.list.common.ui.ListDebtView;

public class ToChargeFragment extends Fragment implements ListDebtView, OnClickDebtHeaderListener {

    @BindView(R.id.fragment_tocharge_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_to_charge_total)
    TextView fragmentToChargeTotal;
    private List<DebtHeader> debtHeaderList = new ArrayList<>();
    private ToChargeFragmentAdapter adapter;
    private ListDebtPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_charge, container, false);
        ButterKnife.bind(this, view);
        this.adapter = new ToChargeFragmentAdapter(this.debtHeaderList, this);
        this.presenter = new ListDebtPresenterImpl(this);
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
        presenter.sendRetrieveDebtHeadersAction(false);
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
        fragmentToChargeTotal.setText("Total: S/." + total);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == ListDebtView.TO_CHARGE_CLOSE_DEBT) {
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
        presenter.sendRetrieveDebtHeadersAction(false);
    }
}

