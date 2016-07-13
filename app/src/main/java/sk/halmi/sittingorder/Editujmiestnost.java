package sk.halmi.sittingorder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import sk.halmi.sittingorder.api.model.RowItemEdituj;

public class Editujmiestnost extends AppCompatActivity {

    private List<RowItemEdituj> zoznammien = new ArrayList<RowItemEdituj>();
    List<RowItemEdituj> zmenamien = new ArrayList<RowItemEdituj>();
//    ListView listView;
//    FloatingActionButton fab;
    @Bind(R.id.list_wrapper)
    LinearLayout wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editujmiestnost1);
        ButterKnife.bind(this);

//        listView=(ListView)findViewById(R.id.listView);
        findViewById(R.id.b_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Editujmiestnost.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

//        //FLOATING BUTTON
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // -------------------------- NAPLNANIE LISTVIEWU -----------------------------------------------
        zoznammien.add(new RowItemEdituj("1","Karol", "Kamo"));
        zoznammien.add(new RowItemEdituj("2","Julius", "Kamo"));

        // ---------------------------- KAPACITA -------------------------------------------------------

        int velikost=zoznammien.size();
        for (int j=0;j<20-velikost;j++)
        {
            zoznammien.add(new RowItemEdituj("","", ""));
        }

        populateList();
        populateOnClickList();
        //saveList();


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
        LayoutInflater layoutInflater = LayoutInflater.from(Editujmiestnost.this);
        Integer idenfier = 0;
        for (RowItemEdituj item : zoznammien) {
            //natiahni xml layout a vytvor objekt
            View itemView = layoutInflater.inflate(R.layout.rowitem_edituj, null);
            //priradime tag, aby sme ho vedeli volat neskor
            itemView.setTag(idenfier);

            //najdeme v R.layout.rowitem_edituj vsetky polia, ktore plnime
            final EditText eID = ((EditText)itemView.findViewById(R.id.idecko));
            final EditText eName = ((EditText)itemView.findViewById(R.id.name));
            final EditText eSurname = ((EditText)itemView.findViewById(R.id.priezvisko));
            eID.setText(item.getIdecko());
            eName.setText(item.getMeno());
            eSurname.setText(item.getPriezvisko());
            itemView.findViewById(R.id.btndelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eID.setText("");
                    eName.setText("");
                    eSurname.setText("");
                }
            });

            wrapper.addView(itemView);
        }
//        ArrayAdapter<RowItemEdituj> adapter = new ListViewAdapterRowItemEdituj();
//        listView.setAdapter(adapter);
    }

    private void populateOnClickList()
    {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//                                        {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
//                                            {
//                                                int pos=position+1;
//                                                Toast.makeText(Editujmiestnost.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();}
//                                        }
//        );
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
                        Toast.makeText(Editujmiestnost.this, "Vyhodime " + menicko, Toast.LENGTH_SHORT).show();
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
}
