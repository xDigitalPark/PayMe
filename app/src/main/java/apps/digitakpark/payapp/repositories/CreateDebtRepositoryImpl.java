package apps.digitakpark.payapp.repositories;

import android.content.ContentValues;
import android.provider.ContactsContract;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.lib.data.DatabaseAdapter;
import apps.digitakpark.payapp.model.Balance;
import apps.digitakpark.payapp.events.CreateDebtEvent;
import apps.digitakpark.payapp.model.Contact;
import apps.digitakpark.payapp.model.Debt;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.Payment;

public class CreateDebtRepositoryImpl implements CreateDebtRepository {

    private EventBus eventBus;
    private DatabaseAdapter database;
    private DebtLookupRepository debtLookupRepository;
    private ContactRepository contactRepository;


    public CreateDebtRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.database = PaymeApplication.getDatabaseInstance();
        this.debtLookupRepository = new DebtLookupRepositoryImpl();
        this.contactRepository = new ContactRepositoryImpl();
    }

    @Override
    public void createDebt(DebtHeader debtHeader, Debt debt) {
        boolean debtHeaderAdded = createDebtHeader(debtHeader),
                debtAdded = createDebt(debt),
                balanceAdded = createBalance(debtHeader, debt),
                contactUpserted = contactRepository.upsertContact(debtHeader.getNumber(), debtHeader.getName()),
                paymentCreated = true;



        if(debtHeaderAdded && debtAdded && balanceAdded && contactUpserted && paymentCreated) {
            CreateDebtEvent event = new CreateDebtEvent();
            event.setMessage("Deuda creada");
            event.setDebt(debt);
            event.setDebtHeader(debtHeader);
            event.setStatus(CreateDebtEvent.DEBT_CREATED);
            eventBus.post(event);
        }
    }

//    private boolean createPayment(Debt debt) {
//
//        ContentValues data = new ContentValues();
//        data.put(DatabaseAdapter.PAYMENTS_TABLE_COL_DEBT_ID, debt.getId());
//        data.put(DatabaseAdapter.PAYMENTS_TABLE_COL_NUMBER, debt.getNumber());
//        data.put(DatabaseAdapter.PAYMENTS_TABLE_COL_TOTAL, debt.getTotal());
//        data.put(DatabaseAdapter.PAYMENTS_TABLE_COL_DATE, debt.getDate());
//        data.put(DatabaseAdapter.PAYMENTS_TABLE_COL_MINE, debt.isMine());
//
//        return database.insertData(DatabaseAdapter.PAYMENTS_TABLE, data);
//    }

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
        ContentValues debtData = new ContentValues();
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_CONCEPT, debt.getConcept());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_NUMBER, debt.getNumber());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_CURRENCY, debt.getCurrency());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_TOTAL, debt.getTotal());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_DATE, debt.getDate());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_MINE, debt.isMine());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_DATE_LIMIT, debt.getLimit());

        String table = (!debt.isMine())?DatabaseAdapter.DEBT_TABLE_TOCHARGE:DatabaseAdapter.DEBT_TABLE_TOPAY;
        Long id =  database.insertDataAndGetId(table, debtData);
        debt.setId(id);
        return id != -1;

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



    @Override
    public void editDebt(DebtHeader debtHeader, Debt debt, Double editPreTotal) {
         boolean debtUpdated = updateDebt(debt),
                 debtHeaderUpdated = updateDebtHeader(debt, editPreTotal);

        if(debtUpdated && debtHeaderUpdated) {
            CreateDebtEvent event = new CreateDebtEvent();
            event.setMessage("Header modificado");
            event.setDebt(debt);
            event.setDebtHeader(debtHeader);
            event.setStatus(CreateDebtEvent.DEBT_UPDATED);
            eventBus.post(event);
        }

    }

    private boolean updateDebt(Debt debt) {
        String debtTable = debt.isMine()?DatabaseAdapter.DEBT_TABLE_TOPAY:
                DatabaseAdapter.DEBT_TABLE_TOCHARGE;
        ContentValues debtData = new ContentValues();
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_CONCEPT, debt.getConcept());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_CURRENCY, debt.getCurrency());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_TOTAL, debt.getTotal());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_DATE, debt.getDate());
        debtData.put(DatabaseAdapter.DEBT_TABLE_COL_DATE_LIMIT, debt.getLimit());
        return database.updateData(debtTable, debtData, "id = ?", ""+debt.getId());
    }

    public boolean updateDebtHeader(Debt debt, Double editPreTotal) {
        String debtHeaderTable = debt.isMine()?DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY:
                DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE;

        DebtHeader debtHeader = debtLookupRepository.lookupDebtHeader(debt.getNumber(), debt.isMine());
        Double newTotal = debtHeader.getTotal() + (debt.getTotal() - editPreTotal);
        ContentValues headerData = new ContentValues();
        headerData.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_TOTAL, newTotal);
        boolean updated = database.updateData(debtHeaderTable, headerData, "number = ?", debtHeader.getNumber());
        if (updated) {
            Balance balance = debtLookupRepository.lookupBalance(debtHeader.getNumber());

            ContentValues balanceData = new ContentValues();
            if (debtHeader.isMine()) {
                balance.setMyTotal(newTotal);
                balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_MY_TOTAL, balance.getMyTotal());
            }
            else {
                balance.setPartyTotal(newTotal);
                balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_PARTY_TOTAL, balance.getPartyTotal());
            }
            balance.setTotal(balance.getPartyTotal() - balance.getMyTotal());
            balanceData.put(DatabaseAdapter.BALANCE_TABLE_COL_TOTAL, balance.getTotal());
            return database.updateData(DatabaseAdapter.BALANCE_TABLE, balanceData, "number = ?", balance.getNumber());
        }
        return false;

    }

}
