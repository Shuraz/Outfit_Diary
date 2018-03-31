package edu.csu.surajpokhrel.outfit_diary.admin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.csu.surajpokhrel.outfit_diary.utility.ImageListActivity;
import edu.csu.surajpokhrel.outfit_diary.utility.ImageUpload;
import edu.csu.surajpokhrel.outfit_diary.R;

public class BeachAdmin extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private EditText txtImageName;
    private Uri imgUri;
    public static String PATH;

    public static final String FB_STORAGE_PATH = "beach/";
    public static final String FB_DATABASE_PATH = "categories/Beach";
    public static final int REQUEST_CODE = 1234;
    public static final int CAMERE_CODE=4321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dress);
        String s = getIntent().getStringExtra("ACTIVITY_CHECK");
        switch (s){
            case "beachA":
                PATH="categories/Beach";
                break;
            case "formalA":
                PATH="categories/Formal";
                break;
            case "interviewA":
                PATH="categories/Interview";
                break;
            case "pregnancyA":
                PATH="categories/Pregnancy";
                break;
            case "summerA":
                PATH="categories/Summer";
                break;
            case "winterA":
                PATH="categories/Winter";
                break;
            case "celebrationA":
                PATH="categories/Celebration";
                break;
            default: PATH="image";
        }

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(PATH);

        imageView = (ImageView) findViewById(R.id.imageView);
        txtImageName = (EditText) findViewById(R.id.txtImageName);
    }

    public void btnBrowse_Click(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode==CAMERE_CODE && resultCode == RESULT_OK ) {
            imgUri = data.getData();

            Bitmap bm = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bm);


        }

    }
    public void btnTakePic_Click(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERE_CODE);

    }


    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click(View v) {
        if (imgUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading image");
            dialog.show();

            //Get the storage reference
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

            //Add file to reference
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    //Dimiss dialog when success
                    dialog.dismiss();
                    //Display success toast msg
                    String uploadId = mDatabaseRef.push().getKey();
                    Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                    ImageUpload imageUpload = new ImageUpload(uploadId,txtImageName.getText().toString(), taskSnapshot.getDownloadUrl().toString());

                    //Save image info in to firebase database
                    //String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(imageUpload);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Dimiss dialog when error
                            dialog.dismiss();
                            //Display err toast msg
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnShowListImage_Click(View v) {
        Intent i = new Intent(BeachAdmin.this, ImageListActivity.class);
        startActivity(i);
    }
}
