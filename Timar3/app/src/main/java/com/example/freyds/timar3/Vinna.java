package com.example.freyds.timar3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

/**
 * Klasi sem heldur utan um virkni i activiy_vinna. Raesir og stodvar klukku, synir vinnutima og laun. Skrair tima i gagnagrunn
 * @author Anna Hafthorsdottir, Freydis Halldorsdottir, Helga Loa Kristjansdottir, Pall Arnar Palsson
 * @date 10. october 2014
 */

public class Vinna extends Activity {

    Chronometer clock;
    Button in;
    Button out;
    //Button reset;
    TextView hours;
    TextView amount;
    TextView job_name;
    TextView salary1;
    TextView salary2;
    TextView salarytime;

    DatabaseAdapter helper;
    String name;
    Bundle extras;
    static int total_minutes = 0;
    static int total_hours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinna);

        in = (Button)findViewById(R.id.button_in);
        out = (Button)findViewById(R.id.button_out);
        //reset = (Button)findViewById(R.id.button_reset);
        clock = (Chronometer)findViewById(R.id.chronometer);
        hours = (TextView)findViewById(R.id.hours);
        amount = (TextView)findViewById(R.id.amount);

        job_name = (TextView)findViewById(R.id.job_name);
        salary1 = (TextView)findViewById(R.id.dagvinna);
        salary2 = (TextView)findViewById(R.id.yfirvinna);
        salarytime = (TextView)findViewById(R.id.timi);


        helper = new DatabaseAdapter(this);

        extras = getIntent().getExtras();
        name = extras.getString("name");

        job_name.setText(name);
        final String[] salary = helper.getSalary(name);
        salary1.setText(salary[0]);
        salary2.setText(salary[1]);

        salarytime.setText(salary[2]);

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock.setBase(SystemClock.elapsedRealtime());
                clock.start();
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock.stop();
                hours.setText(getSeconds(clock.getText().toString()));
                int a = Integer.parseInt(salary[0]) * total_hours;
                String b = "" + a;
                amount.setText(b);

            }
        });
        /*reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock.setBase(SystemClock.elapsedRealtime());
            }
        });*/

    }

    public static String getSeconds(String time)
    {
        // time er strengur á forminu HH:MM:SS eða MM:SS
        String[] array = time.split(":");
        // skiptir strengnum í mism. stök í fylkinu...
        // getur í mesta lagi innihaldið 3 stök, þ.e. HH,MM,SS

        if (array.length == 2)
        {
            total_minutes = Integer.parseInt(array[0]);
        }
        else if (array.length == 3)
        {
            total_minutes = Integer.parseInt(array[1]);
            total_hours = Integer.parseInt(array[0]);
        }
        return (total_hours + " klst. og " + total_minutes + " mín.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Sýnir valmynd; þetta bætir atriði við action barinn ef það er til staðar.
        getMenuInflater().inflate(R.menu.menu_vinna, menu);
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
        else if (id == R.id.menu_upphafsskjar)
        {
            Intent openStart = new Intent(Vinna.this, Upphafsskjar.class);
            startActivity(openStart);
        }
        else if (id == R.id.breyta_vinnu)
        {
            String name2 = name;
            Intent intent = new Intent(Vinna.this, breyta_vinnu.class);
            intent.putExtra("name", name2);                                      //sendir nafnið á vinnunni til breyta_vinnu activity
            Vinna.this.startActivity(intent);
        }
        else if (id == R.id.delete)
        {
            String name2 = name;
            Intent intent = new Intent(Vinna.this, Upphafsskjar.class);
            helper.deleteJob(name2);
            Vinna.this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

