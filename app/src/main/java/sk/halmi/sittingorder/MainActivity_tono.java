package sk.halmi.sittingorder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity_tono extends AppCompatActivity {

    Spinner poschodieSpinnner;
    ListView zoznamMiestnosti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.titlesearchroom);

        Spinner budovaSpinner = (Spinner) findViewById(R.id.spinnerBudova);
        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.budovaArray, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        budovaSpinner.setAdapter(spinnerArrayAdapter);

        //Spinner selection
        budovaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.poschodieArray6, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    poschodieSpinnner.setAdapter(spinnerArrayAdapter);
                } else {
                    ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.poschodieArray8, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    poschodieSpinnner.setAdapter(spinnerArrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.poschodieSpinnner = (Spinner) findViewById(R.id.spinnerPoschodie);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        zoznamMiestnosti = (ListView) findViewById(R.id.zoznamMiestnosti);
        vytvorMiestnosti();
    }


    private void vytvorMiestnosti() {
        // Construct the data source
        final ArrayList<Miestnost> arrayOfUsers = new ArrayList<Miestnost>();
        for (int i = 0; i < 10; i++) {
            arrayOfUsers.add(new Miestnost("Miestnost:" + i, "Poschodie:" + i));
        }
// Create the adapter to convert the array to views
        MiestnostAdapter adapter = new MiestnostAdapter(this, arrayOfUsers);

        zoznamMiestnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), arrayOfUsers.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Attach the adapter to a ListView
        zoznamMiestnosti.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
