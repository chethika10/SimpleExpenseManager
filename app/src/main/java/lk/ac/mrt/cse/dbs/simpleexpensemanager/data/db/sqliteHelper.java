package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class sqliteHelper extends SQLiteOpenHelper {
    private static sqliteHelper sqliteHelper;

    public static sqliteHelper getSqliteHelper() {
        if(sqliteHelper == null){
            sqliteHelper=new sqliteHelper(null);
        }
        return sqliteHelper;
    }

    public sqliteHelper(@Nullable Context context) {
        super(context, "DB", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists accounts( accountNo varchar(255) primary key not null,bankName varchar(255),accountHolderName varchar(255)," +
                "balance float(53));");
        sqLiteDatabase.execSQL("create table if not exists transactions( id int primary key autoincrement ,date varchar(255), expenseType varchar(255), amount float(53), foreign key (accountNo) references accounts (accountNo)" +
                " );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists accounts;");
        sqLiteDatabase.execSQL("drop table if exists transactions;");
        onCreate(sqLiteDatabase);
    }
}
