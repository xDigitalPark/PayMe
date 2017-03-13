package apps.digitakpark.payapp.repositories;

import android.content.ContentValues;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.lib.data.DatabaseAdapter;
import apps.digitakpark.payapp.model.Balance;
import apps.digitakpark.payapp.events.CreateDebtEvent;
import apps.digitakpark.payapp.model.Contact;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;

public class CreateDebtRepositoryImpl implements CreateDebtRepository {

    private EventBus eventBus;
    private DatabaseAdapter database;
    private DebtLookupRepository debtLookupRepository;

    public CreateDebtRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.database = PaymeApplication.getDatabaseInstance();
        this.debtLookupRepository = new DebtLookupRepositoryImpl();
    }

    @Override
    public void createDebt(DebtHeader debtHeader, Debt debt) {
        boolean debtHeaderAdded = createDebtHeader(debtHeader),
                debtAdded = createDebt(debt),
                balanceAdded = createBalance(debtHeader, debt),
                contactUpserted = upsertContact(debtHeader);
        if(debtHeaderAdded && debtAdded && balanceAdded && contactUpserted) {
            CreateDebtEvent event = new CreateDebtEvent();
            event.setMessage("Header creado");
            event.setDebt(debt);
            event.setDebtHeader(debtHeader);
            event.setStatus(CreateDebtEvent.DEBT_CREATED);
            eventBus.post(event);
        }
    }

    @Override
    public boolean createDebtHeader(DebtHeader debtHeader) {
        DebtHeader foundHeader = debtLookupRepository.lookupDebtHeader(debtHeader.getNumber(), debtHeader.isMine());
        if (foundHeader != null) {
            Double newTotal = foundHeader.getTotal() + debtHeader.getTotal();
            debtHeader.setTotal(newTotal);
        }
        ContentValues headerData = new ContentValues();
        headerData.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_NAME, debtHeader.getName());
        headerData.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_NUMBER, debtHeader.getNumber());
        headerData.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_CURRENCY, debtHeader.getCurrency());
        headerData.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_TOTAL, debtHeader.getTotal());
        headerData.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_MINE, debtHeader.isMine());
        if (foundHeader == null) {
            if (!debtHeader.isMine()) {
                return database.insertData(DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE, headerData);
            } else {
                return database.insertData(DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY, headerData);
            }
        } else {
            if (!debtHeader.isMine()) {
                return database.updateData(DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE, headerData, "number = ?", debtHeader.getNumber());
            } else {
                return database.updateData(DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY, headerData, "number = ?", debtHeader.getNumber());
            }
        }
    }

    @Override
    public boolean createDebt(Debt debt) {
        ContentValues headerData = new ContentValues();
        headerData.put(DatabaseAdapter.DEBT_TABLE_COL_CONCEPT, debt.getConcept());
        headerData.put(DatabaseAdapter.DEBT_TABLE_COL_NUMBER, debt.getNumber());
        headerData.put(DatabaseAdapter.DEBT_TABLE_COL_CURRENCY, debt.getCurrency());
        headerData.put(DatabaseAdapter.DEBT_TABLE_COL_TOTAL, debt.getTotal());
        headerData.put(DatabaseAdapter.DEBT_TABLE_COL_DATE, debt.getDate());
        headerData.put(DatabaseAdapter.DEBT_TABLE_COL_MINE, debt.isMine());
        headerData.put(DatabaseAdapter.DEBT_TABLE_COL_DATE_LIMIT, debt.getLimit());
        if (!debt.isMine()) {
            return database.insertData(DatabaseAdapter.DEBT_TABLE_TOCHARGE, headerData);
        } else {
            return database.insertData(DatabaseAdapter.DEBT_TABLE_TOPAY, headerData);
        }
    }

    @Override
    public boolean createBalance(DebtHeader debtHeader, Debt debt) {
        Balance foundBalance = debtLookupRepository.lookupBalance(debtHeader.getNumber());
        Double myTotal = 0D,
                partyTotal = 0D,
                total = 0D;
        String number = debtHeader.getNumber(),
               currency = debtHeader.getCurrency(),
               name = debtHeader.getName();
        if (foundBalance != null) {
            myTotal = foundBalance.getMyTotal();
            partyTotal = foundBalance.getPartyTotal();
            number = foundBalance.getNumber();
            currency = foundBalance.getCurrency();
            name = foundBalance.getName();
        }
        if (debt.isMine()) {
            myTotal = myTotal + debt.getTotal();
        } else {
            partyTotal = partyTotal + debt.getTotal();
        }
        total = partyTotal - myTotal;
        ContentValues balanceData = new ContentValues();
        balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_NUMBER, number);
        balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_NAME, name);
        balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_CURRENCY, currency);
        balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_MY_TOTAL, myTotal);
        balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_PARTY_TOTAL, partyTotal);
        balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_TOTAL, total);
        balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_MINE, total < 0);

        if (foundBalance == null) {
            return database.insertData(DatabaseAdapter.BALANCE_TABLE, balanceData);
        } else {
            return database.updateData(DatabaseAdapter.BALANCE_TABLE, balanceData, "number = ?", number);
        }
    }

    private boolean upsertContact(DebtHeader debtHeader) {
        Contact foundContact = debtLookupRepository.lookupContact(debtHeader.getNumber());
        if (foundContact == null) {
            ContentValues contactData = new ContentValues();
            contactData.put(DatabaseAdapter.CONTACT_NUMBER, debtHeader.getNumber());
            contactData.put(DatabaseAdapter.CONTACT_NAME, debtHeader.getName());
            return database.insertData(DatabaseAdapter.CONTACT_TABLE, contactData);
        }
        return true;
    }

}
