package edu.csu.surajpokhrel.outfit_diary.allAccess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.csu.surajpokhrel.outfit_diary.R;

public class LocationActivity extends AppCompatActivity {
    public static final String PLACE_NAME = "edu.csu.surajpokhrel.outfit_diary.AllAccess.placeName";
    public static final String PLACE_LAT = "edu.csu.surajpokhrel.outfit_diary.AllAccess.lat";
    public static final String PLACE_LON = "edu.csu.surajpokhrel.outfit_diary.AllAccess.lon";


    EditText etName,etLat,etLong,etInfo;
    Button btPlace;
    ListView listViewPlaces;

    //list to store all places from firebase database
    List<LocationInfo> places;

    DatabaseReference databaseRefefPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //getting references of places node
        databaseRefefPlace= FirebaseDatabase.getInstance().getReference("places");
        //getting views
        etName=(EditText)findViewById(R.id.putname);
        etLat=(EditText)findViewById(R.id.putlat);
        etLong=(EditText)findViewById(R.id.putlog);
        etInfo=(EditText)findViewById(R.id.putinfo);
btPlace=(Button)findViewById(R.id.btAddPlace);
        listViewPlaces=(ListView)findViewById(R.id.placeList);
        places= new ArrayList<>();
        btPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlace();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseRefefPlace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
    //clear previous list of places
                places.clear();
                //iterating through all nodes

              for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                  LocationInfo locationInfo = postSnapshot.getValue(LocationInfo.class);
                  places.add(locationInfo);
              }
                //creating adapter
                LocListAdapter locListAdapter = new LocListAdapter(LocationActivity.this,places);
               //attaching adapter to listview
                listViewPlaces.setAdapter(locListAdapter);
                //attaching listener to list view
                listViewPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        LocationInfo locationInfo = places.get(i);
                        Intent intent = new Intent(getApplicationContext(), LocationWeatherActivity.class);

                        //putting place name ,lat , lon to intent
                        intent.putExtra(PLACE_NAME,locationInfo.getPlaceName());
                        intent.putExtra(PLACE_LAT,locationInfo.getLat());
                        intent.putExtra(PLACE_LON,locationInfo.getLog());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void addPlace(){
        String name= etName.getText().toString().trim();
        String lat = etLat.getText().toString().trim();
        String lon= etLong.getText().toString().trim();
        String info= etInfo.getText().toString().trim();

        if(!TextUtils.isEmpty(name)){
            String idloc=databaseRefefPlace.push().getKey();
            LocationInfo locationInfo= new LocationInfo(lat,lon,name,info);
            databaseRefefPlace.child(idloc).setValue(locationInfo);
            etName.setText("");
            etLat.setText("");
            etLong.setText("");
            etInfo.setText("");
            Toast.makeText(this,"Place added",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Please enter text again", Toast.LENGTH_LONG).show();
        }

    }
}
