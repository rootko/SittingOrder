package sk.halmi.sittingorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.halmi.sittingorder.api.BackendAPI;
import sk.halmi.sittingorder.api.SittingOrder;
import sk.halmi.sittingorder.api.model.RowItemEdituj;
import sk.halmi.sittingorder.api.model.person.PersonSet;
import sk.halmi.sittingorder.api.model.person.Result;
import sk.halmi.sittingorder.api.model.putperson.D;
import sk.halmi.sittingorder.api.model.putperson.Person;
import sk.halmi.sittingorder.api.model.putperson.PutPerson;
import sk.halmi.sittingorder.api.model.room.RoomSet;
import sk.halmi.sittingorder.helper.Constants;

public class Editujmiestnost extends AppCompatActivity {

    private List<RowItemEdituj> zoznammien = new ArrayList<RowItemEdituj>();
    List<RowItemEdituj> zmenamien = new ArrayList<RowItemEdituj>();
	private ArrayList<Result> assignToRoom = new ArrayList<>();
	private ArrayList<String> removeFromRoom = new ArrayList<>();

//    ListView listView;
//    FloatingActionButton fab;
    @Bind(R.id.list_wrapper)
    LinearLayout wrapper;

	@Bind(R.id.scrollView)
	ScrollView scrollView;

	@Bind(R.id.b_save)
	ImageButton btnSave;

