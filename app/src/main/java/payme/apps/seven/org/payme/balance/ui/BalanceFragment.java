package payme.apps.seven.org.payme.balance.ui;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.balance.BalancePresenter;
import payme.apps.seven.org.payme.balance.BalancePresenterImpl;
import payme.apps.seven.org.payme.balance.adapters.BalanceFragmentAdapter;
import payme.apps.seven.org.payme.model.Balance;

public class BalanceFragment extends Fragment implements BalanceView {

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
        fragmentBalanceDetailTotal.setText("Total: S/." + total);
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
}
