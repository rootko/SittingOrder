package sk.halmi.sittingorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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
        TextView tvPoschodie = (TextView) convertView.findViewById(R.id.cisloPoschodia);
        // Populate the data into the template view using the data object
        tvMiestnost.setText(miestnost.getCislo());
        tvPoschodie.setText(miestnost.getPoschodie());
        // Return the completed view to render on screen
        return convertView;
    }
}
