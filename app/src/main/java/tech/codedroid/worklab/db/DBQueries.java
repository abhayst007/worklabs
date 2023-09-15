package tech.codedroid.worklab.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;

public class DBQueries {

    public boolean insertSavedJob(Context context, HashMap<String, String> vals) {
        boolean flag = false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("title", vals.get("title"));
            values.put("company", vals.get("company"));
            values.put("location", vals.get("location"));
            values.put("description", vals.get("description"));
            values.put("job_url", vals.get("job_url"));
            values.put("salary", vals.get("salary"));
            values.put("contract_time", vals.get("contract_time"));
            values.put("label", vals.get("label"));
            long newRowId = db.insert("saved_jobs", null, values);

            if (newRowId != -1) {
                flag = true;
                Log.e("TAG", "insertSavedJob: ============================>" + "DB Values Inserted");
            }

        } catch (Exception e) {
            Log.e("TAG", "insertSavedJob: =========>", e);
        } finally {
            db.close();
        }
        return flag;
    }
}
