package sk.halmi.sittingorder;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.halmi.sittingorder.api.BackendAPI;
import sk.halmi.sittingorder.api.SittingOrder;
import sk.halmi.sittingorder.api.model.person.PersonSet;
import sk.halmi.sittingorder.api.model.person.Result;

public class Splashactivity extends AppCompatActivity {
	private static List<Result> people;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashactivity);

		getPeople();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splashactivity.this, MainActivity_tono.class);
                startActivity(i);
				Splashactivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

	private void getPeople() {
		// Create a very simple REST adapter which points the GitHub API endpoint.
		SittingOrder client = BackendAPI.createService(SittingOrder.class);

		// Fetch the response via retrofit
		Call<PersonSet> call = client.getPersons("", "json");
		call.enqueue(new Callback<PersonSet>() {

			@Override
			public void onResponse(Call<PersonSet> call, Response<PersonSet> response) {
				Log.d("OkHttp", "people are ready!");
				if (response.isSuccessful()) {
					//get the results - in this case a PersonSet
					PersonSet personSet = response.body();
					people = new ArrayList<Result>();
					//get throught the results
					people = personSet.getD().getResults();
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
}
