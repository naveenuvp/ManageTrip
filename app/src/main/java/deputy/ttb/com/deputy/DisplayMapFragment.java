package deputy.ttb.com.deputy;

import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import deputy.ttb.com.deputy.GreenDaoDB.DaoSession;
import deputy.ttb.com.deputy.Model.Shifts;
import deputy.ttb.com.deputy.RestClientService.ApiService;
import deputy.ttb.com.deputy.RestClientService.RestClient;
import deputy.ttb.com.deputy.Utils.Utility;

/**
 * Created by naveenu on 19/03/2017.
 */

public class DisplayMapFragment  extends Fragment{

    private static Context mContext  =	null;
    private static String TAG = "DisplayMapFragment";

    private ApiService mApiService = null;
    private DaoSession daoSession  = null;

    private GoogleMap mGoogleMap = null;
    private Shifts  shiftsDetails   =   null;

    @BindView(R.id.textViewStartTime)
    TextView textViewStartTime;

    @BindView(R.id.textViewEndTime)
    TextView textViewEndTime;

    @BindView(R.id.mapView)
    MapView mapView;

    public DisplayMapFragment newInstance(Bundle params){
        DisplayMapFragment fragment = new DisplayMapFragment();
        fragment.setArguments(params);
        return fragment;
    }
    // Init variables
    private void initialize(View view, Bundle savedInstanceState){
        mContext	=	getActivity();
        daoSession  =   ApplicationContext.getInstance().getDaoSession();
        mApiService =   new RestClient().getApiService();

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(mapReadyCallbackHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapview, container, false);
        ButterKnife.bind(this, view);
        initialize(view, savedInstanceState);

        if (getArguments() != null) {
            Bundle params   =   getArguments();
            shiftsDetails   =   params.getParcelable("map_details");
        }
        return view;
    }

    private OnMapReadyCallback mapReadyCallbackHandler = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap = googleMap;
            displayMapView();
        }
    };

    /**
     * Display Map View
     */
    private void displayMapView(){
        if(shiftsDetails.getStart() != null){
            textViewStartTime.setText(getString(R.string.start_shift_time) +":"+  Utility.getDate(shiftsDetails.getStart()));
        }
        if(shiftsDetails.getEnd() != null && shiftsDetails.getEnd().length() > 0){
            textViewEndTime.setText(mContext.getString(R.string.end_shift_time) +":"+ Utility.getDate(shiftsDetails.getEnd()));
        }
        else{
            textViewEndTime.setText(mContext.getString(R.string.end_shift_time) +":"+ mContext.getString(R.string.shift_in_progress));
        }

        if (mGoogleMap != null) {
            final double latitude = Double.parseDouble(shiftsDetails.getStartLatitude());
            final double longitude = Double.parseDouble(shiftsDetails.getStartLongitude());
            LatLng startLatLand = new LatLng(latitude, longitude);
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                // Show rationale and request permission.
                String errorMessage =   String.format(getResources().getString(R.string.gps_permission_denied), getResources().getString(R.string.app_name));
                Utility.showMessageOKCancel(mContext, errorMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utility.openDeviceAppSettingsScreen(mContext);
                    }
                });
            }
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setCompassEnabled(true);

            // Create marker
            MarkerOptions startMarker = new MarkerOptions().position(startLatLand);
            // ROSE color icon
            startMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            // Adding marker
            mGoogleMap.addMarker(startMarker);

            final double latitudeEnd = (double) Double.parseDouble(shiftsDetails.getEndLatitude());
            final double longitudeEnd = (double) Double.parseDouble(shiftsDetails.getEndLongitude());
            LatLng endLatLang = new LatLng(latitudeEnd, longitudeEnd);

            // Create marker
            MarkerOptions endMarker = new MarkerOptions().position(endLatLang);
            // VIOLET color icon
            endMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            // Adding marker
            mGoogleMap.addMarker(endMarker);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(endLatLang).zoom(12.0f).build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
