package edu.csu.surajpokhrel.outfit_diary.utility;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.csu.surajpokhrel.outfit_diary.allAccess.LocationActivity;
import edu.csu.surajpokhrel.outfit_diary.R;

public class MapsVisitActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView mName,mLat,mLog;
    String lat,lon;

    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_visit);
       Intent intent = getIntent();

        lat = (intent.getStringExtra(LocationActivity.PLACE_LAT)) ;
        lon=(intent.getStringExtra(LocationActivity.PLACE_LON)) ;

        System.out.println("welcome: "+ intent.getStringExtra(LocationActivity.PLACE_LAT));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
