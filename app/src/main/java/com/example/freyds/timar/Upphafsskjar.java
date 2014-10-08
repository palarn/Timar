package com.example.freyds.timar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class Upphafsskjar extends Activity implements AdapterView.OnItemClickListener{

    ListView vinnuListi;
    String[] vinnur;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upphafsskjar);

        ArrayAdapter<String> adapter;
        Bundle extras = getIntent().getExtras();
        vinnuListi = (ListView)findViewById(R.id.vinnuListi);

        if (extras != null)
        {
            String value = extras.getString("name");
            list.add(value);
            vinnur = new String[list.size()];
            list.toArray(vinnur);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vinnur);
            vinnuListi.setAdapter(adapter);
            vinnuListi.setOnItemClickListener(this);
        }

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
        /*else if (id == R.id.yfirlit) {
            Intent yfirlit = new Intent(Upphafsskjar.this, yfirlit.class);
            Upphafsskjar.this.startActivity(yfirlit);
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView temp = (TextView) view;
        //Toast.makeText(this, temp.getText() + "" + i, Toast.LENGTH_SHORT);

        Intent job = new Intent(Upphafsskjar.this, Vinna.class);
        Upphafsskjar.this.startActivity(job);
    }
}
