package sk.halmi.sittingorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.halmi.sittingorder.adapter.MiestnostAdapter_tono;
import sk.halmi.sittingorder.api.BackendAPI;
import sk.halmi.sittingorder.api.SittingOrder;
import sk.halmi.sittingorder.api.model.Miestnost_tono;
import sk.halmi.sittingorder.api.model.RowItem;
import sk.halmi.sittingorder.api.model.room.Result;
import sk.halmi.sittingorder.api.model.room.RoomSet;
import sk.halmi.sittingorder.helper.Constants;

public class MainActivity_tono extends AppCompatActivity {

    Spinner poschodieSpinnner;
    GridView zoznamMiestnosti;


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
				if (Constants.DUMMY) {
					ArrayList<Miestnost_tono> data = new ArrayList<Miestnost_tono>();
					for (int i = 0; i < 13; i++) {
						data.add(new Miestnost_tono("JT6", i + "", i + "01", 5 + "", 6 + ""));
					}
					vytvorMiestnosti(data);
					return;
				}


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
                                data.add(new Miestnost_tono(result.getIdBuilding(), result.getIdFloor(), result.getIdRoom(), result.getOccupied(),result.getCapacity() + ""));
                            }
                            //add fake room if no results
                            if(data.size() == 0){
                                data.add(new Miestnost_tono("---", "", getString(R.string.no_results), "", ""));
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
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                showSearchDialog();
            }
        });
        zoznamMiestnosti = (GridView) findViewById(R.id.zoznamMiestnosti);
//        vytvorMiestnosti();
    }


    private void vytvorMiestnosti(final ArrayList<Miestnost_tono> miestnosti) {
        // Create the adapter to convert the array to views
        MiestnostAdapter_tono adapter = new MiestnostAdapter_tono(this, miestnosti);

        zoznamMiestnosti.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Miestnost_tono miestnost = miestnosti.get(position);
                if (!miestnost.getBuilding().equals("---")){
                    Intent editIntent = new Intent(MainActivity_tono.this, Editujmiestnost.class);
                    editIntent.putExtra("building", miestnost.getBuilding());
                    editIntent.putExtra("floor", miestnost.getPoschodie());
                    editIntent.putExtra("room", miestnost.getCislo());
                    editIntent.putExtra("capacity", miestnost.getKapacita());
                    editIntent.putExtra("occupation", miestnost.getObsadenost());
                    startActivity(editIntent);
					MainActivity_tono.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

				}
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
			case R.id.action_search_results:
				startActivity(new Intent(MainActivity_tono.this, Vysledkyvyhladavania.class));
				break;
		}

        return super.onOptionsItemSelected(item);
    }

    @Bind(R.id.ETmeno)
    EditText meno;
    @Bind(R.id.ETpriezvisko)
    EditText priezvisko;
    @Bind(R.id.ETid)
    EditText id;

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_tono.this);
        // Get the layout inflater
        LayoutInflater inflater = MainActivity_tono.this.getLayoutInflater();
        final View itemView = inflater.inflate(R.layout.dialog, null);
        ButterKnife.bind(this, itemView);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle(R.string.TV_textHladajPodla)
                .setView(itemView)
                // Add action buttons
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int idDialog) {
                        String name= meno.getText().toString();
                        String surname = priezvisko.getText().toString();
                        String idEmp = id.getText().toString();
                        Intent results = new Intent(MainActivity_tono.this, Vysledkyvyhladavania.class);
                        results.putExtra("name", name);
                        results.putExtra("surname", surname);
                        results.putExtra("idEmp", idEmp);
                        startActivity(results);
                        Log.d("Dialog", name + " " + surname + " "+ idEmp);

                    }
                })
                .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("Dialog", "Boxes are empty");
                    }
                });

        builder.create().show();

    }

}
