package com.example.freyds.timar3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class vinnu_yfirlit extends Activity {
    DatabaseAdapter helper;
    Bundle extras;
    List<String> job_in;

    ExListAdapter listAdapter;
    ExpandableListView listView;

    List<String> date_header;
    HashMap<String, List<String>> job_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinnu_yfirlit);

        helper = new DatabaseAdapter(this);
        extras = getIntent().getExtras();
        String[] names = extras.getStringArray("names");
        String dateFrom = extras.getString("dateFrom");
        String dateTo = extras.getString("dateTo");
        String[] results = helper.getWorklogInfo(names, dateFrom, dateTo);
        job_in = new ArrayList<String>();
        //results er fylki af niðurstöðum
        //results[0] = date, results[1] = name, results[2] = job_in...
        //results[6] = date, results[7] = name, results[8] = job_in o.s.frv.
        listView = (ExpandableListView) findViewById(R.id.expandableListView);
        populateList(results);

        listAdapter = new ExListAdapter(this, date_header, job_items);
        listView.setAdapter(listAdapter);

        //ef það er ýtt á vinnu
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(vinnu_yfirlit.this, dags_yfirlit.class);
                String date = date_header.get(groupPosition);
                String job = job_items.get(date_header.get(groupPosition)).get(childPosition);

                String[] job_arr = new String[job_in.size()];
                job_in.toArray(job_arr);
                intent.putExtra("date", date);
                intent.putExtra("job", job);
                intent.putExtra("in", job_arr[childPosition]);
                startActivity(intent);
                return true;
                /*Toast.makeText(getApplicationContext(), date_header.get(groupPosition)
                                + " : "
                                + job_items.get(date_header.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT).show();
                return false;*/
            }
        });
    }

    private void populateList(String[] results) {

        date_header = new ArrayList<String>();
        job_items = new HashMap<String, List<String>>();
        List<String> jobs = new ArrayList<String>();

        //þessi nær í allar dagsetningarnar sem komu úr worklog töflunni
        for (int i = 0; i < results.length; i += 6)
        {
            if(!(date_header.contains(results[i])))
            {
                date_header.add(results[i]);            //unique date headers
            }
            jobs.add(results[i+1]);                     //job names
            job_in.add(results[i+2]);
            job_items.put(results[i], jobs);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vinnu_yfirlit, menu);
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
        else if (id == R.id.menu_upphafsskjar)
        {
            Intent openStart = new Intent(vinnu_yfirlit.this, Upphafsskjar.class);
            startActivity(openStart);
        }
        else if (id == R.id.menu_back)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
