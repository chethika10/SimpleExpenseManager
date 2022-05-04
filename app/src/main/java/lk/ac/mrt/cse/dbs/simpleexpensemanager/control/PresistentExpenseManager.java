package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.fixedAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.fixedTransactionDAO;

public class PresistentExpenseManager extends ExpenseManager {
    public PresistentExpenseManager()  {
        try {
            setup();
        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() throws ExpenseManagerException {
        setAccountsDAO(new fixedAccountDAO());
        setTransactionsDAO(new fixedTransactionDAO());
    }
}
