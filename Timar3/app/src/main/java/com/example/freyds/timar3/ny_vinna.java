package com.example.freyds.timar3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *  Klasi sem tekur inn gildi (upplýsingar um vinnustað) og færir þær inní gagnagrunn
 */

public class ny_vinna extends Activity {

    EditText nafn, dagvinna, yfirvinna;
    DatabaseAdapter helper;
    Button skra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_vinna);

        nafn = (EditText) findViewById(R.id.jobname);
        dagvinna = (EditText) findViewById(R.id.dagvinna);
        yfirvinna = (EditText) findViewById(R.id.yfirvinna);
        skra = (Button) findViewById(R.id.skra_button);

        helper = new DatabaseAdapter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    //bætir inn nýrri vinnu í töfluna JOB_INFO
    public void addJob(View view)
    {
        String name = nafn.getText().toString();
        String salary1 = dagvinna.getText().toString();
        String salary2 = yfirvinna.getText().toString();

        if (name.matches("") || salary1.matches("") || salary2.matches(""))
        {
            Toast.makeText(getApplicationContext(), "Fylla verður út alla reiti", Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = helper.insertData(name, salary1, salary2);
            Intent intent = new Intent(ny_vinna.this, Upphafsskjar.class);
            startActivity(intent);
        }
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
