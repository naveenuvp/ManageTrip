package deputy.ttb.com.deputy;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import deputy.ttb.com.deputy.Adapter.ShiftDetailsAdapter;
import deputy.ttb.com.deputy.GreenDaoDB.DaoSession;
import deputy.ttb.com.deputy.GreenDaoDB.ShiftDetails;
import deputy.ttb.com.deputy.GreenDaoDB.ShiftDetailsDao;
import deputy.ttb.com.deputy.Model.Shifts;
import deputy.ttb.com.deputy.RestClientService.ApiService;
import deputy.ttb.com.deputy.RestClientService.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by naveenu on 18/03/2017.
 */

public class ShiftDetailsFragment extends Fragment implements MapClickListener{

    private static Context mContext  =	null;
    private static String TAG = "ShiftDetailsFragment";

    private List<Shifts> mShiftDetailListArray   =   new ArrayList<>();
    private ListView shiftListView  =   null;
    private ShiftDetailsAdapter shiftDetailsAdapter    =   null;
    private ApiService mApiService = null;
    private DaoSession  daoSession  =   null;

    // Public constructor
    public ShiftDetailsFragment newInstance(Bundle params){
        ShiftDetailsFragment fragment = new ShiftDetailsFragment();
        fragment.setArguments(params);
        return fragment;
    }

    // Init variables
    private void initialize(View view){
        mContext	=	getActivity(); // Get context.
        daoSession                  =   ApplicationContext.getInstance().getDaoSession();
        mApiService = new RestClient().getApiService();

        shiftListView   =   (ListView) view.findViewById(R.id.shift_details_list_item);
        shiftDetailsAdapter    =   new ShiftDetailsAdapter(mContext, mShiftDetailListArray, this);
        shiftListView.setAdapter(shiftDetailsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shift_details, container, false);
        initialize(view);
        getShifts();
        displayShiftDetailsListItems();
        return view;
    }

    /**
     * Get Shift Details
     */
    private void getShifts(){
        Call<Shifts> request  =   mApiService.getShiftDetails();
        Log.d(TAG, "Getting shift details...");
        request.enqueue(new Callback<Shifts>() {
            @Override
            public void onResponse(Call<Shifts> call, Response<Shifts> response) {
                Log.d(TAG, "Response shift details : "+response);
                if(response.body() != null){
                    String shiftDetailsJson   =   new Gson().toJson(response.body(), Shifts.class);
                    saveShiftDetails(shiftDetailsJson);
                    displayShiftDetailsListItems();
                }
                else{
                    Toast.makeText(getActivity(), getText(R.string.shift_details_not_available), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<Shifts> call, Throwable t) {
                Log.d(TAG, "Error "+t.getMessage());
            }
        });
    }

    /**
     * Save shift details and show it user
     * @param jsonShiftDetails
     */
    private void saveShiftDetails(String jsonShiftDetails){
        ShiftDetailsDao shiftDetailsDao =   daoSession.getShiftDetailsDao();
        if(shiftDetailsDao.count() == 0){
            ShiftDetails    shiftDetails    =   new ShiftDetails();
            shiftDetails.setShiftDetails(jsonShiftDetails);
            daoSession.insertOrReplace(shiftDetails);
        }
        else{
            ShiftDetails    shiftDetails    =   shiftDetailsDao.queryBuilder().list().get(0);
            ShiftDetails    shiftDetailsObject    =   shiftDetailsDao.queryBuilder().where(ShiftDetailsDao.Properties.ShiftDetails.eq(shiftDetails.getShiftDetails())).list().get(0);
            shiftDetailsObject.setShiftDetails(jsonShiftDetails);
            shiftDetailsDao.update(shiftDetailsObject);
        }
    }

    /**
     * Display shift details
     */
    private void displayShiftDetailsListItems(){
        if(daoSession.getShiftDetailsDao().count() > 0){
            ShiftDetails    shiftDetails    =   daoSession.getShiftDetailsDao().queryBuilder().list().get(0);
            String shiftJsonData    =   shiftDetails.getShiftDetails();
            if(shiftJsonData != null){
                try {
                    JSONArray   jsonArray   =   new JSONArray(shiftJsonData);
                    if(jsonArray.length() > 0){
                        mShiftDetailListArray.clear();
                    }
                    for(int i=0; i<jsonArray.length(); i++){
                        Shifts shifts   =   new Gson().fromJson(jsonArray.get(i).toString(), Shifts.class);
                        mShiftDetailListArray.add(shifts);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shiftDetailsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void selectedMap(int position) {
        Bundle params   =   new Bundle();
        params.putParcelable("map_details",mShiftDetailListArray.get(position));
        Fragment fragment   =   new DisplayMapFragment().newInstance(params);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main, fragment)
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }
}
