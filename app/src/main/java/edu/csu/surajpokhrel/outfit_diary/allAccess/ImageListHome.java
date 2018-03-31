package edu.csu.surajpokhrel.outfit_diary.allAccess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import edu.csu.surajpokhrel.outfit_diary.utility.ImageListAdapter;
import edu.csu.surajpokhrel.outfit_diary.utility.ImageUpload;
import edu.csu.surajpokhrel.outfit_diary.R;

/**
 * Created by surajpokhrel on 25/9/17.
 */

public class ImageListHome extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    // private ListView lv;
    private GridView gv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private static String PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        imgList = new ArrayList<>();
        gv= (GridView)findViewById(R.id.listViewImage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading list image...");
        progressDialog.show();
        String s = getIntent().getStringExtra("ACTIVITY_CHECK");
       switch (s){
           case "beach1":
               PATH="categories/Beach";
               break;
           case "celebration1":
               PATH="categories/Celebration";
               break;
           case "formal1":
               PATH="categories/Formal";
               break;
           case "interview1":
               PATH="categories/Interview";
               break;
           case "party1":
               PATH="categories/Party";
               break;
           case "pregnancy1":
               PATH="categories/Pregnancy";
               break;
           case "summer1":
               PATH="categories/Summer";
               break;
           case "winter1":
               PATH="categories/Winter";
               break;
           default: PATH="image";
       }

       mDatabaseRef = FirebaseDatabase.getInstance().getReference().child(PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }

                adapter = new ImageListAdapter(ImageListHome.this, R.layout.image_item, imgList);

                gv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });

        Toast.makeText(getApplicationContext(),"Your Wardrobe",Toast.LENGTH_SHORT).show();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(imgList.get(i).getUrl()));
                startActivity(intent);

            }

        });

    }
}
