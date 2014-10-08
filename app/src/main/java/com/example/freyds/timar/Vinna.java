package com.example.freyds.timar;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class Vinna extends Activity {

    Chronometer clock;
    Button in;
    Button out;
    Button reset;
    TextView hours;
    TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinna);

        in = (Button)findViewById(R.id.button_in);
        out = (Button)findViewById(R.id.button_out);
        reset = (Button)findViewById(R.id.button_reset);
        clock = (Chronometer)findViewById(R.id.chronometer);
        hours = (TextView)findViewById(R.id.hours);
        amount = (TextView)findViewById(R.id.amount);

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
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clock.setBase(SystemClock.elapsedRealtime());
            }
        });
    }

    public static String getSeconds(String time)
    {
        // time er strengur á forminu HH:MM:SS eða MM:SS
        String[] array = time.split(":");
        // skiptir strengnum í mism. stök í fylkinu...
        // getur í mesta lagi innihaldið 3 stök, þ.e. HH,MM,SS
        int minutes = 0;
        int hours = 0;
        if (array.length == 2)
        {
            minutes = Integer.parseInt(array[0]);
        }
        else if (array.length == 3)
        {
            minutes = Integer.parseInt(array[1]);
            hours = Integer.parseInt(array[0]);
        }
        return (hours + " klst. og " + minutes + " mín.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vinna, menu);
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
