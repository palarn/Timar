package com.example.freyds.timar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ny_vinna extends Activity {

    EditText nafn, dagvinna, yfirvinna;
    Button skra;

    DatabaseAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_vinna);

        helper = new DatabaseAdapter(this);

        nafn = (EditText) findViewById(R.id.job_name);
        dagvinna = (EditText) findViewById(R.id.dagvinna);
        yfirvinna = (EditText) findViewById(R.id.yfirvinna);
        skra = (Button) findViewById(R.id.skra_button);
    }

    //bætir inn nýrri vinnu í töfluna JOB_INFO
    public void addJob(View view)
    {
        String name = nafn.getText().toString();
        String salary1 = dagvinna.getText().toString();
        String salary2 = yfirvinna.getText().toString();

        long id = helper.insertData(name, salary1, salary2);

        Intent intent = new Intent(ny_vinna.this, Upphafsskjar.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ny_vinna, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
