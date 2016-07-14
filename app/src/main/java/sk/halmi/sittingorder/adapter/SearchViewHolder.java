package sk.halmi.sittingorder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sk.halmi.sittingorder.R;

/**
 * Created by FS0149 on 12.7.2016.
 */
public class SearchViewHolder extends RecyclerView.ViewHolder {

	@Bind(R.id.idecko)
	TextView t_id;

	@Bind(R.id.name)
	TextView t_name;

	@Bind(R.id.priezvisko)
	TextView t_surname;

	@Bind(R.id.budova)
	TextView t_building;

	@Bind(R.id.poschodie)
	TextView t_floor;

	@Bind(R.id.miestnost)
	TextView t_room;

	//this is a reference to the whole view, so we can make an onClick event in recycler view
	View me;

	public SearchViewHolder(View itemView) {
		super(itemView);
		me = itemView;
		ButterKnife.bind(this, itemView);
	}
}
