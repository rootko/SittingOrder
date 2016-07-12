package sk.halmi.sittingorder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.halmi.sittingorder.api.BackendAPI;
import sk.halmi.sittingorder.api.SittingOrder;
import sk.halmi.sittingorder.api.model.person.PersonSet;
import sk.halmi.sittingorder.api.model.person.Result;
import sk.halmi.sittingorder.api.model.room.RoomSet;

public class MainActivity extends AppCompatActivity {
	@Bind(R.id.e_building_no)
	EditText eBuildingNumber;

	@Bind(R.id.e_floor_no)
	EditText eFloorNumber;

	@Bind(R.id.e_room_no)
	EditText eRoomNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		ButterKnife.bind(MainActivity.this);
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

	@OnClick(R.id.btn_people)
	public void peopleClick(Button button) {
		// Create a very simple REST adapter which points the GitHub API endpoint.
		SittingOrder client = BackendAPI.createService(SittingOrder.class);
		String buildingFilter = "IdBuilding eq '" + eBuildingNumber.getText().toString().toUpperCase() + "'";
		String roomFilter = "IdRoom eq '"+eRoomNumber.getText().toString()+"'";

		// Fetch and print a list of the contributors to this library.
		Call<PersonSet> call = client.getPersons(buildingFilter + " and " + roomFilter, "json");
		call.enqueue(new Callback<PersonSet>() {
			@Override
			public void onResponse(Call<PersonSet> call, Response<PersonSet> response) {
				Log.d("Cube", "onResponse");
				if (response.isSuccessful()) {
					PersonSet personSet = response.body();
					for (Result result : personSet.getD().getResults()) {
						Log.d("Cube", result.getFirstName() + " " + result.getLastName());
					}
//					List<D.Cube> cubes = response.body().getCube().getCube().get(0).getCube();
//					setupRecyclerView(cubes);
//					for (Envelope.Cube cube : cubes) {
//						Log.d("Cube", cube.toString());
//					}
				} else {
					// error response, no access to resource?
					Log.d("Cube", "--------- didn't work ---------" + response.message());
				}
			}

			@Override
			public void onFailure(Call<PersonSet> call, Throwable t) {
				// something went completely south (like no internet connection)
				Log.d("Cube", "onFailure");
			}
		});
	}
	@OnClick(R.id.btn_rooms)
	public void roomClick(Button button) {
		// Create a very simple REST adapter which points the GitHub API endpoint.
		SittingOrder client = BackendAPI.createService(SittingOrder.class);

		// Fetch and print a list of the contributors to this library.
		String buildingFilter = "IdBuilding eq '" + eBuildingNumber.getText().toString().toLowerCase() + "'";
		String floorFilter = "IdFloor eq '" + eFloorNumber.getText().toString() + "'";
		Call<RoomSet> call = client.getRooms(buildingFilter + " and " + floorFilter, "json");
		call.enqueue(new Callback<RoomSet>() {
			@Override
			public void onResponse(Call<RoomSet> call, Response<RoomSet> response) {
				Log.d("Cube", "onResponse");
				if (response.isSuccessful()) {
					RoomSet personSet = response.body();
					for (sk.halmi.sittingorder.api.model.room.Result result : personSet.getD().getResults()) {
						Log.d("Cube", result.getIdBuilding() + " " + result.getIdFloor() + " " + result.getIdRoom());
					}
//					List<D.Cube> cubes = response.body().getCube().getCube().get(0).getCube();
//					setupRecyclerView(cubes);
//					for (Envelope.Cube cube : cubes) {
//						Log.d("Cube", cube.toString());
//					}
				} else {
					// error response, no access to resource?
					Log.d("Cube", "--------- didn't work ---------" + response.message());
				}
			}

			@Override
			public void onFailure(Call<RoomSet> call, Throwable t) {
				// something went completely south (like no internet connection)
				Log.d("Cube", "onFailure");
			}
		});
	}
}
