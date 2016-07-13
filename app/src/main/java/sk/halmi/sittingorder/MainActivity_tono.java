package sk.halmi.sittingorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.halmi.sittingorder.adapter.MiestnostAdapter_tono;
import sk.halmi.sittingorder.api.BackendAPI;
import sk.halmi.sittingorder.api.SittingOrder;
import sk.halmi.sittingorder.api.model.Miestnost_tono;
import sk.halmi.sittingorder.api.model.room.Result;
import sk.halmi.sittingorder.api.model.room.RoomSet;

public class MainActivity_tono extends AppCompatActivity {

    Spinner poschodieSpinnner;
    ListView zoznamMiestnosti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tono);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.titlesearchroom);

        final Spinner budovaSpinner = (Spinner) findViewById(R.id.spinnerBudova);

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

        final String[] buildingIds = new String[] {"JT6", "JT8"};
        final String[] poschodieIds = new String[] {"1", "2", "3", "4", "5", "6"};

        poschodieSpinnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SittingOrder client = BackendAPI.createService(SittingOrder.class);

                int index = budovaSpinner.getSelectedItemPosition();
                int indexPoschodie = poschodieSpinnner.getSelectedItemPosition();

                // Toast.makeText(MainActivity_tono.this, buildingIds[index], Toast.LENGTH_SHORT).show();

                String buildingFilter = "IdBuilding eq '" + buildingIds[index] + "'";
                String floorFilter = "IdFloor eq '" + poschodieIds [indexPoschodie] + "'";
                Call<RoomSet> call = client.getRooms(buildingFilter + " and " + floorFilter, "json");
                call.enqueue(new Callback<RoomSet>() {
                    @Override
                    public void onResponse(Call<RoomSet> call, Response<RoomSet> response) {
                        Log.d("OkHttp", "onResponse");
                        if (response.isSuccessful()) {
                            RoomSet roomSet = response.body();
                            ArrayList<Miestnost_tono> data = new ArrayList<Miestnost_tono>();
                            for (Result result : roomSet.getD().getResults()) {
                                Log.d("OkHttp", result.getIdBuilding() + " " + result.getIdFloor() + " " + result.getIdRoom());
                                data.add(new Miestnost_tono(result.getIdRoom(),result.getIdFloor()));
                            }

                            vytvorMiestnosti(data);

                        } else {
                            // error response, no access to resource?
                            Log.d("OkHttp", "--------- didn't work ---------" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<RoomSet> call, Throwable t) {
                        // something went completely south (like no internet connection)
                        Log.d("OkHttp", "onFailure");
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        zoznamMiestnosti = (ListView) findViewById(R.id.zoznamMiestnosti);
//        vytvorMiestnosti();
    }


    private void vytvorMiestnosti(ArrayList<Miestnost_tono> miestnosti) {
        // Construct the data source
//        final ArrayList<Miestnost_tono> arrayOfUsers = new ArrayList<Miestnost_tono>();
//        for (int i = 0; i < 10; i++) {
//            arrayOfUsers.add(new Miestnost_tono("Miestnost:" + i, "Poschodie:" + i));
//        }
//// Create the adapter to convert the array to views
        MiestnostAdapter_tono adapter = new MiestnostAdapter_tono(this, miestnosti);

        zoznamMiestnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity_tono.this, Editujmiestnost.class));
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
		switch (item.getItemId()) {
			case R.id.action_room_edit:
				startActivity(new Intent(MainActivity_tono.this, Editujmiestnost.class));
				break;

			case R.id.action_search_person:
				startActivity(new Intent(MainActivity_tono.this, VyhladajZamestnanca.class));
				break;

			case R.id.action_search_results:
				startActivity(new Intent(MainActivity_tono.this, Vysledkyvyhladavania.class));
				break;
		}

        return super.onOptionsItemSelected(item);
    }
}
