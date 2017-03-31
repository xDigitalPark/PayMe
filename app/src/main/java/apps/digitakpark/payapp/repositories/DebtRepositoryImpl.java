package apps.digitakpark.payapp.repositories;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

import apps.digitakpark.payapp.PaymeApplication;
import apps.digitakpark.payapp.events.DebtDetailEvent;
import apps.digitakpark.payapp.lib.data.DatabaseAdapter;
import apps.digitakpark.payapp.lib.events.EventBus;
import apps.digitakpark.payapp.model.Contact;
import apps.digitakpark.payapp.model.DebtHeader;
import apps.digitakpark.payapp.events.ToChargeDebtListEvent;
import apps.digitakpark.payapp.events.ToPayDebtListEvent;
import apps.digitakpark.payapp.lib.events.GreenRobotEventBus;
import apps.digitakpark.payapp.model.Balance;

public class DebtRepositoryImpl implements DebtRepository {

    private DatabaseAdapter database;
    private EventBus eventBus;
    private DebtLookupRepository lookupRepository;
    private ContactRepository contactRepository;

    public DebtRepositoryImpl() {
        this.database = PaymeApplication.getDatabaseInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
        this.lookupRepository = new DebtLookupRepositoryImpl();
        this.contactRepository = new ContactRepositoryImpl();
    }

    @Override
    public void deleteDebtHeader(DebtHeader debtHeader, boolean removeChilds, boolean pushEvent) {
        String table = !debtHeader.isMine()?DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE:DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY;
        boolean debtHeaderDeleted = this.database.deleteData(table, "number = ?", debtHeader.getNumber()),
            childsDeleted = true,
            balanceUpdated = true; // TODO UPDATE BALANCE
        if(debtHeaderDeleted==true && removeChilds == true) {
            String debtTable = !debtHeader.isMine()?DatabaseAdapter.DEBT_TABLE_TOCHARGE:DatabaseAdapter.DEBT_TABLE_TOPAY;
            String query = "DELETE FROM " + debtTable + " WHERE number = '" + debtHeader.getNumber() + "'";
            database.executeSQL(query);
            int count = database.countData(debtTable, "number = '" + debtHeader.getNumber() + "'");
            childsDeleted = (count == 0);
            if (childsDeleted) {
                balanceUpdated = updateBalanceInDeleteDebtHeaderAction(debtHeader, pushEvent);
            }
        }

        if (removeChilds == false && debtHeaderDeleted == true && pushEvent == true) {
            DebtDetailEvent event = new DebtDetailEvent();
            event.setStatus(DebtDetailEvent.DEBT_HEADER_DELETED_OK);
            event.setMessage("OK");
            eventBus.post(event);
            return;
        }

        if (debtHeaderDeleted && childsDeleted && balanceUpdated && pushEvent == true) {
            if (!debtHeader.isMine()) {
                ToChargeDebtListEvent event = new ToChargeDebtListEvent();
                event.setStatus(ToChargeDebtListEvent.DEBT_HEADER_DELETED_OK);
                event.setMessage("OK");
                eventBus.post(event);
            } else {
                ToPayDebtListEvent event = new ToPayDebtListEvent();
                event.setStatus(ToPayDebtListEvent.DEBT_HEADER_DELETED_OK);
                event.setMessage("OK");
                eventBus.post(event);
            }

        }
    }

