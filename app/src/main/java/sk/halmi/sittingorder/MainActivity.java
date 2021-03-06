package sk.halmi.sittingorder;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.halmi.sittingorder.adapter.SimpleItemRecyclerViewAdapter;
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

	@Bind(R.id.result_list)
	RecyclerView recyclerView;

	private static ArrayList<String> data;

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

		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.btn_people)
	public void peopleClick(Button button) {
		hideKeyboard(button);
		// Create a very simple REST adapter which points the GitHub API endpoint.
		SittingOrder client = BackendAPI.createService(SittingOrder.class);
		//add filters needed to make a request
		String buildingFilter = "IdBuilding eq '" + eBuildingNumber.getText().toString().toUpperCase() + "'";
		String roomFilter = "IdRoom eq '"+eRoomNumber.getText().toString()+"'";

		// Fetch the response via retrofit
		Call<PersonSet> call = client.getPersons(buildingFilter + " and " + roomFilter, "json");
		call.enqueue(new Callback<PersonSet>() {
			@Override
			public void onResponse(Call<PersonSet> call, Response<PersonSet> response) {
				Log.d("OkHttp", "onResponse");
				if (response.isSuccessful()) {
					//get the results - in this case a PersonSet
					PersonSet personSet = response.body();
					ArrayList<String> data = new ArrayList<String>();
					//get throught the results
					for (Result result : personSet.getD().getResults()) {
						//log each one
						Log.d("OkHttp", result.getFirstName() + " " + result.getLastName());
						//and add it to the data set
						data.add(result.getFirstName() + " " + result.getLastName());
					}
					//when all items are processed, refresh the recycler view
					refreshRecyclerView(data);
				} else {
					// error response, no access to resource?
					Log.d("OkHttp", "--------- didn't work ---------" + response.message());
				}
			}

			@Override
			public void onFailure(Call<PersonSet> call, Throwable t) {
				// something went completely south (like no internet connection)
				Log.d("OkHttp", "onFailure");
			}
		});
	}

	//see the comments in peopleClick() method - they're the same
	@OnClick(R.id.btn_rooms)
	public void roomClick(Button button) {
		hideKeyboard(button);

		SittingOrder client = BackendAPI.createService(SittingOrder.class);

		String buildingFilter = "IdBuilding eq '" + eBuildingNumber.getText().toString().toUpperCase() + "'";
		String floorFilter = "IdFloor eq '" + eFloorNumber.getText().toString() + "'";
		Call<RoomSet> call = client.getRooms(buildingFilter + " and " + floorFilter, "json");
		call.enqueue(new Callback<RoomSet>() {
			@Override
			public void onResponse(Call<RoomSet> call, Response<RoomSet> response) {
				Log.d("OkHttp", "onResponse");
				if (response.isSuccessful()) {
					RoomSet personSet = response.body();
					ArrayList<String> data = new ArrayList<String>();
					for (sk.halmi.sittingorder.api.model.room.Result result : personSet.getD().getResults()) {
						Log.d("OkHttp", result.getIdBuilding() + " " + result.getIdFloor() + " " + result.getIdRoom());
						data.add(result.getIdBuilding() + " " + result.getIdFloor() + " " + result.getIdRoom());
					}
					refreshRecyclerView(data);
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

	private void refreshRecyclerView(ArrayList<String> data) {
		assert recyclerView != null;
		if (null != data) {
			MainActivity.data = data;
			// use this setting to improve performance if you know that changes
			// in content do not change the layout size of the RecyclerView
			recyclerView.setHasFixedSize(true);
			//set adapter for this recycler view - adapter formats data entries to a set of views
			recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(MainActivity.this, data));
		}
	}

	//helper method to hide the keyboard
	private void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
