package com.example.freyds.timar3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Byr til SQL gagnagrunn sem heldur utan um mikilvaegar upplysingar, eydir einnig gognum ur gagnagrunni ef tharf
 * @author Anna Hafthorsdottir, Freydis Halldorsdottir, Helga Loa Kristjansdottir, Pall Arnar Palsson
 * @date 10. october 2014
 */

//þetta er gagnagrunnurinn
public class DatabaseAdapter
{
    Helper helper;
    public DatabaseAdapter(Context context)
    {
        helper = new Helper(context);
    }

    //fall t.þ.a. bæta gildum inní JOB_INFO töfluna, þegar notandinn bætir inn nýrri vinnu
    //JOB_INFO: Name, Salary1, Salary2, Salarytime
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

    //fall t.þ.a. bæta gildum inní WORK_LOG töfluna, þegar notandinn stimplar sig út
    //WORK_LOG: Date, Name, In, Out, Total hours, Salary
    public long insertWorkLog(String date, String name, String in, String out, String hours, String minutes, String salary)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.DATE, date);
        contentValues.put(Helper.JOB_NAME, name);
        contentValues.put(Helper.JOB_IN, in);
        contentValues.put(Helper.JOB_OUT, out);

        String hours_minutes = hours + " klst." + minutes + " mín.";
        contentValues.put(Helper.HOURS, hours_minutes);

        double time = Double.parseDouble(hours) + (Double.parseDouble(minutes) / 60);
        int salary2 = (int)(time * Integer.parseInt(salary));
        contentValues.put(Helper.AMOUNT, salary2);

        long id = db.insert(Helper.WORK_LOG, null, contentValues);
        return id;
    }

    //fall t.þ.a. bæta við gildum í TEMP_TABLE þegar notandi stimplar sig inn
    //TEMP_TABLE: Date, Name, In, Out, Total hours
    //(setjum núna inn dagsetningu, nafn og tíma sem stimplað er inn)
    //töpum þannig ekki gögnum ef það slökknar t.d. á símanum eða e-ð
    public long insertInTemp(String date, String name, String in) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.TEMP_DATE, date);
        contentValues.put(Helper.TEMP_NAME, name);
        contentValues.put(Helper.TEMP_IN, in);

        long id = db.insert(Helper.TEMP_TABLE, null, contentValues);
        return id;
    }

    //fall t.þ.a. bæta við gildum í TEMP_TABLE þegar notandi stimplar sig út
    //(bætum núna við tíma sem stimplað er út og fjölda klukkustunda sem unnar voru)
    public long insertOutTemp(String name, String out) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.TEMP_OUT, out);

        String[] start = getStartTime();
        String end = out;                                               //strengur á forminu HH:MM

        String[] startarray = start[0].split(":");                      //startarray[1] = minutes, startarray[0] = hours
        String[] endarray = end.split(":");
        int temp_hours = Integer.parseInt(endarray[0]) - Integer.parseInt(startarray[0]);
        int temp_minutes = Integer.parseInt(endarray[1]) - Integer.parseInt(startarray[1]);
        int hours;
        int minutes;
        if (temp_minutes < 0) {
            temp_hours = temp_hours * 60 + temp_minutes;
            hours = temp_hours / 60;
            minutes = temp_hours % 60;
        }
        else {
            hours = temp_hours;
            minutes = temp_minutes;
        }
        contentValues.put(Helper.TOTAL_HOURS, hours);
        contentValues.put(Helper.TOTAL_MINUTES, minutes);

        String[] whereArgs = {name};
        int count = db.update(Helper.TEMP_TABLE, contentValues, Helper.TEMP_NAME+" =? ", whereArgs);
        return count;
    }

    public String[] getStartTime() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] column = {Helper.TEMP_IN};
        Cursor cursor = db.query(Helper.TEMP_TABLE, column, null, null, null, null, null);   // => sql skipunin: select salary1, salary2 where name='job'
        List<String> list = new ArrayList<String>();
        while(cursor.moveToNext())
        {
            String start = cursor.getString(0);
            list.add(start);
        }
        String[] starttime = new String[list.size()];
        list.toArray(starttime);
        return starttime;
    }

    public int deleteJob(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {name};
        int count = db.delete(Helper.JOB_INFO, Helper.NAME+" =? ", whereArgs);
        return count;
    }

    public int deleteTemp() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(Helper.TEMP_TABLE, null, null);
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

    //skilar upplýsingum úr temp töflu
    public String[] getTempInfo() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Helper.TEMP_DATE, Helper.TEMP_NAME, Helper.TEMP_IN, Helper.TEMP_OUT, Helper.TOTAL_HOURS, Helper.TOTAL_MINUTES};
        Cursor cursor = db.query(Helper.TEMP_TABLE, columns, null, null, null, null, null);
        List<String> list = new ArrayList<String>();

        while(cursor.moveToNext()) {
            String date = cursor.getString(0);
            String name = cursor.getString(1);
            String t_in = cursor.getString(2);
            String t_out = cursor.getString(3);
            String hours = cursor.getString(4);
            String minutes = cursor.getString(5);
            list.add(date);
            list.add(name);
            list.add(t_in);
            list.add(t_out);
            list.add(hours);
            list.add(minutes);
        }
        String[] info = new String[list.size()];
        list.toArray(info);
        return info;
    }

    public String makePlaceholders(int len)
    {
        if (len < 1) {
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public String[] getWorklogInfo(String[] jobs, String dateFrom, String dateTo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "SELECT * FROM WORKLOG "
                + "WHERE Date BETWEEN '" + dateFrom +
                "' AND '" + dateTo +
                "' AND Name IN (" + makePlaceholders(jobs.length) + ")";

        Cursor cursor = db.rawQuery(query, jobs);
        List<String> list = new ArrayList<String>();
        while(cursor.moveToNext())
        {
            String date = cursor.getString(0);
            String name = cursor.getString(1);
            String job_in = cursor.getString(2);
            String job_out = cursor.getString(3);
            String hours = cursor.getString(4);
            String amount = cursor.getString(5);
            list.add(date);
            list.add(name);
            list.add(job_in);
            list.add(job_out);
            list.add(hours);
            list.add(amount);
        }
        String[] returnArray = new String[list.size()];
        list.toArray(returnArray);
        return returnArray;
    }

    public String[] getJobInfo(String date, String job, String job_in) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Helper.DATE, Helper.JOB_NAME, Helper.JOB_IN, Helper.JOB_OUT, Helper.HOURS, Helper.AMOUNT};
        String selection = Helper.DATE + " = '" + date + "' AND " + Helper.JOB_NAME + " = '" + job + "' AND " + Helper.JOB_IN + " = '" + job_in + "'";

        Cursor cursor  = db.query(Helper.WORK_LOG, columns, selection, null, null, null, null);
        List<String> list = new ArrayList<String>();
        while(cursor.moveToNext()) {
            String d = cursor.getString(0);
            String name = cursor.getString(1);
            String in = cursor.getString(2);
            String out = cursor.getString(3);
            String hours = cursor.getString(4);
            String amount = cursor.getString(5);
            list.add(d);
            list.add(name);
            list.add(in);
            list.add(out);
            list.add(hours);
            list.add(amount);
        }
        String[] info = new String[list.size()];
        list.toArray(info);
        return info;
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
        private static final String CREATE_TABLE = "CREATE TABLE " + JOB_INFO + "("+ NAME + " VARCHAR(255) PRIMARY KEY,"+ SALARY1
                + " INTEGER(255)," + SALARY2 + " INTEGER(255));";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + JOB_INFO;

        //tafla með upplýsingum um hvenær unnið er
        //dálkar: dagsetning, nafn vinnu, klukkustundir sem eru skráðar fyrir þessa ákveðnu dagsetningu
        private static final String WORK_LOG = "WORKLOG";
        private static final String DATE = "Date";
        private static final String JOB_NAME = "Name";
        private static final String JOB_IN = "Job_In";
        private static final String JOB_OUT = "Job_Out";
        private static final String HOURS = "Hours";
        private static final String AMOUNT = "Amount";
        private static final String CREATE_WORKLOG = "CREATE TABLE " + WORK_LOG + "(" + DATE + " VARCHAR(255),"
                + JOB_NAME + " VARCHAR(255)," + JOB_IN + " VARCHAR(255)," + JOB_OUT + " VARCHAR(255),"
                + HOURS + " VARCHAR(255)," + AMOUNT
                + " INTEGER(255), PRIMARY KEY(" + DATE + "," + JOB_NAME + "," + JOB_IN + "));";

        //þetta er temp tafla sem inniheldur alltaf bara eina færslu
        //gerir það að verkum að við getum stimplað okkur inn, farið úr appinu og stimplað okkur út án þess að gögnin tapist
        //þegar stimplað er út sendum við gögnin inn í worklog töfluna og einum þessari einu færslu sem var
        //gögn sem eru send: Nafn vinnu, dagsetningu, hvenær stimplað var inn, hvenær stimplað var út og fjöldi klukkustunda + mínútna sem var unnið
        private static final String TEMP_TABLE = "TEMP";
        private static final String TEMP_DATE = "TempDate";
        private static final String TEMP_NAME  = "TempName";
        private static final String TEMP_IN = "TempIn";
        private static final String TEMP_OUT = "TempOut";
        private static final String TOTAL_HOURS = "TotalHours";
        private static final String TOTAL_MINUTES = "TotalMinutes";
        private static final String CREATE_TEMP = "CREATE TABLE " + TEMP_TABLE + "(" + TEMP_DATE + " VARCHAR(255)," + TEMP_NAME + " VARCHAR(255) PRIMARY KEY,"
                + TEMP_IN + " VARCHAR(255)," + TEMP_OUT + " VARCHAR(255)," + TOTAL_HOURS + " INTEGER(255),"
                + TOTAL_MINUTES + " INTEGER(255));";

        private Context context;
        private static final int DATABASE_VERSION = 23;

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
            db.execSQL(CREATE_WORKLOG);
            db.execSQL(CREATE_TEMP);
        /*}
        catch (SQLException e)
        {
            Message.message(context, ""+e);
        }*/
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + WORK_LOG);
                db.execSQL("DROP TABLE IF EXISTS " + TEMP_TABLE);
                db.execSQL(CREATE_WORKLOG);
                db.execSQL(CREATE_TEMP);
            }
        }
    }
}