    private boolean updateBalanceInDeleteDebtHeaderAction(DebtHeader debtHeader, boolean pushEvent) {
        Balance balance = lookupRepository.lookupBalance(debtHeader.getNumber());
        if (!debtHeader.isMine()) {
            balance.setPartyTotal(balance.getPartyTotal() - debtHeader.getTotal());
        } else {
            balance.setMyTotal(balance.getMyTotal() - debtHeader.getTotal());
        }
        balance.setTotal(balance.getPartyTotal()- balance.getMyTotal());
        if (balance.getTotal() != 0D) {
            ContentValues data = new ContentValues();
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_MY_TOTAL, balance.getMyTotal());
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_PARTY_TOTAL, balance.getPartyTotal());
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_TOTAL, balance.getTotal());
            return database.updateData(DatabaseAdapter.BALANCE_TABLE, data, "number = ?", debtHeader.getNumber());
        } else {
            if (pushEvent == true) {
                return database.deleteData(DatabaseAdapter.BALANCE_TABLE, "number = ?", debtHeader.getNumber());
            }
        }
        return true;
    }

    /**
     *
     * @param currentNumber: the actual number to be changed
     * @param mine: is my debt?
     * @param number: the new number
     * @param name: the new name
     */
    @Override
    public void changeDebtContactLink(String currentNumber, boolean mine, String number, String name) {
        String tableDebtHeader = !mine?DatabaseAdapter.DEBT_HEADER_TABLE_TOCHARGE:DatabaseAdapter.DEBT_HEADER_TABLE_TOPAY;
        String tableDebt = !mine?DatabaseAdapter.DEBT_TABLE_TOCHARGE:DatabaseAdapter.DEBT_TABLE_TOPAY;
        boolean debtFound = false;
        ContentValues data = null;
        // Check if exists one registry with the given number
        data = new ContentValues();
        DebtHeader currentDebtHeader = lookupRepository.lookupDebtHeader(currentNumber, mine);
        DebtHeader debtHeaderFound = lookupRepository.lookupDebtHeader(number, mine);
        debtFound = debtHeaderFound != null;
        Double existingDebtHeaderTotal = 0D;

        if (debtFound == true) {
            // Update Existing DebtHeader total
            Double newTotal = debtHeaderFound.getTotal() + currentDebtHeader.getTotal();
            debtHeaderFound.setTotal(newTotal);
            data.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_TOTAL, newTotal);
            existingDebtHeaderTotal = newTotal;
        }
        // Update DebtHeader
        boolean debtHeaderUpdated = false,
                debtUpdated = false,
                balanceUpdated = false;
        data.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_NUMBER, number);
        data.put(DatabaseAdapter.DEBT_HEADER_TABLE_COL_NAME, name);
        if (debtFound == true) {
            // Update DebtHeader
            debtHeaderUpdated = database.updateData(tableDebtHeader, data, "number = ?", number);
            // Remove existing registry
            boolean deleted = database.deleteData(tableDebtHeader, "number = ?", currentNumber);
            if (!deleted) {
                // FIXME: Raise error
                debtHeaderUpdated = false;
            }
        } else {
            // Update non existing header
            debtHeaderUpdated = database.updateData(tableDebtHeader, data, "number = ?", currentNumber);
        }

        if (debtHeaderUpdated == true) {
            // Update Debt
            data = new ContentValues();
            data.put(DatabaseAdapter.DEBT_TABLE_COL_NUMBER, number);
            debtUpdated = database.updateData(tableDebt, data, "number = ?", currentNumber);
        }

        if (debtHeaderUpdated == true && debtUpdated == true) {
            data = new ContentValues();
            Balance balanceFound = lookupRepository.lookupBalance(number);
            if (balanceFound != null) {
                // Lookup Balance
                Double debtHeaderTotal = currentDebtHeader.getTotal();
                if (existingDebtHeaderTotal > 0)
                    debtHeaderTotal = existingDebtHeaderTotal;
                if (mine)
                    balanceFound.setMyTotal(debtHeaderTotal);
                else
                    balanceFound.setPartyTotal(debtHeaderTotal);
                balanceFound.computeTotal();
                data.put(DatabaseAdapter.BALANCE_TABLE_COL_MY_TOTAL, balanceFound.getMyTotal());
                data.put(DatabaseAdapter.BALANCE_TABLE_COL_PARTY_TOTAL, balanceFound.getPartyTotal());
                data.put(DatabaseAdapter.BALANCE_TABLE_COL_TOTAL, balanceFound.getTotal());
            }
            // Update Balance
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_NUMBER, number);
            data.put(DatabaseAdapter.BALANCE_TABLE_COL_NAME, name);

            if (balanceFound != null) {
                // Check if delete
                DebtHeader reverseDebtHeaderFound = lookupRepository.lookupDebtHeader(currentNumber, !mine);
                if (reverseDebtHeaderFound == null)
                    balanceUpdated = database.deleteData(DatabaseAdapter.BALANCE_TABLE, "number = ?", currentNumber);

                balanceUpdated = database.updateData(DatabaseAdapter.BALANCE_TABLE, data, "number = ?", number);

            } else {
                balanceUpdated = database.updateData(DatabaseAdapter.BALANCE_TABLE, data, "number = ?", currentNumber);
            }

            if (balanceUpdated == true) {
                // Send Event

                Contact contact = lookupRepository.lookupContact(number);
                if (contact == null) {
                    contactRepository.upsertContact(number, name);
                }

                Map<String, String> dataDet = new HashMap<>();
                dataDet.put("name", name);
                dataDet.put("number", number);
                DebtDetailEvent event = new DebtDetailEvent();
                event.setStatus(DebtDetailEvent.DEBT_DETAIL_RELINKED_OK);
                event.setData(dataDet);
                event.setMessage("OK");
                eventBus.post(event);
            }
        }

    }
}
