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
public class SimpleViewHolder extends RecyclerView.ViewHolder {

	@Bind(R.id.t_text)
	TextView textView;

	//this is a reference to the whole view, so we can make an onClick event in recycler view
	View me;

	public SimpleViewHolder(View itemView) {
		super(itemView);
		me = itemView;
		ButterKnife.bind(this, itemView);
	}
}
