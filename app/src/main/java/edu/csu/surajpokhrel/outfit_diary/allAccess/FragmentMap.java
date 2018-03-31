package edu.csu.surajpokhrel.outfit_diary.allAccess;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.csu.surajpokhrel.outfit_diary.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMap extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    TextView mName,mLat,mLog;
    String lat,lon;
    SupportMapFragment mMapFragment;
    int a;

    public FragmentMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maps_visit, container, false);

        Intent intent = getActivity().getIntent();

        lat = (intent.getStringExtra(LocationActivity.PLACE_LAT)) ;
        lon=(intent.getStringExtra(LocationActivity.PLACE_LON)) ;
       // Toast.makeText(getActivity(),"Hello",Toast.LENGTH_LONG).show();

        System.out.println("welcome: "+ intent.getStringExtra(LocationActivity.PLACE_LAT));
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);
        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng s1 = new LatLng(Float.parseFloat(lat), Float.parseFloat(lon));
        LatLng s2 = new LatLng(-33.891475, 151.276684);
        LatLng s3 = new LatLng(27.175015, 78.042155);
        LatLng s4 = new LatLng(-27.987850, 86.925026);
        mMap.addMarker(new MarkerOptions().position(s1).title("Opera House, Sydney"));
        // mMap.addMarker(new MarkerOptions().position(s2).title("Bondi Beach, Sydney"));
        //mMap.addMarker(new MarkerOptions().position(s3).title("Taj Mahal, India"));
        // mMap.addMarker(new MarkerOptions().position(s4).title("Mount Everest, Nepal"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(s1));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(s2));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(s3));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(s4));
    }
}
