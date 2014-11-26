package com.example.freyds.timar3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;

/**
 * Klasi sem sækjir gögn um vinnutíma í SQL gagnagrunn og birtir eftir dögum
 * @author Anna Hafthorsdottir, Freydis Halldorsdottir, Helga Loa Kristjansdottir, Pall Arnar Palsson
 * @date 14. november 2014
 */

public class dags_yfirlit extends Activity {
    TextView date, name, in, out, hours, amount;
    Bundle extras;
    DatabaseAdapter helper;

    //Hlutir skilgreindir í byrjun
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dags_yfirlit);
        date = (TextView) findViewById(R.id.date);
        name = (TextView) findViewById(R.id.name);
        in = (TextView) findViewById(R.id.in);
        out = (TextView) findViewById(R.id.out);
        hours = (TextView) findViewById(R.id.hours);
        amount = (TextView) findViewById(R.id.amount);

        helper = new DatabaseAdapter(this);
        extras = getIntent().getExtras();
        String d = extras.getString("date");
        String n = extras.getString("job");
        String i = extras.getString("in");

        String[] results = helper.getJobInfo(d, n, i);
        date.setText(results[0]);
        name.setText(results[1]);
        in.setText("Stimplað inn: "+results[2]);
        out.setText("Stimplað út: "+results[3]);
        hours.setText("Samtals tímar: " + results[4]);
        amount.setText("Laun: " + results[5]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dags_yfirlit, menu);
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
            Intent openStart = new Intent(dags_yfirlit.this, Upphafsskjar.class);
            startActivity(openStart);
        }
        else if (id == R.id.menu_yfirlit)
        {
            Intent openStart = new Intent(dags_yfirlit.this, yfirlit.class);
            startActivity(openStart);
        }
        else if (id == R.id.menu_back)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
