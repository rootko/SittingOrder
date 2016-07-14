package sk.halmi.sittingorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sk.halmi.sittingorder.api.model.Miestnost_tono;
import sk.halmi.sittingorder.R;

/**
 * Created by FPTSlovakia on 7/12/2016.
 */
public class MiestnostAdapter_tono extends ArrayAdapter<Miestnost_tono>{
    public MiestnostAdapter_tono(Context context, ArrayList<Miestnost_tono> miestnosti){
        super(context, 0, miestnosti);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Miestnost_tono miestnost = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.miestnost_layout_tono, parent, false);
        }
        // Lookup view for data population
        TextView tvMiestnost = (TextView) convertView.findViewById(R.id.cisloMiestnosti);
        TextView tvObsadenost = (TextView) convertView.findViewById(R.id.obsadenost);
        // Populate the data into the template view using the data object
        tvMiestnost.setText(miestnost.getCislo());
        tvObsadenost.setText(miestnost.getObsadenost()+"/"+miestnost.getKapacita());
        // Return the completed view to render on screen
        return convertView;
    }
}
