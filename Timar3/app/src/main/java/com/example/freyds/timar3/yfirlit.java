package com.example.freyds.timar3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;


public class yfirlit extends Activity implements
        android.widget.CompoundButton.OnCheckedChangeListener {

    ListView list_job;
    ArrayList<ListItem> array_job;
    ListAdapter adapter;
    DatabaseAdapter helper;
    DatePicker datePickerFrom;              //maxDate = í dag
    DatePicker datePickerTo;                //maxDate = í dag, minDate = sami dagur og datePickerFrom...
    Button get;
    List<String> job_names = new ArrayList<String>();
    String dateFrom;
    String dateTo;
    Calendar calendar = Calendar.getInstance();
    int maxYear = calendar.get(Calendar.YEAR);
    int maxMonth = calendar.get(Calendar.MONTH);
    int maxDay = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yfirlit);

        list_job = (ListView) findViewById(R.id.listView);
        helper = new DatabaseAdapter(this);
        displayList();

        datePickerFrom = (DatePicker) findViewById(R.id.datePicker);
        datePickerTo = (DatePicker) findViewById(R.id.datePicker2);

        /*datePickerFrom.init(maxYear, maxMonth, maxDay-1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateFromChanged(DatePicker datePickerFrom, int day, int month, int year)
            {

            }
        })

        datePickerTo.init(maxYear, maxMonth, maxDay, new D)
        public void onDateToChanged(DatePicker datePickerTo, int day, int month, int year)
        {

        }*/



        get = (Button) findViewById(R.id.get);
        helper = new DatabaseAdapter(this);
    }

    public String getDate(DatePicker datePicker)
    {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String date = (year + "-" + month + "-" + day);
        return date;
    }

    public void getOverview(View view)
    {
        Intent intent = new Intent(yfirlit.this, vinnu_yfirlit.class);
        String[] job_array = new String[job_names.size()];
        job_names.toArray(job_array);

        dateFrom = getDate(datePickerFrom);
        dateTo = getDate(datePickerTo);

        String strTo= dateTo.substring(0,4)+ dateTo.substring(5,7)+ dateTo.substring(8,10);
        int strToInt=Integer.parseInt(strTo);
        String strFrom= dateFrom.substring(0,4)+dateFrom.substring(5,7)+ dateFrom.substring(8,10);
        int strFromInt=Integer.parseInt(strFrom);

        if (job_array.length != 0)
        {
            if(strFromInt <= strToInt) {
                intent.putExtra("names", job_array);
                intent.putExtra("dateFrom", dateFrom);
                intent.putExtra("dateTo", dateTo);
                Toast.makeText(this, dateFrom + " " + dateTo + " " + job_array[0], Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Laga þarf dagsetningu", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Veldu vinnu", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayList() {
        array_job = new ArrayList<ListItem>();
        String[] data = helper.getNames();

        for(int i = 0; i < data.length; i++) {
            array_job.add(new ListItem(data[i]));
        }

        adapter = new ListAdapter(array_job, this);
        list_job.setAdapter(adapter);
    }

    //skilar lista af nöfnum á vinnum sem eru checked
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int pos = list_job.getPositionForView(buttonView);
        ListItem job;
        if (pos != ListView.INVALID_POSITION)
        {
            job = array_job.get(pos);
            job.setSelected(isChecked);

            if (isChecked)
            {
                job_names.add(job.getName());
            }
            else
            {
                if (job_names.contains(job.getName()))
                {
                    job_names.remove(job.getName());
                }
            }


            /*Toast.makeText(
                    this,
                    "Clicked on job: " + job.getName() + ". State: is "
                            + isChecked, Toast.LENGTH_SHORT).show();*/
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.yfirlit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.menu_yfirlit_back)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
