package sk.halmi.sittingorder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Editujmiestnost extends AppCompatActivity {

    private List<RowItemEdituj> zoznammien = new ArrayList<RowItemEdituj>();
    List<RowItemEdituj> zmenamien = new ArrayList<RowItemEdituj>();
    ListView listView;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editujmiestnost1);

        listView=(ListView)findViewById(R.id.listView);

        //FLOATING BUTTON
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // -------------------------- NAPLNANIE LISTVIEWU -----------------------------------------------
        zoznammien.add(new RowItemEdituj("Karol", "Kamo"));
        zoznammien.add(new RowItemEdituj("Julius", "Kamo"));

        // ---------------------------- KAPACITA -------------------------------------------------------

        int velikost=zoznammien.size();
        for (int j=0;j<10-velikost;j++)
        {
            zoznammien.add(new RowItemEdituj("", ""));
        }

        populateList();
        populateOnClickList();
        saveList();


    }

    //Kliknutie na floating button ULOZIT
    private void saveList()
    {
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int velkost=zoznammien.size();
                if (velkost!=0)
                {
                RowItemEdituj rowItemEdituj = zoznammien.get(velkost-1);

                System.out.println("VELKOST "+velkost);

                if (rowItemEdituj.getMeno().toString().equals(""))
                {
                    Toast.makeText(Editujmiestnost.this,"UKLADAM "+velkost+"-1" , Toast.LENGTH_LONG).show();
                    for (int i=0;i<velkost-1;i++)
                    {
                        RowItemEdituj rowItemEdituj_save = zoznammien.get(i);
                    }
                }
                else
                {
                    Toast.makeText(Editujmiestnost.this,"UKLADAM "+velkost , Toast.LENGTH_LONG).show();
                    for (int i=0;i<velkost;i++)
                    {
                        RowItemEdituj rowItemEdituj_save = zoznammien.get(i);
                    }
                }
            }}
        });
    }

    private void populateList() {
        ArrayAdapter<RowItemEdituj> adapter = new ListViewAdapterRowItemEdituj();
        listView.setAdapter(adapter);
    }

    private void populateOnClickList()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                                            {
          int pos=position+1;
          Toast.makeText(Editujmiestnost.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();}
                                        }
        );
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

            final EditText txt_name = (EditText) itemView.findViewById(R.id.name);
            txt_name.setText(rowItemEdituj.getMeno());

            EditText txt_priezvisko = (EditText) itemView.findViewById(R.id.priezvisko);
            txt_priezvisko.setText(rowItemEdituj.getPriezvisko());
            final String menicko=rowItemEdituj.getMeno();

            ImageButton imageButton=(ImageButton) itemView.findViewById(R.id.btndelete);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Editujmiestnost.this, "Vyhodime "+ menicko, Toast.LENGTH_SHORT).show();

                    //vyhadzovaniee ludi
                    zoznammien.remove(position);
                    notifyDataSetChanged();
                }
            });

            return itemView;
        }
    }
}
