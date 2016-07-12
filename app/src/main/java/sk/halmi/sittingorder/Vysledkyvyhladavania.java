package sk.halmi.sittingorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

;

public class Vysledkyvyhladavania extends Activity {

    private List<RowItem> vyhladavanie = new ArrayList<RowItem>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vysledkyvyhladavania);

        listView=(ListView)findViewById(R.id.listView);

        //------------------------------ NAPLNANIE LISTVIEWU ----------------------------------------------------
        for (int i=0;i<6;i++) {
            vyhladavanie.add(new RowItem(i+"Karol", "Kamo", "8", "4", "409"));
        }
        populateList();
        populateOnClickList();
    }

    private void populateList() {
        ArrayAdapter<RowItem> adapter = new ListViewAdapterRowItem();
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
            Toast.makeText(Vysledkyvyhladavania.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(Vysledkyvyhladavania.this,Editujmiestnost.class));
        }
        }
    );
    }

    public class ListViewAdapterRowItem extends ArrayAdapter<RowItem> {

        public ListViewAdapterRowItem() {
            super(Vysledkyvyhladavania.this, R.layout.rowitem, vyhladavanie);
        }

        public View getView(int position, View covertView, ViewGroup parent) {
            View itemView = covertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.rowitem, parent, false);
            }

            RowItem rowItem = vyhladavanie.get(position);

            TextView txt_name = (TextView) itemView.findViewById(R.id.name);
            txt_name.setText(rowItem.getMeno());

            TextView txt_priezvisko = (TextView) itemView.findViewById(R.id.priezvisko);
            txt_priezvisko.setText(rowItem.getPriezvisko());

            TextView txt_budova = (TextView) itemView.findViewById(R.id.budova);
            txt_budova.setText(rowItem.getBudova());

            TextView txt_poschodie = (TextView) itemView.findViewById(R.id.poschodie);
            txt_poschodie.setText(rowItem.getPoschodie());

            TextView txt_miestnost = (TextView) itemView.findViewById(R.id.miestnost);
            txt_miestnost.setText(rowItem.getMiestnost());

            return itemView;
        }
    }
}
