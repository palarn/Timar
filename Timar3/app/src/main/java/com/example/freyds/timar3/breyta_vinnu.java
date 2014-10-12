package com.example.freyds.timar3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class breyta_vinnu extends Activity {

    TextView old_name;
    TextView old_salary1;
    TextView old_salary2;

    EditText new_name;
    EditText new_salary1;
    EditText new_salary2;

    Button change;
    DatabaseAdapter helper;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breyta_vinnu);

        old_name = (TextView)findViewById(R.id.old_name);
        old_salary1 = (TextView)findViewById(R.id.old_salary1);
        old_salary2 = (TextView)findViewById(R.id.old_salary2);

        helper = new DatabaseAdapter(this);
        extras = getIntent().getExtras();

        String name = extras.getString("name");
        old_name.setText(name);

        String[] salary2 = helper.getSalary(name);
        old_salary1.setText(salary2[0]);
        old_salary2.setText(salary2[1]);

        new_name = (EditText)findViewById(R.id.new_name);
        new_salary1 = (EditText)findViewById(R.id.new_salary1);
        new_salary2 = (EditText)findViewById(R.id.new_salary2);

        change = (Button)findViewById(R.id.change_button);
    }

    public void changeJob(View view)
    {
        String oldname = old_name.getText().toString();
        String oldsalary1 = old_salary1.getText().toString();
        String oldsalary2 = old_salary2.getText().toString();
        String newname = new_name.getText().toString();
        String newsalary1 = new_salary1.getText().toString();
        String newsalary2 = new_salary2.getText().toString();

        String[] newstuff = {newname, newsalary1, newsalary2};
        String[] oldstuff = {oldname, oldsalary1, oldsalary2};
        String[] changes = new String[3];

        for(int i = 0; i < newstuff.length; i++)
        {
            if (newstuff[i].matches(""))
            {
                changes[i] = oldstuff[i];
            }
            else
            {
                changes[i] = newstuff[i];
            }
        }
        long id = helper.changeJob(oldname, changes[0], changes[1], changes[2]);

        Intent intent = new Intent(breyta_vinnu.this, Vinna.class);
        intent.putExtra("name", changes[0]);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vinna, menu);
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
