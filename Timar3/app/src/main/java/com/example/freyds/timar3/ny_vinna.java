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
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
/**
 *  Klasi sem tekur inn gildi (upplýsingar um vinnustað) og færir þær inní gagnagrunn
 */

public class ny_vinna extends Activity {

    EditText nafn, dagvinna, yfirvinna;
    Spinner timiYfirvinnu;
    DatabaseAdapter helper;
    Button skra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_vinna);

        nafn = (EditText) findViewById(R.id.jobname);
        dagvinna = (EditText) findViewById(R.id.dagvinna);
        yfirvinna = (EditText) findViewById(R.id.yfirvinna);
        timiYfirvinnu = (Spinner)findViewById(R.id.timiYfirvinnu);
        skra = (Button) findViewById(R.id.skra_button);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timiYfirvinnu.setAdapter(adapter);
        timiYfirvinnu.setOnItemSelectedListener(new MyOnItemSelectedListener());

        Button myButton =(Button)findViewById(R.id.button1);
        myButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Spinner sp =	(Spinner)findViewById(R.id.timiYfirvinnu);
                String spinnerString = null;
                spinnerString = sp.getSelectedItem().toString();
                int nPos = sp.getSelectedItemPosition();


                Toast.makeText(getApplicationContext(), "getSelectedItem=" + spinnerString,
                        Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "getSelectedItemPosition=" + nPos,
                        Toast.LENGTH_LONG).show();
            }
        });
        helper = new DatabaseAdapter(this);
    }

    public  class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            Toast.makeText(parent.getContext(), "Item is " +
                    parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();

        }

        public void onNothingSelected(AdapterView parent) {
            //do nothing
        }
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
        String timeSelected = timiYfirvinnu.getSelectedItem().toString();

        if (name.matches("") || salary1.matches("") || salary2.matches(""))
        {
            Toast.makeText(getApplicationContext(), "Fylla verður út alla reiti", Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = helper.insertData(name, salary1, salary2, timeSelected);
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



/*package com.example.freyds.timar3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
*/