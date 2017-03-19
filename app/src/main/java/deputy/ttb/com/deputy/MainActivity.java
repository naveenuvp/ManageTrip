package deputy.ttb.com.deputy;

import android.*;
import android.Manifest;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import deputy.ttb.com.deputy.RestClientService.ApiService;
import deputy.ttb.com.deputy.RestClientService.RestClient;
import deputy.ttb.com.deputy.Utils.Utility;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {
    final private int REQUEST_CODE_FOR_PERMISSIONS = 220;
    private static String TAG = "MainActivity";

    private Context mContext = null;
    private GoogleApiClient mGoogleApiClient = null;
    private ApiService mApiService = null;

    @BindView(R.id.imageButton)
    Button imageButton;

    @BindView(R.id.shiftDetailsButton)
    Button shiftDetailsButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    /**
     *
     * Initialize app variables and other services
     */
    private void initialize() {
        mContext = this;
        mApiService = new RestClient().getApiService();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this); // Inject Butterknife
        ApplicationContext.getInstance().init(getApplicationContext());
        initialize();
        if (Build.VERSION.SDK_INT >= 23) {
            // Request required for permissions
            requestForMultiplePermissions();
        }
    }

    @Override
    public void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @OnClick({R.id.imageButton, R.id.shiftDetailsButton})
    public void startOrStopShift(View view) {
        switch (view.getId()){
            case R.id.imageButton:
                startOrStopShift();
                break;
            case R.id.shiftDetailsButton:
                showShifts();
                break;
            default:
                break;
        }
    }

    /**
     * Function Starts or Stops the shift
     */
    private void startOrStopShift(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Show Error message
            showPermissionDeniedError();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(imageButton.getTag().toString().equals(getString(R.string.start_shift))){
            startShift(location);
        }
        else{
            endShift(location);
        }
    }

    /**
     * Update Shift Tag
     * @param tag
     */
    private void updateShiftTag(String tag){
        imageButton.setTag(tag);
        imageButton.setText(tag);
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Get Shift Details
     */
    private void showShifts(){
        Fragment fragment   =   new ShiftDetailsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main, fragment)
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    /**
     *
     *Request for multiple permissions
     *
     */
    private boolean requestForMultiplePermissions(){
        List<String> requiredPermissionsList = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION)) requiredPermissionsList.add("GPS");
        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_COARSE_LOCATION)) requiredPermissionsList.add("Course Location");
        if (permissionsList.size() > 0) {
            if (requiredPermissionsList.size() > 0) {
                // Request custom message to user
                String message =  String.format(getResources().getString(R.string.location_permission_string), requiredPermissionsList.get(0));
                for (int i = 1; i < requiredPermissionsList.size(); i++)
                    message = message + ", " + requiredPermissionsList.get(i);
                ActivityCompat.requestPermissions(MainActivity.this, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_FOR_PERMISSIONS);
                return false;
            }
            ActivityCompat.requestPermissions(MainActivity.this, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_FOR_PERMISSIONS);
            return false;
        }
        return true;
    }

    /**
     *
     * Add Permission list
     * @param permissionsList
     * @param permission
     * @return
     */
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_FOR_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Log.v(TAG, "Permissions granted");
                } else {
                    // Permission Denied
                    showPermissionDeniedError();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Start shift
      * @param location
     */
    private void startShift(Location location){
        String today    =   Utility.getISO8601Date();
        Map<String, String> shiftDetailsMap  =   new HashMap<String, String>();
        shiftDetailsMap.put("time",   today);
        shiftDetailsMap.put("latitude",    Double.toString(location.getLatitude()));
        shiftDetailsMap.put("longitude",    Double.toString(location.getLongitude()));
        Call<ResponseBody> request  =   mApiService.startShift(shiftDetailsMap);
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.message());
                updateShiftTag(getString(R.string.end_shift));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    /**
     * End shift
     * @param location
     */
    private void endShift(Location location){
        String today    =   Utility.getISO8601Date();
        Map<String, String> shiftDetailsMap  =   new HashMap<String, String>();
        shiftDetailsMap.put("time",   today);
        shiftDetailsMap.put("latitude",    Double.toString(location.getLatitude()));
        shiftDetailsMap.put("longitude",    Double.toString(location.getLongitude()));

        Call<ResponseBody> request  =   mApiService.endShift(shiftDetailsMap);
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.message());
                updateShiftTag(getString(R.string.start_shift));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    /**
     * Show permission denied error
     */
    private void showPermissionDeniedError(){
        // Permission Denied
        String errorMessage =   String.format(getResources().getString(R.string.gps_permission_denied), getResources().getString(R.string.app_name));
        Log.v(TAG, "Permissions denied");
        Utility.showMessageOKCancel(mContext, errorMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utility.openDeviceAppSettingsScreen(mContext);
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed");
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        Log.d(TAG, "LocationSettingsResult ");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "location details");
    }
}