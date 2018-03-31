package edu.csu.surajpokhrel.outfit_diary.utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.csu.surajpokhrel.outfit_diary.R;

public class ImageListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private GridView gv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        imgList = new ArrayList<>();
        gv= (GridView)findViewById(R.id.listViewImage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait. Loading list image...");
        progressDialog.show();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(AddDress.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }

                adapter = new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imgList);

                gv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });
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
