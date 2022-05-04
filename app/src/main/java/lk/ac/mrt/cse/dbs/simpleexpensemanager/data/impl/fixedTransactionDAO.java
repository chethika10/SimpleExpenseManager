package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db.SqliteHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class fixedTransactionDAO implements TransactionDAO {
    private SqliteHelper sqliteHelper ;
    public fixedTransactionDAO(){
        sqliteHelper=SqliteHelper.getSqliteHelper();

    }


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        String dat=""+date.getDate()+"-"+date.getMonth()+"-"+date.getYear();
        String ty="";
        if(expenseType==ExpenseType.EXPENSE){
            ty="EXPENSE";
        }
        else {
            ty="INCOME";
        }
        contentValues.put("date",dat);
        contentValues.put("expenseType",ty);
        contentValues.put("accountNo",accountNo);
        contentValues.put("amount",(float)amount);

        sqLiteDatabase.insert("transactions",null,contentValues);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("select * from transactions;",null);
        List<Transaction> transactions= new LinkedList<>();

        for (cursor.moveToFirst();!cursor.isAfterLast(); cursor.moveToLast()){
            Transaction transaction=new Transaction(new Date(),"",ExpenseType.EXPENSE,0);

            Date date=new Date();
            String[] dat=cursor.getString(cursor.getColumnIndex("date")).split("-");
            date.setDate(Integer.parseInt(dat[0]));
            date.setMonth(Integer.parseInt(dat[1]));
            date.setYear(Integer.parseInt(dat[2]));

            String extype=cursor.getString(cursor.getColumnIndex("expenseType"));
            ExpenseType expenseType=ExpenseType.EXPENSE;
            if(extype.equals("INCOME")) {expenseType=ExpenseType.INCOME;}

            transaction.setAccountNo(cursor.getString(cursor.getColumnIndex("accountNo")));
            transaction.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
            transaction.setDate(date);
            transaction.setExpenseType(expenseType);
            transactions.add(transaction);

        }
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        SQLiteDatabase sqLiteDatabase= sqliteHelper.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("select * from transactions limit "+limit,null);
        List<Transaction> transactions= new LinkedList<>();

        for (cursor.moveToFirst();!cursor.isAfterLast(); cursor.moveToLast()){
            Transaction transaction=new Transaction(new Date(),"",ExpenseType.EXPENSE,0);

            Date date=new Date();
            String[] dat=cursor.getString(cursor.getColumnIndex("date")).split("-");
            date.setDate(Integer.parseInt(dat[0]));
            date.setMonth(Integer.parseInt(dat[1]));
            date.setYear(Integer.parseInt(dat[2]));

            String extype=cursor.getString(cursor.getColumnIndex("expenseType"));
            ExpenseType expenseType=ExpenseType.EXPENSE;
            if(extype.equals("INCOME")) {expenseType=ExpenseType.INCOME;}

            transaction.setAccountNo(cursor.getString(cursor.getColumnIndex("accountNo")));
            transaction.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
            transaction.setDate(date);
            transaction.setExpenseType(expenseType);
            transactions.add(transaction);

        }
        return transactions;
    }
}
