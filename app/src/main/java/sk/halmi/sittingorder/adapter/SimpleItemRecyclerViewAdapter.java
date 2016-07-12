package sk.halmi.sittingorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import sk.halmi.sittingorder.R;

/**
 * Created by FS0149 on 12.7.2016.
 */
public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
	//reference to context
	private Context context;

	//our list of values that we want to display in recycler view
	private final List<String> mValues;

	//constructor fills out our mValues
	public SimpleItemRecyclerViewAdapter(Context context, List<String> data) {
		this.context = context;
		mValues = data;
	}

	//loads xml layout to use as each list item and passes it to a constructor of view holder
	@Override
	public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_card, parent, false);
		return new SimpleViewHolder(view);
	}

	//binds each list item - based on position in list, displays data
	@Override
	public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
		holder.textView.setText(mValues.get(position));

		//here I can specify an onClickListener:
		holder.me.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Clicked on " + mValues.get(position), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public int getItemCount() {
		return mValues.size();
	}
}
