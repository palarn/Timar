package com.example.freyds.timar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Upphafsskjar extends Activity implements AdapterView.OnItemClickListener {

    ListView vinnuListi;                    //ListView widget sem heldur utan um öll nöfnin á skráðum vinnum
    DatabaseAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upphafsskjar);

        helper = new DatabaseAdapter(this);

        ArrayAdapter<String> adapter;
        vinnuListi = (ListView)findViewById(R.id.vinnuListi);

        String[] data = helper.getNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        vinnuListi.setAdapter(adapter);
        vinnuListi.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
        else if (id == R.id.ny_vinna) {
            Intent newJob = new Intent(Upphafsskjar.this, ny_vinna.class);
            Upphafsskjar.this.startActivity(newJob);
        }

        //það á eftir að útfæra yfirlitið

        /*else if (id == R.id.yfirlit) {
            Intent yfirlit = new Intent(Upphafsskjar.this, yfirlit.class);
            Upphafsskjar.this.startActivity(yfirlit);
        }*/

        return super.onOptionsItemSelected(item);
    }

    //fall sem skiptir um activity þegar ýtt er á e-a vinnu á listanum
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String name = (String) (vinnuListi.getItemAtPosition(i));           //nafnið á vinnunni
        Intent job_selected = new Intent(Upphafsskjar.this, Vinna.class);
        job_selected.putExtra("name", name);                                //sendir nafnið á vinnunni í Vinna activityið
        Upphafsskjar.this.startActivity(job_selected);
    }
}
