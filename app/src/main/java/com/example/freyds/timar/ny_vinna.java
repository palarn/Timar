package com.example.freyds.timar;

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

    EditText nafn;
    EditText dagvinna;
    EditText yfirvinna;
    Button skra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ny_vinna);

        nafn = (EditText)findViewById(R.id.job_name);
        dagvinna = (EditText)findViewById(R.id.dagvinna);
        yfirvinna = (EditText)findViewById(R.id.yfirvinna);
        skra = (Button)findViewById(R.id.skra_button);

        skra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(nafn.getText().toString().equals("")) &&
                    !(dagvinna.getText().toString().equals("")) &&
                    !(yfirvinna.getText().toString().equals("")))
                {
                    String name = nafn.getText().toString();
                    double salary1 = Double.parseDouble(dagvinna.getText().toString());
                    double salary2 = Double.parseDouble(yfirvinna.getText().toString());

                    Intent intent = new Intent(ny_vinna.this, Upphafsskjar.class);
                    intent.putExtra("name", name);
                    intent.putExtra("salary1", salary1);
                    intent.putExtra("salary2", salary2);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(ny_vinna.this, "Fylla verður út alla reiti", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
