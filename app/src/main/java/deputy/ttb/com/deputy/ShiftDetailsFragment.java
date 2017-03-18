package deputy.ttb.com.deputy;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by naveenu on 18/03/2017.
 */

public class ShiftDetailsFragment extends Fragment{

    private static Context context  =	null;

    public ShiftDetailsFragment newInstance(Bundle params){
        ShiftDetailsFragment fragment = new ShiftDetailsFragment();
        fragment.setArguments(params);
        return fragment;
    }
    // Init variables
    private void initialize(){
        context	=	getActivity(); // Get context.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shift_details, container, false);


        return view;
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


}
