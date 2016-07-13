package sk.halmi.sittingorder;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
;import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.halmi.sittingorder.api.BackendAPI;
import sk.halmi.sittingorder.api.SittingOrder;
import sk.halmi.sittingorder.api.model.RowItem;
import sk.halmi.sittingorder.api.model.RowItemEdituj;
import sk.halmi.sittingorder.api.model.person.PersonSet;
import sk.halmi.sittingorder.api.model.person.Result;

public class Vysledkyvyhladavania extends AppCompatActivity {

    private List<RowItem> vyhladavanie = new ArrayList<RowItem>();
    //    ListView listView;
    @Bind(R.id.list_wrapper)
    LinearLayout wrapper;

    String name2Search, surname2Search, idEmp2Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vysledkyvyhladavania);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        name2Search = intent.getStringExtra("name");
        surname2Search = intent.getStringExtra("surname");
        idEmp2Search = intent.getStringExtra("idEmp");

        Toast.makeText(Vysledkyvyhladavania.this, name2Search + ": " + surname2Search + " " + idEmp2Search, Toast.LENGTH_SHORT).show();

////        listView=(ListView)findViewById(R.id.listView);
////        //------------------------------ NAPLNANIE LISTVIEWU ----------------------------------------------------
//        for (int i = 0; i < 26; i++) {
//            vyhladavanie.add(new RowItem("i", "Karol", "Kamo", "8", "4", "409"));
//        }
//        populateList();
////        populateOnClickList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataFromBackend();
    }

    private void getDataFromBackend() {
        String filter = "";
        //http://54.169.86.172:8000/sap/opu/odata/SAP/ZSUMMER_SRV/PersonSet?$format=json&$filter=IdPerson eq 42 and FirstName eq 'Rudolf' and LastName eq 'Seman'
        if (null != name2Search && !"".equals(name2Search.toString())) {
            filter += "FirstName eq '"+name2Search+"' ";
        }
        if (null != surname2Search && !"".equals(surname2Search.toString())) {
            filter += "and LastName eq '"+surname2Search+"' ";
        }
        if (null != idEmp2Search && !"".equals(idEmp2Search.toString())) {
            filter += "and IdPerson eq '"+idEmp2Search+"' ";
        }

        //if filter starts with and - remove it
        if (filter.startsWith("and")) {
            filter = filter.substring(3);
        }

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
                    vyhladavanie = new ArrayList<RowItem>();
                    //get throught the results
                    for (Result result : personSet.getD().getResults()) {
                        //log each one
                        Log.d("OkHttp", result.getFirstName() + " " + result.getLastName());
                        //and add it to the data set
                        vyhladavanie.add(new RowItem(result.getIdPerson()+"", result.getFirstName(), result.getLastName(), result.getIdBuilding(), result.getIdFloor(), result.getIdRoom()));
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

    private void populateList() {

        LayoutInflater layoutInflater = LayoutInflater.from(Vysledkyvyhladavania.this);
        Integer idenfier = 0;
        for (RowItem item : vyhladavanie) {
            //natiahni xml layout a vytvor objekt
            View itemView = layoutInflater.inflate(R.layout.rowitem, null);
            //priradime tag, aby sme ho vedeli volat neskor
            itemView.setTag(idenfier);

            //najdeme v R.layout.rowitem_edituj vsetky polia, ktore plnime
            TextView eID = ((TextView) itemView.findViewById(R.id.idecko));
            TextView eName = ((TextView) itemView.findViewById(R.id.name));
            TextView eSurname = ((TextView) itemView.findViewById(R.id.priezvisko));
            TextView eBudova = ((TextView) itemView.findViewById(R.id.budova));
            TextView ePoschodie = ((TextView) itemView.findViewById(R.id.poschodie));
            TextView eMiestnost = ((TextView) itemView.findViewById(R.id.miestnost));


            eID.setText(item.getIdecko());
            eName.setText(item.getMeno());
            eSurname.setText(item.getPriezvisko());
            eBudova.setText(item.getBudova());
            ePoschodie.setText(item.getPoschodie());
            eMiestnost.setText(item.getMiestnost());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Vysledkyvyhladavania.this, " Clicked", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Vysledkyvyhladavania.this,Editujmiestnost.class);
                    startActivity(intent);

                }
            });

            wrapper.addView(itemView);

            // STARY KOD
//        ArrayAdapter<RowItem> adapter = new ListViewAdapterRowItem();
//        listView.setAdapter(adapter);
//    }
//
//    private void populateOnClickList()
//    {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//                                        {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
//                                            {
//                                                int pos=position+1;
//                                                Toast.makeText(Vysledkyvyhladavania.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();
//
//                                                startActivity(new Intent(Vysledkyvyhladavania.this,Editujmiestnost.class));
//                                            }
//                                        }
//        );
        }
//
//    public class ListViewAdapterRowItem extends ArrayAdapter<RowItem> {
//
//        public ListViewAdapterRowItem() {
//            super(Vysledkyvyhladavania.this, R.layout.rowitem, vyhladavanie);
//        }
//
//        public View getView(int position, View covertView, ViewGroup parent) {
//            View itemView = covertView;
//            if (itemView == null) {
//                itemView = getLayoutInflater().inflate(R.layout.rowitem, parent, false);
//            }
//
//            RowItem rowItem = vyhladavanie.get(position);
//
//            TextView txt_idecko = (TextView) itemView.findViewById(R.id.idecko);
//            txt_idecko.setText(rowItem.getIdecko());
//
//            TextView txt_name = (TextView) itemView.findViewById(R.id.name);
//            txt_name.setText(rowItem.getMeno());
//
//            TextView txt_priezvisko = (TextView) itemView.findViewById(R.id.priezvisko);
//            txt_priezvisko.setText(rowItem.getPriezvisko());
//
//            TextView txt_budova = (TextView) itemView.findViewById(R.id.budova);
//            txt_budova.setText(rowItem.getBudova());
//
//            TextView txt_poschodie = (TextView) itemView.findViewById(R.id.poschodie);
//            txt_poschodie.setText(rowItem.getPoschodie());
//
//            TextView txt_miestnost = (TextView) itemView.findViewById(R.id.miestnost);
//            txt_miestnost.setText(rowItem.getMiestnost());
//
//            return itemView;
//        }
//    }
    }
}
