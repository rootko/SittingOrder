package sk.halmi.sittingorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VyhladajZamestnanca extends AppCompatActivity {

    EditText meno;
    EditText priezvisko;
    EditText id;
    TextView vysledok;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editujmiestnost);
        meno = (EditText)findViewById(R.id.ETmeno);
        priezvisko = (EditText)findViewById(R.id.ETpriezvisko);
        vysledok = (TextView)findViewById(R.id.TVvysledok);
        id = (EditText) findViewById(R.id.ETid);

        Button potvrd = (Button)findViewById(R.id.enter);
        potvrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= meno.getText().toString();
                String surname = priezvisko.getText().toString();
                String idEmp = id.getText().toString();
                vysledok.setText(name + " " + surname + " "+ idEmp);
            }
        });

        Button zrus = (Button)findViewById(R.id.zrus);
        zrus.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                vysledok.setText("nic ste nezadali");
            }
        }
        );
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
