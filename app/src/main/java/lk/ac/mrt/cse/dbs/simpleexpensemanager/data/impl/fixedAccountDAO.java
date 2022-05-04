package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db.SqliteHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class fixedAccountDAO implements AccountDAO {
    private SqliteHelper sqliteHelper ;
    public fixedAccountDAO(){
        sqliteHelper=SqliteHelper.getSqliteHelper();

    }

    @Override
    public List<String> getAccountNumbersList() {
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("select accountNo from accounts;",null);
        List<String> account_nums = new LinkedList<>();
        for (cursor.moveToFirst();!cursor.isAfterLast(); cursor.moveToLast()){
            String accountNo=cursor.getString(cursor.getColumnIndex("accountNo"));
            account_nums.add(accountNo);
        }
        return account_nums;
    }

    @Override
    public List<Account> getAccountsList() {
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("select * from accounts;",null);
        List<Account> accounts= new LinkedList<>();
        for (cursor.moveToFirst();!cursor.isAfterLast(); cursor.moveToLast()){
            Account account=new Account("","","",0);
            account.setAccountNo(cursor.getString(cursor.getColumnIndex("accountNo")));
            account.setAccountHolderName(cursor.getString(cursor.getColumnIndex("accountHolderName")));
            account.setBankName(cursor.getString(cursor.getColumnIndex("bankName")));
            account.setBalance(cursor.getDouble(cursor.getColumnIndex("balance")));
            accounts.add(account);

        }
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("select * from accounts where accountNo="+accountNo,null);
        Account account=new Account("","","",0);
        cursor.moveToFirst();
        account.setAccountNo(cursor.getString(cursor.getColumnIndex("accountNo")));
        account.setAccountHolderName(cursor.getString(cursor.getColumnIndex("accountHolderName")));
        account.setBankName(cursor.getString(cursor.getColumnIndex("bankName")));
        account.setBalance((double) cursor.getFloat(cursor.getColumnIndex("balance")));
        return account;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("accountNo",account.getAccountNo());
        contentValues.put("accountHolderName",account.getAccountHolderName());
        contentValues.put("bankName",account.getBankName());
        contentValues.put("balance",(float)account.getBalance());

        sqLiteDatabase.insert("accounts",null,contentValues);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getWritableDatabase();
        String[] no={accountNo};
        sqLiteDatabase.delete("accounts","accountNo=?",no);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account=getAccount(accountNo);
        if(expenseType==ExpenseType.EXPENSE){
            amount=account.getBalance()-amount;
        }
        else {
            amount=account.getBalance()+amount;
        }
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("balance",amount);
        sqLiteDatabase.update("accounts",contentValues,"accountNo=?",new String[]{accountNo});

    }
}
