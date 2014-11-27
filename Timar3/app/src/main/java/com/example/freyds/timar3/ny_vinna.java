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
 * Klasi sem tekur inn gildi (upplysingar um vinnustad) og faerir thaer inn i gagnagrunn
 * @author Anna Hafthorsdottir, Freydis Halldorsdottir, Helga Loa Kristjansdottir, Pall Arnar Palsson
 * @date 10. october 2014
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
        // Sýnir valmynd; þetta bætir atriði við action barinn ef það er til staðar.
        getMenuInflater().inflate(R.menu.ny_vinna, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action bar heldur utan um smelli. Action barinn mun
        // sjálfkrafa sjá um smelli á Home/Up takkann. Svo lengi sem
        // tekið er fram parent activity í AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
