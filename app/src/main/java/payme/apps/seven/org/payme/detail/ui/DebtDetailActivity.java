package payme.apps.seven.org.payme.detail.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import payme.apps.seven.org.payme.R;
import payme.apps.seven.org.payme.create.ui.CreateDebtActivity;
import payme.apps.seven.org.payme.detail.DebtDetailPresenter;
import payme.apps.seven.org.payme.detail.DebtDetailPresenterImpl;
import payme.apps.seven.org.payme.detail.adapters.DebtDetailActivityAdapter;
import payme.apps.seven.org.payme.model.Debt;
import payme.apps.seven.org.payme.model.DebtHeader;

public class DebtDetailActivity extends AppCompatActivity implements DebtDetailView {

    @BindView(R.id.activity_debt_detail_recyclerview)
    RecyclerView recyclerView;
    DebtDetailActivityAdapter adapter;
    List<Debt> debtList = new ArrayList<>();
    @BindView(R.id.activity_debt_detail_total)
    TextView activityDebtDetailTotal;
    private Double total;
    private String number;
    private String name;
    private Debt debt;
    private boolean mine;
    private DebtDetailPresenter presenter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_detail);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        prepopActivity(extras);
        this.adapter = new DebtDetailActivityAdapter(this.debtList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fab = (FloatingActionButton) findViewById(R.id.activity_debt_detail_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToCreateDebtActivity(mine);
            }
        });

        this.presenter = new DebtDetailPresenterImpl(this);
        this.presenter.onCreate();
    }

    @Override
    protected void onStart() {
        this.presenter.sendRetrieveDebtsAction(this.number, this.mine);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void prepopActivity(Bundle extras) {
        name = extras.getString("debt_name");
        number = extras.getString("debt_number");
        mine = extras.getBoolean("debt_mine");
        setTitle(name);
        debt = new Debt();
        debt.setNumber(number);
        debt.setMine(mine);
    }

    @Override
    public void onLoadDebtList(List<Debt> debtList) {
        this.adapter.changeDataSet(debtList);
    }

    @Override
    public void navigateToCreateDebtActivity(boolean mine) {
        Intent intent = new Intent(getApplicationContext(), CreateDebtActivity.class);
        intent.putExtra("debt_mine", mine);
        intent.putExtra("debt_number", number);
        intent.putExtra("debt_name", name);
        startActivity(intent);
    }

    @Override
    public void onLoadTotal(Double total) {
        activityDebtDetailTotal.setText("Total: S/." + total);
    }

    @Override
    public void onDebtSelected(Debt debt, int actionId) {
        switch (actionId) {
            case DebtDetailView.CLOSE_DEBT:
                onCloseDebtOptionSelected(debt);
                break;
            case DebtDetailView.DELETE_DEBT:
                onDeleteDebtOptionSelected(debt);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onDebtSelected(adapter.getSelectedDebt(), item.getItemId());
        return super.onContextItemSelected(item);
    }


    @Override
    public void onCloseDebtOptionSelected(Debt debt) {
        presenter.sendCloseDebtAction(debt);
    }

    @Override
    public void onDeleteDebtOptionSelected(Debt debt) {
        presenter.sendDeleteDebtAction(debt);
    }

    @Override
    public void onDebtDeleted(Long id) {
        // to reduce total
        adapter.removeItem(id);
        presenter.sendRetrieveDebtsAction(debt.getNumber(), debt.isMine());
        Double total = adapter.getTotal();
        if (total == 0) {
            DebtHeader debtHeader = new DebtHeader();
            debtHeader.setNumber(debt.getNumber());
            debtHeader.setMine(debt.isMine());
            presenter.sendDeleteDetHeaderAction(debtHeader);
        }
    }

    @Override
    public void onDebtHeaderDeleted() {
        finish();
    }
}