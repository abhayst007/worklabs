package tech.codedroid.worklab.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "worklabs.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS saved_jobs (_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT ,company TEXT ,location TEXT ,description TEXT ,job_url TEXT, salary TEXT DEFAULT 'not specified', contract_time TEXT, label TEXT )";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
