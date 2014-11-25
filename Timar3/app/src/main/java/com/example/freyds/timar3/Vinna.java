package com.example.freyds.timar3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    static boolean clicked = false;

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

        //ýtt á stimpla inn takkann
        //temp_date, name, temp_in sett í TEMP töflu
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked)
                {
                    clock.setBase(SystemClock.elapsedRealtime());
                    clock.start();

                    SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat t = new SimpleDateFormat("HH:mm");

                    Date time = new java.util.Date();
                    String temp_date = d.format(time);
                    String temp_in = t.format(time);

                    helper.insertInTemp(temp_date, name, temp_in);
                    clicked = true;
                }
            }
        });

        //ýtt á stimpla út takkann (bara ef búið er að ýta á stimpla inn)
        //temp_out sett í TEMP töflu, total_hours og total_minutes reiknað út frá temp_in og temp_out
        //upplýsingarnar úr TEMP settar í WORKLOG og færslunni úr TEMP er eytt
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked)
                {
                    clock.stop();
                    hours.setText(getSeconds(clock.getText().toString()));

                    Date time = new java.util.Date();
                    SimpleDateFormat t = new SimpleDateFormat("HH:mm");
                    String temp_out = t.format(time);

                    helper.insertOutTemp(name, temp_out);

                    Toast.makeText(getApplicationContext(), temp_out, Toast.LENGTH_LONG).show();
                    String[] tempinfo = helper.getTempInfo();

                    double hours = Double.parseDouble(tempinfo[4]) + (Double.parseDouble(tempinfo[5]) / 60);



                    String stimplainn = tempinfo[2];
                    String stimplaut = tempinfo[3];
                    System.out.print(stimplainn);
                    String [] innstimplun = stimplainn.split(":");
                    String [] utstimplun = stimplaut.split(":");
                    String inntimi = innstimplun[0] + "." + innstimplun[1];
                    String uttimi = utstimplun[0] + "." + utstimplun[1];
                    double in = Double.parseDouble(inntimi);
                    double out = Double.parseDouble(uttimi);
                    Log.e("This is the output", in+"");
                    Log.e("This is the output", out+"");

                    String yfirvinna = salary[2];
                    String [] yfirvinnustrengur = yfirvinna.split(":");
                    System.out.print(yfirvinnustrengur);
                    String yfirvinnafyrripartur = yfirvinnustrengur[0];
                    double yfirvinnutala = Double.parseDouble(yfirvinnafyrripartur);
                    Log.e("This is the output", yfirvinnutala+"");
                    int yfirvinnukaup = (int)(Integer.parseInt(salary[1]));

                    //Ef þú vinnur á dagvinnulaunum allan daginn
                    if(in >= 8 && out <= yfirvinnutala && hours < 8){
                        int money = (int)(Integer.parseInt(salary[0]) * hours);
                        String money_text = "" + money;
                        amount.setText(money_text);
                    }
                    //Ef þú stimplar þig inn fyrir klukkan 8 um morgun
                    else if(in < 8){
                        //Ef þú stimplar þig út á dagvinnutíma
                        if(out <= yfirvinnutala && out >= 8){
                            double yfirvinnutimi = 8-in;
                            double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
                            double dagvinnulaun = Double.parseDouble(salary[0])*(hours-yfirvinnutimi);
                            int money = (int)(yfirvinnulaun+dagvinnulaun);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                        //Ef þú stimplar þig líka út fyrir kl 8 um morguninn
                        else if(out < 8 && hours <= 8){
                            int money = (int)(Integer.parseInt(salary[1]) * hours);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                        //Ef þú stimplar þig út eftir að yfirvina hefst
                        else if(out > yfirvinnutala){
                            double yfirvinnutimi = (8-in) + (out - yfirvinnutala);
                            double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
                            double dagvinnulaun = Double.parseDouble(salary[0])*(hours-yfirvinnutimi);
                            int money = (int)(yfirvinnulaun+dagvinnulaun);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                        //Ef þú vinnur lengur en 17 tíma og ert því komin hringinn (klukkan er orðin meira en miðnættir aftur)
                        else if(hours > 17 && out < 8){
                            double yfirvinnutimi = (8-in) + (24-yfirvinnutala) + out;
                            double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
                            double dagvinnulaun = Double.parseDouble(salary[0])*(hours-yfirvinnutimi);
                            int money = (int)(yfirvinnulaun+dagvinnulaun);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                    }
                    //Ef þú stimplar þig inn eftir að yfirvinna hefst (fyrir miðnætti samt)
                    else if(in > yfirvinnutala){
                        //Ef þú stimplar þig líka út á yfirvinnutíma
                        if((out > yfirvinnutala && hours < 8) || (out <= 8)){
                            int money = (int)(Integer.parseInt(salary[1]) * hours);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                        //Ef þú stimplar þig út á dagvinnutíma
                        else if(out < yfirvinnutala && out > 8){
                            double yfirvinnutimi = (24-in) + 8;
                            double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
                            double dagvinnulaun = Double.parseDouble(salary[0])*(hours-yfirvinnutimi);
                            int money = (int)(yfirvinnulaun+dagvinnulaun);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                        //Ef þú vinnur í meira en 14 tíma og ert komin aftur í yfirvinnu
                        else if(hours > 14 && out > yfirvinnutala){
                            double yfirvinnutimi = (24-in) + 8 + (out-yfirvinnutala);
                            double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
                            double dagvinnulaun = Double.parseDouble(salary[0])*(hours-yfirvinnutimi);
                            int money = (int)(yfirvinnulaun+dagvinnulaun);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                    }
                    //Ef þú stimplar þig inn á dagvinnutíma
                    else if(in >= 8 && in <= yfirvinnutala){
                        if(out > yfirvinnutala){
                            double yfirvinnutimi = out - yfirvinnutala;
                            double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
                            double dagvinnulaun = Double.parseDouble(salary[0])*(hours-yfirvinnutimi);
                            int money = (int)(yfirvinnulaun+dagvinnulaun);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                        else if(out <= 8){
                            double yfirvinnutimi = out + (24-yfirvinnutala);
                            double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
                            double dagvinnulaun = Double.parseDouble(salary[0])*(hours-yfirvinnutimi);
                            int money = (int)(yfirvinnulaun+dagvinnulaun);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }
                        else if(hours > 24 && out > 8){
                            double yfirvinnutimi = (out-8) + 8 + (24-yfirvinnutala);
                            double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
                            double dagvinnulaun = Double.parseDouble(salary[0])*(hours-yfirvinnutimi);
                            int money = (int)(yfirvinnulaun+dagvinnulaun);
                            String money_text = "" + money;
                            amount.setText(money_text);
                        }

                    }
                    //date, name, in, out, hours, minutes, salary
                    helper.insertWorkLog(tempinfo[0], tempinfo[1], tempinfo[2], tempinfo[3], tempinfo[4], tempinfo[5], salary[0]);
                    helper.deleteTemp();
                    clicked = false;
                }
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
        if(!clicked){
            finish();
        }
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