    Intent intent;
    String building;
    String floor;
    String room;
	int capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editujmiestnost1);
        ButterKnife.bind(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Log.d("save", "------------ ADD");
				saveToDatabase();
            }
        });
    }

	@Override
	protected void onStart() {
		super.onStart();
		intent=getIntent();
		building=intent.getStringExtra("building");
		floor=intent.getStringExtra("floor");
		room=intent.getStringExtra("room");
		if (null != intent.getStringExtra("capacity")) {
			capacity=Integer.valueOf(intent.getStringExtra("capacity"));
		} else {
			capacity = -1;
		}

		Log.d("Search", building + ":" + floor + ":" + room + ":" + capacity);

		TextView txt_b=(TextView)findViewById(R.id.txt_rb);
		TextView txt_f=(TextView)findViewById(R.id.txt_rf);
		TextView txt_r=(TextView)findViewById(R.id.txt_rr);
		txt_b.setText(building);
		txt_f.setText(floor);
		txt_r.setText(room);

		//undefined capacity - load from backend
		if (capacity == -1) {
			getCapacityAndContinue();
		} else {
			getDataFromBackend();
		}
		populateList();
		populateOnClickList();
		scrollView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mTouchPosition = event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					mReleasePosition = event.getY();

					if (Math.abs(mTouchPosition - mReleasePosition) > 100) {
						if (mTouchPosition - mReleasePosition > 0) {
							// user scroll down
							Log.d("scroll", mTouchPosition - mReleasePosition + " scrolled down");
							btnSave.setVisibility(View.INVISIBLE);
							mTouchPosition = event.getY();
						} else {
							//user scroll up
							Log.d("scroll", mTouchPosition - mReleasePosition + " scrolled up");
							btnSave.setVisibility(View.VISIBLE);
							mTouchPosition = event.getY();
						}
					}
				}
				return scrollView.onTouchEvent(event);
			}
		});
	}

	private float mTouchPosition;
	private float mReleasePosition;

	private void getCapacityAndContinue() {
		if (Constants.DUMMY) {
			capacity = 19;
			getDataFromBackend();
			return;

		}

		SittingOrder client = BackendAPI.createService(SittingOrder.class);

		String buildingFilter = "IdBuilding eq '" + building + "'";
		String floorFilter = "IdFloor eq '" + floor + "'";
		String roomFilter = "IdRoom eq '" + room + "'";
		Call<RoomSet> call = client.getRooms(buildingFilter + " and " + floorFilter + " and " + roomFilter, "json");
		call.enqueue(new Callback<RoomSet>() {
			@Override
			public void onResponse(Call<RoomSet> call, Response<RoomSet> response) {
				Log.d("OkHttp", "onResponse");
				if (response.isSuccessful()) {
					RoomSet roomSet = response.body();
					for (sk.halmi.sittingorder.api.model.room.Result result : roomSet.getD().getResults()) {
						Log.d("Room", result.getIdBuilding() + " " + result.getIdFloor() + " " + result.getIdRoom() + " " + result.getCapacity());
						capacity = result.getCapacity();
					}
					getDataFromBackend();
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


	//    //Kliknutie na floating button ULOZIT
//    private void saveList()
//    {
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int velkost=zoznammien.size();
//                if (velkost!=0)
//                {
//                    for (int i=0;i<velkost;i++)
//                    {
//                        RowItemEdituj rowItemEdituj = zoznammien.get(i);
//                        if(rowItemEdituj.getMeno().equals(""))
//                        {
//
//                        }
//                        else
//                        {
//                            RowItemEdituj rowItemEdituj_save = zoznammien.get(i);
//                            // posielanie na ulozenie potom
//                        }
//                    }
//                }}
//        });
//    }

    private void populateList() {
		wrapper.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(Editujmiestnost.this);
        Integer idenfier = 100;
        for (RowItemEdituj item : zoznammien) {
            //natiahni xml layout a vytvor objekt
            final View itemView = layoutInflater.inflate(R.layout.rowitem_edituj, null);
            //priradime tag, aby sme ho vedeli volat neskor
            itemView.setTag(idenfier);

            //najdeme v R.layout.rowitem_edituj vsetky polia, ktore plnime
            final AutoCompleteTextView eID = ((AutoCompleteTextView)itemView.findViewById(R.id.idecko));
			//use autocomplete
			String[] ids = Splashactivity.getIds();
			ArrayAdapter<String> adapterID = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ids);
			eID.setAdapter(adapterID);
			eID.setThreshold(1);
			eID.setTag(idenfier + 1);
			eID.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s.toString().contains("[")) {
						String id = s.toString().substring(0, s.toString().indexOf('[')).trim();
						Integer idInt = Integer.parseInt(id);
						Result person = Splashactivity.getPerson(idInt);
						eID.setText(idInt + "");
						if (null != person) {
							person.setIdBuilding(building);
							person.setIdFloor(floor);
							person.setIdRoom(room);

							assignToRoom.add(person);
							Log.d("TextChanged - id", idInt + "");
							Integer tag = (Integer) eID.getTag();
							AutoCompleteTextView name = (AutoCompleteTextView) itemView.findViewWithTag(tag + 1);
							name.setText(person.getFirstName());
							AutoCompleteTextView surname = (AutoCompleteTextView) itemView.findViewWithTag(tag + 2);
							surname.setText(person.getLastName());
						}
					}
				}
			});


			final AutoCompleteTextView eName = ((AutoCompleteTextView)itemView.findViewById(R.id.name));
			String[] names = Splashactivity.getNames();
			ArrayAdapter<String> adapterName = new ArrayAdapter<String>(this,R.layout.simple_list_item_search_help, names);
			eName.setAdapter(adapterName);
			eName.setThreshold(1);
			eName.setTag(idenfier + 2);

			eName.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s.toString().contains("[")) {
						Log.d("TextChanged - name", s.toString());
						int start = s.toString().indexOf("[");
						int end = s.toString().indexOf("]");
						Integer idInt = Integer.parseInt(s.toString().substring(start + 1, end));
						Result person = Splashactivity.getPerson(idInt);
						eID.setText(idInt + "");
						if (null != person) {
							person.setIdBuilding(building);
							person.setIdFloor(floor);
							person.setIdRoom(room);

							assignToRoom.add(person);
							Integer tag = (Integer) eID.getTag();
							AutoCompleteTextView name = (AutoCompleteTextView) itemView.findViewWithTag(tag + 1);
							name.setText(person.getFirstName());
							AutoCompleteTextView surname = (AutoCompleteTextView) itemView.findViewWithTag(tag + 2);
							surname.setText(person.getLastName());
						}
					}
				}
			});

            final AutoCompleteTextView eSurname = ((AutoCompleteTextView)itemView.findViewById(R.id.priezvisko));
			String[] surnames = Splashactivity.getSurnames();
			ArrayAdapter<String> adapterSurname = new ArrayAdapter<String>(this,R.layout.simple_list_item_search_help, surnames);
			eSurname.setAdapter(adapterSurname);
			eSurname.setThreshold(1);
			eSurname.setTag(idenfier + 3);

			eSurname.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s.toString().contains("[")) {
						Log.d("TextChanged - name", s.toString());
						int start = s.toString().indexOf("[");
						int end = s.toString().indexOf("]");
						Integer idInt = Integer.parseInt(s.toString().substring(start + 1, end));
						Result person = Splashactivity.getPerson(idInt);
						eID.setText(idInt + "");
						if (null != person) {
							person.setIdBuilding(building);
							person.setIdFloor(floor);
							person.setIdRoom(room);

							assignToRoom.add(person);
							Integer tag = (Integer) eID.getTag();
							AutoCompleteTextView name = (AutoCompleteTextView) itemView.findViewWithTag(tag + 1);
							name.setText(person.getFirstName());
							AutoCompleteTextView surname = (AutoCompleteTextView) itemView.findViewWithTag(tag + 2);
							surname.setText(person.getLastName());
						}

					}
				}
			});


            eID.setText(item.getIdecko());
            eName.setText(item.getMeno());
            eSurname.setText(item.getPriezvisko());
			itemView.findViewById(R.id.btndelete).setTag(idenfier + 4);
            itemView.findViewById(R.id.btndelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
					if (!removeFromRoom.contains(eID.getText().toString()) && !"".equals(eID.getText().toString())) {
						removeFromRoom.add(eID.getText().toString());
					}
                    eID.setText("");
                    eName.setText("");
                    eSurname.setText("");
                }
            });

            wrapper.addView(itemView);
			idenfier += 100;
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void populateOnClickList() {
    }

    public class ListViewAdapterRowItemEdituj extends ArrayAdapter<RowItemEdituj> {

        public ListViewAdapterRowItemEdituj() {

            super(Editujmiestnost.this, R.layout.rowitem_edituj, zoznammien);
        }

        public View getView(final int position, View covertView, ViewGroup parent) {
            View itemView = covertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.rowitem_edituj, parent, false);
            }

            RowItemEdituj rowItemEdituj = zoznammien.get(position);

            EditText txt_idecko=(EditText)itemView.findViewById(R.id.idecko);
            txt_idecko.setText(rowItemEdituj.getIdecko());

            final EditText txt_name = (EditText) itemView.findViewById(R.id.name);
            txt_name.setText(rowItemEdituj.getMeno());

            EditText txt_priezvisko = (EditText) itemView.findViewById(R.id.priezvisko);
            txt_priezvisko.setText(rowItemEdituj.getPriezvisko());
            final String menicko=rowItemEdituj.getMeno();

            ImageButton imageButton=(ImageButton) itemView.findViewById(R.id.btndelete);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (menicko.equals(""))
                    {

                    }
                    else {
                        //vyhadzovaniee ludi
                        zoznammien.remove(position);
                        zoznammien.add(new RowItemEdituj("","",""));
                        notifyDataSetChanged();
                    }
                }
            });

            return itemView;
        }
    }

	private void getDataFromBackend() {
		if (Constants.DUMMY) {
			zoznammien = new ArrayList<RowItemEdituj>();
			for (int i = 0; i < 13; i++) {
				zoznammien.add(new RowItemEdituj("12", "Frenky", "Tester"));
			}
			populateList();
			return;
		}

		String filter = "IdBuilding eq '"+building+"'";
		filter += " and IdFloor eq '"+floor+"'";
		filter += " and IdRoom eq '"+room+"'";
		// Create a very simple REST adapter which points the GitHub API endpoint.
		SittingOrder client = BackendAPI.createService(SittingOrder.class);

		// Fetch the response via retrofit
		Call<PersonSet> call = client.getPersons(filter, "json");
		call.enqueue(new Callback<PersonSet>() {
			@Override
			public void onResponse(Call<PersonSet> call, Response<PersonSet> response) {
				Log.d("OkHttp", "onResponse");
				if (response.isSuccessful()) {
					//get the results - in this case a PersonSet
					PersonSet personSet = response.body();
					zoznammien = new ArrayList<RowItemEdituj>();
					//get throught the results
					for (Result result : personSet.getD().getResults()) {
						//log each one
						Log.d("OkHttp", result.getFirstName() + " " + result.getLastName());
						//and add it to the data set
						zoznammien.add(new RowItemEdituj(result.getIdPerson() + "", result.getFirstName(), result.getLastName()));
					}

					//add empty places
					if (zoznammien.size() < capacity) {
						for (int i = 0; i <= capacity - zoznammien.size(); i++) {
							zoznammien.add(new RowItemEdituj("", "", ""));
						}
					}

					//when all items are processed, refresh the recycler view
					populateList();
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

	private void saveToDatabase() {
		for (Result person : assignToRoom) {
			Log.d("save", person.getIdPerson() + " " + person.getLastName() + " " + person.getFirstName());
			putPerson(person);
		}

		Log.d("save", "------------ REMOVE");
		for (String id : removeFromRoom) {
			Log.d("save", id);
			clearPerson(Integer.valueOf(id));
		}

		Toast.makeText(Editujmiestnost.this, "Saved!", Toast.LENGTH_SHORT).show();
		finish();
	}

	private void clearPerson(Integer id) {
		Result person = Splashactivity.getPerson(id);
		person.setIdBuilding("");
		person.setIdFloor("");
		person.setIdRoom("");
		putPerson(person);
	}

	private void putPerson(final Result person) {
		// Create a very simple REST adapter which points the GitHub API endpoint.
		SittingOrder client = BackendAPI.createService(SittingOrder.class);

		Person putPerson = new Person();
		putPerson.setFirstName(person.getFirstName());
		putPerson.setLastName(person.getLastName());
		putPerson.setIdPerson(person.getIdPerson());
		putPerson.setIdBuilding(person.getIdBuilding());
		putPerson.setIdFloor(person.getIdFloor());
		putPerson.setIdRoom(person.getIdRoom());

		// Fetch the response via retrofit
		Call<Person> call = client.putPerson(putPerson);
		call.enqueue(new Callback<Person>() {
			@Override
			public void onResponse(Call<Person> call, Response<Person> response) {
				Log.d("OkHttp", "onResponse");
			}

			@Override
			public void onFailure(Call<Person> call, Throwable t) {
				// something went completely south (like no internet connection)
				Log.d("OkHttp", "onFailure");
			}
		});
	}


}
