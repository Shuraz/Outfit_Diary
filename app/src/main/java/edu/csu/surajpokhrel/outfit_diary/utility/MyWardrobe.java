package edu.csu.surajpokhrel.outfit_diary.utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.csu.surajpokhrel.outfit_diary.R;

public class MyWardrobe extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private GridView gv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;
    private static String userID;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wardrobe);


        gv= (GridView)findViewById(R.id.listViewImage_MyWardrobe);

        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait. Loading list image...");
        progressDialog.show();
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user =firebaseAuth.getCurrentUser();
        userID = user.getUid();

        displayList();



        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(imgList.get(i).getUrl()));
                startActivity(intent);

            }

        });

        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageUpload imageUpload=imgList.get(i);
                showUpdateDeleteDialog(imageUpload.getImageId(),imageUpload.getName(),imageUpload.getUrl());
                return true;
            }
        });
    }

    private void displayList() {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(AddDress.FB_DATABASE_PATH).child(userID).child("image");
        imgList = new ArrayList<>();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //Searching image in FB database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }


                //Initilize the adapter
                adapter = new ImageListAdapter(MyWardrobe.this, R.layout.image_item, imgList);
                gv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });
    }

    private void showUpdateDeleteDialog(final String photoId, final String picName, final String url){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.etUPname);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateImageName);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeletePic);

        dialogBuilder.setTitle(picName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name= editTextName.getText().toString().trim();
                if(!TextUtils.isEmpty(name) && name.equals(picName)){
                    deleteImage(photoId);
                    b.dismiss();
                    displayList();

                }
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  String name= editTextName.getText().toString().trim();
                if(!TextUtils.isEmpty(name)){
                    updateImage(photoId,name,url);
                    b.dismiss();
                    displayList();
                }

            }
        });

    }

    private boolean deleteImage(String id){
        //getting the specified image reference
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(AddDress.FB_DATABASE_PATH).child(userID).child("image");
        DatabaseReference dR = mDatabaseRef.child(id);
        //removing img
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Image Deleted", Toast.LENGTH_LONG).show();

        return true;

    }

    private boolean updateImage(String id, String name, String url) {
        //getting the specified image reference
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(AddDress.FB_DATABASE_PATH).child(userID).child("image");
        DatabaseReference dR = mDatabaseRef.child(id);

        //updating artist
        ImageUpload imageUpload= new ImageUpload(id, name, url);
        dR.setValue(imageUpload);
        Toast.makeText(getApplicationContext(), "Image Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
