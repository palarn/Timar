package com.example.freyds.timar3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


//þetta er gagnagrunnurinn
public class DatabaseAdapter
{
    Helper helper;
    public DatabaseAdapter(Context context)
    {
        helper = new Helper(context);
    }

    //fall t.þ.a. bæta gildum inní JOB_INFO töfluna, þegar notandinn bætir inn nýrri vinnu
    public long insertData(String name, String salary1, String salary2, String timeselected)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.NAME, name);
        contentValues.put(Helper.SALARY1, salary1);
        contentValues.put(Helper.SALARY2, salary2);
        contentValues.put(Helper.SALARYTIME, timeselected);
        long id = db.insert(Helper.JOB_INFO, null, contentValues);
        return id;
    }

    //fall t.þ.a. bæta gildum inní WORK_LOG töfluna, þegar notandinn stimplar sig inn/út
    //ekkert verið að nota þetta enn sem komið er
    public long insertWorkLog(String date, String name, String hours)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.DATE, date);
        contentValues.put(Helper.JOB_NAME, name);
        contentValues.put(Helper.HOURS, hours);
        long id = db.insert(Helper.WORK_LOG, null, contentValues);
        return id;
    }

    public int deleteJob(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {name};
        int count = db.delete(Helper.JOB_INFO, Helper.NAME+" =? ", whereArgs);
        return count;
    }

    public int changeJob(String old_name, String new_name, String new_salary1, String new_salary2)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.NAME, new_name);
        contentValues.put(Helper.SALARY1, new_salary1);
        contentValues.put(Helper.SALARY2, new_salary2);

        String[] whereArgs = {old_name};
        int count = db.update(Helper.JOB_INFO, contentValues, Helper.NAME+" =? ", whereArgs);
        return count;
    }
    //skilar nöfnunum á vinnunum sem eru skráðar
    public String[] getNames()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Helper.NAME};
        Cursor cursor = db.query(Helper.JOB_INFO, columns, null, null, null, null, null);
        List<String> list = new ArrayList<String>();
        while(cursor.moveToNext())
        {
            String name = cursor.getString(0);
            list.add(name);
        }
        String[] vinnur = new String[list.size()];
        list.toArray(vinnur);
        return vinnur;
    }



    //skilar dagvinnu- og yfirvinnukaupi fyrir vinnuna job
    public String[] getSalary(String job)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Helper.NAME, Helper.SALARY1, Helper.SALARY2, Helper.SALARYTIME};
        Cursor cursor = db.query(Helper.JOB_INFO, columns, Helper.NAME + " = '" + job + "'", null, null, null, null);   // => sql skipunin: select salary1, salary2 where name='job'

        List<String> list = new ArrayList<String>();
        while(cursor.moveToNext())
        {
            String salary1 = cursor.getString(1);
            String salary2 = cursor.getString(2);
            String salarytime = cursor.getString(3);
            list.add(salary1);
            list.add(salary2);
            list.add(salarytime);
        }
        String[] salary = new String[list.size()];
        list.toArray(salary);
        return salary;
    }

    static class Helper extends SQLiteOpenHelper
    {
        //tafla með upplýsingum um vinnuna
        //dálkar: nafn vinnu, dagvinnulaun, yfirvinnulaun
        private static final String DATABASE_NAME = "database";
        private static final String JOB_INFO = "JOBINFO";
        private static final String NAME = "Name";
        private static final String SALARY1 = "Salary1";
        private static final String SALARY2 = "Salary2";
        private static final String SALARYTIME = "Salarytime";
        private static final String CREATE_TABLE = "CREATE TABLE " + JOB_INFO + "("+ NAME + " VARCHAR(255) PRIMARY KEY,"+ SALARY1 + " INTEGER(255)," + SALARY2 + " INTEGER(255));";

        //private static final String ALTER_TABLE = "ALTER TABLE " + JOB_INFO + "ADD COLUMN " + SALARYTIME + "VARCHAR(255);";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + JOB_INFO;

        //tafla með upplýsingum um hvenær unnið er
        //dálkar: dagsetning, nafn vinnu, klukkustundir sem eru skráðar fyrir þessa ákveðnu dagsetningu

        //þetta er ekki komið í notkun ennþá... notað í Vinna.java
        private static final String WORK_LOG = "WORKLOG";
        private static final String DATE = "Date";
        private static final String JOB_NAME = "Name";
        private static final String HOURS = "Hours";
        private static final String CREATE_WORKLOG = "CREATE TABLE " + WORK_LOG + "(" + DATE + " DATE," + JOB_NAME + " VARCHAR(255)," + HOURS + " INTEGER(255));";
        private Context context;
        private static final int DATABASE_VERSION = 2;

        public Helper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            //try {
            db.execSQL(CREATE_TABLE);
        /*}
        catch (SQLException e)
        {
            Message.message(context, ""+e);
        }*/
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                db.execSQL("ALTER TABLE JOBINFO ADD COLUMN SALARYTIME VARCHAR(255)");
            }
        }
    }
}
