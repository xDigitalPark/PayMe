package apps.digitakpark.payapp.balance.ui;

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

import apps.digitakpark.payapp.balance.BalancePresenter;
import apps.digitakpark.payapp.balance.adapters.BalanceFragmentAdapter;
import apps.digitakpark.payapp.balance.listeners.OnTabSelectedListener;
import apps.digitakpark.payapp.list.DividerItemDecorator;
import apps.digitakpark.payapp.model.Balance;
import butterknife.BindView;
import butterknife.ButterKnife;
import payme.pe.apps.digitakpark.payme.R;
import apps.digitakpark.payapp.balance.BalancePresenterImpl;

public class BalanceFragment extends Fragment implements BalanceView, OnTabSelectedListener {

    @BindView(R.id.fragment_balance_recyclerview)
    RecyclerView recyclerView;
    BalanceFragmentAdapter adapter;
    @BindView(R.id.fragment_balance_detail_total)
    TextView fragmentBalanceDetailTotal;
    private List<Balance> balanceList = new ArrayList<>();
    private BalancePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        ButterKnife.bind(this, view);

        this.adapter = new BalanceFragmentAdapter(this.balanceList);
        this.presenter = new BalancePresenterImpl(this);
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
        presenter.sendRetrieveBalancesAction();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLoadBalanceList(List<Balance> balanceList) {
        this.adapter.changeDataSet(balanceList);
    }

    @Override
    public void onLoadTotal(Double total) {
        fragmentBalanceDetailTotal.setText("Total   |   S/. " + total);
        if (total < 0)
            fragmentBalanceDetailTotal.setTextColor(getResources().getColor(R.color.negativeDebt));
        else
            fragmentBalanceDetailTotal.setTextColor(getResources().getColor(R.color.positiveDebt));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == BalanceView.CLOSE_DEBT) {
            onBalanceSelected(adapter.getSelectedBalance());
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBalanceSelected(Balance balance) {
        presenter.sendArchiveDebtBalance(balance);
    }

    @Override
    public void onBalanceArchived(Balance balance) {
        presenter.sendRetrieveBalancesAction();
    }

    @Override
    public void onResumeFragment() {
        if (presenter != null) {
            presenter.sendRetrieveBalancesAction();
        }
    }
}
