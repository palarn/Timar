package com.example.freyds.timar3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Klasinn sem birtir upphafsskjainn í forritinu. Synir skradar vinnur og inniheldur tengla i nyskraningu nyrra vinna og yfirlit
 * @author Anna Hafthorsdottir, Freydis Halldorsdottir, Helga Loa Kristjansdottir, Pall Arnar Palsson
 * @date 10. october 2014
 */

public class Upphafsskjar extends Activity implements AdapterView.OnItemClickListener {

    ListView vinnuListi;                    //ListView widget sem heldur utan um öll nöfnin á skráðum vinnum
    DatabaseAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upphafsskjar);

        ArrayAdapter<String> adapter;
        helper = new DatabaseAdapter(this);
        vinnuListi = (ListView)findViewById(R.id.vinnuListi);

        String[] data = helper.getNames();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        vinnuListi.setAdapter(adapter);
        vinnuListi.setOnItemClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Sýnir valmynd; þetta bætir atriði við action barinn ef það er til staðar.
        getMenuInflater().inflate(R.menu.menu_upphafsskjar, menu);
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
        else if (id == R.id.ny_vinna) {
            Intent nyvinna = new Intent(Upphafsskjar.this, ny_vinna.class);
            Upphafsskjar.this.startActivity(nyvinna);
        }
        else if (id == R.id.yfirlit) {
            Intent yfirlit = new Intent(Upphafsskjar.this, yfirlit.class);
            Upphafsskjar.this.startActivity(yfirlit);
        }

        return super.onOptionsItemSelected(item);
    }

    //fall sem skiptir um activity þegar ýtt er á e-a vinnu á listanum
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String name = (String) (vinnuListi.getItemAtPosition(i));           //nafnið á vinnunni
        Intent job_selected = new Intent(Upphafsskjar.this, Vinna.class);
        job_selected.putExtra("name", name);                                //sendir nafnið á vinnunni í Vinna activityið
        Upphafsskjar.this.startActivity(job_selected);
    }
}
