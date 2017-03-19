package deputy.ttb.com.deputy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import deputy.ttb.com.deputy.MapClickListener;
import deputy.ttb.com.deputy.Model.Shifts;
import deputy.ttb.com.deputy.R;
import deputy.ttb.com.deputy.Utils.Utility;

/**
 * Created by naveenu on 18/03/2017.
 */

public class ShiftDetailsAdapter extends BaseAdapter {
    private static String TAG   =   "ShiftDetailsAdapter";

    private LayoutInflater mInflater    =   null;
    private Context mContext =   null;
    private List<Shifts> mShiftItemsArray   =   null;
    public MapClickListener mapClickListener    =   null;

    public ShiftDetailsAdapter (Context context, List<Shifts> shiftItemsArray, MapClickListener listener){
        mInflater   =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext    =   context;
        this.mShiftItemsArray   =   shiftItemsArray;
        this.mapClickListener   =   listener;
    }

    @Override
    public int getCount() {
        return mShiftItemsArray.size();
    }

    @Override
    public Object getItem(int position) {
        return mShiftItemsArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Shifts shifts   =   mShiftItemsArray.get(position);
        ViewHolder holder;
        if(view != null){
            holder  =   (ViewHolder) view.getTag();
        }
        else{
            view=   mInflater.inflate(R.layout.shift_details_list_item, parent, false);
            holder  =   new ViewHolder(view);
            view.setTag(holder);
        }

        ButterKnife.bind(this, view);

        holder.textViewStartTime.setText(mContext.getString(R.string.start_shift_time) +":"+ shifts.getStart());
        if(shifts.getEnd() != null && shifts.getEnd().length() > 0){
            holder.textViewEndTime.setText(mContext.getString(R.string.end_shift_time) +":"+ Utility.getDate(shifts.getEnd()));
        }
        else{
            holder.textViewEndTime.setText(mContext.getString(R.string.end_shift_time) +":"+ mContext.getString(R.string.shift_in_progress));
        }

        Picasso.with(mContext)
                .load(shifts.getImage())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapClickListener.selectedMap(position);
            }
        });
        return view;
    }

    class ViewHolder{
        @BindView(R.id.imageView)
        ImageView imageView;

        @BindView(R.id.textViewStartTime)
        TextView textViewStartTime;

        @BindView(R.id.textViewEndTime)
        TextView textViewEndTime;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
