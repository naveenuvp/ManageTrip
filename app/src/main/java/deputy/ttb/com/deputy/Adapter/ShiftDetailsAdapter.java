package deputy.ttb.com.deputy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import deputy.ttb.com.deputy.Model.Shifts;
import deputy.ttb.com.deputy.R;

/**
 * Created by naveenu on 18/03/2017.
 */

public class ShiftDetailsAdapter extends BaseAdapter {
    private static String TAG   =   "ShiftDetailsAdapter";

    private LayoutInflater mInflater    =   null;
    private Context context =   null;
    private List<Shifts> mShiftItemsArray   =   null;

    public ShiftDetailsAdapter (Context context, List<Shifts> shiftItemsArray){
        mInflater   =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context    =   context;
        this.mShiftItemsArray   =   shiftItemsArray;
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
    public View getView(int position, View view, ViewGroup parent) {
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

        holder.textViewStartTime.setText(shifts.getStart());
        holder.textViewEndTime.setText(shifts.getEnd());

        return view;
    }


    class ViewHolder{
        @BindView(R.id.imageView)
        ImageView imageView;

        @BindView(R.id.textViewStartTime)
        TextView textViewStartTime;

        @BindView(R.id.textViewEndTime)
        TextView textViewEndTime;

        @BindView(R.id.mapView)
        MapView mapView;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }


    }
}
