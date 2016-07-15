package sk.halmi.sittingorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.List;

import sk.halmi.sittingorder.Editujmiestnost;
import sk.halmi.sittingorder.R;
import sk.halmi.sittingorder.api.model.RowItem;

/**
 * Created by FS0149 on 12.7.2016.
 */
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchViewHolder> {
	//reference to context
	private Context context;

	//our list of values that we want to display in recycler view
	private final List<RowItem> mValues;

	// Allows to remember the last item shown on screen
	private int lastPosition = -1;

	//constructor fills out our mValues
	public SearchRecyclerViewAdapter(Context context, List<RowItem> data) {
		this.context = context;
		mValues = data;
	}

	//loads xml layout to use as each list item and passes it to a constructor of view holder
	@Override
	public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem, parent, false);
		return new SearchViewHolder(view);
	}

	//binds each list item - based on position in list, displays data
	@Override
	public void onBindViewHolder(final SearchViewHolder holder, final int position) {
		holder.t_id.setText(mValues.get(position).getIdecko());
		holder.t_name.setText(mValues.get(position).getMeno());
		holder.t_surname.setText(mValues.get(position).getPriezvisko());
		holder.t_building.setText(mValues.get(position).getBudova());
		holder.t_floor.setText(mValues.get(position).getPoschodie());
		holder.t_room.setText(mValues.get(position).getMiestnost());

		//here I can specify an onClickListener:
		holder.me.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(context, "Clicked on " + mValues.get(position), Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(context, Editujmiestnost.class);
				intent.putExtra("building",mValues.get(position).getBudova() );
				intent.putExtra("floor", mValues.get(position).getPoschodie());
				intent.putExtra("room", mValues.get(position).getMiestnost());

				Log.d("Search", mValues.get(position).getBudova() + ":" + mValues.get(position).getPoschodie() + ":" + mValues.get(position).getMiestnost() + ": cap not defined");
				context.startActivity(intent);
				((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				((Activity)context).finish();

			}
		});
		// Here you apply the animation when the view is bound
		setAnimation(holder.me, position);
	}

	@Override
	public int getItemCount() {
		return mValues.size();
	}

	/**
	 * Here is the key method to apply the animation
	 */
	private void setAnimation(View viewToAnimate, int position)
	{
		// If the bound view wasn't previously displayed on screen, it's animated
		if (position > lastPosition)
		{
			Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
			viewToAnimate.startAnimation(animation);
			lastPosition = position;
		}
	}
}
