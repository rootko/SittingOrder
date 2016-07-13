package sk.halmi.sittingorder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VyhladajZamestnanca extends AppCompatActivity {

//    EditText meno;
//    EditText priezvisko;
//    EditText id;
//    TextView vysledok;

    @Bind(R.id.ETmeno)
    EditText meno;
    @Bind(R.id.ETpriezvisko)
    EditText priezvisko;
    @Bind(R.id.ETid)
    EditText id;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById(R.id.buttonAlert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });
    }

//        meno = (EditText)findViewById(R.id.ETmeno);
//        priezvisko = (EditText)findViewById(R.id.ETpriezvisko);
//        vysledok = (TextView)findViewById(R.id.TVvysledok);
//        id = (EditText) findViewById(R.id.ETid);

//        Button potvrd = (Button)findViewById(R.id.enter);
//        potvrd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name= meno.getText().toString();
//                String surname = priezvisko.getText().toString();
//                String idEmp = id.getText().toString();
//                vysledok.setText(name + " " + surname + " "+ idEmp);
//            }
//        });

//        Button zrus = (Button)findViewById(R.id.zrus);
//        zrus.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View view){
//                vysledok.setText("nic ste nezadali");
//            }
//        }
//        );
//    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VyhladajZamestnanca.this);
        // Get the layout inflater
        LayoutInflater inflater = VyhladajZamestnanca.this.getLayoutInflater();
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
                        Intent results = new Intent(VyhladajZamestnanca.this, Vysledkyvyhladavania.class);
                        results.putExtra("name", name);
                        results.putExtra("surname", surname);
                        results.putExtra("idEmp", idEmp);
                        startActivity(results);
                        Log.d("Dialog", name + " " + surname + " "+ idEmp);

                    }
                })
                .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("Dialog", "nic ste nezadali");
                    }
                });

        builder.create().show();

    }


//    public void buttonClicked(View button){
//        String MENO = dajMeno(R.id.ETmeno);
//        String PRIEZVISKO = dajPriezvisko(R.id.ETpriezvisko);
//    }

//    //funkcia aby som dostala meno
//    protected String dajMeno(ETmeno)
//    {
//        EditText etVstup = ((EditText)findViewById(ETmeno));
//        String strVstup = etVstup.getText().toString();
//        String nMeno;
//        try
//        {
//            nMeno = strVstup;
//        }
//   catch();
//        return nMeno;
//    }
//
//   //funkcia aby som dostala priezvisko
//    protected String dajPriezvisko(ETpriezvisko)
//    {
//        EditText etVstup2 = ((EditText)findViewById(ETpriezvisko));
//        String strVstup2 = etVstup2.getText().toString();
//        String nPriezvisko;
//        try
//        {
//            nPriezvisko = strVstup2;
//        }
//        return nPriezvisko;
//    }
//
//    // TO DO- funkcia na spojenie meno a priezvisko a vypisalo ho

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
