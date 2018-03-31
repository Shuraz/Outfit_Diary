package edu.csu.surajpokhrel.outfit_diary.utility;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.csu.surajpokhrel.outfit_diary.R;

public class AddDress extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private EditText txtImageName;
    private Uri imgUri;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "Something";
    private static String userID;


    public static final String FB_STORE_PATH = "image1/";
    public static final String FB_DATABASE_PATH = "register_user";
    public static final int REQUEST_CODE = 1234;
    public static final int CAMERE_CODE=4321;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "HelloCamera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dress);
        firebaseAuth=FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);

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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERE_CODE);
       // }
       // captureImage();
    }

  /*  private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imgUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERE_CODE);
    }*/
  /*  public Uri getOutputMediaFileUri(int type) {
        try{
            imgUri= Uri.fromFile(getOutputMediaFile(type));

        }
        catch (Exception ex){
            ex.printStackTrace();

        }
        return imgUri;
    }
*/

  /*  private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        *//*if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }*/

        // Create a media file name
  /*      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }*/
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click(View v) {
        if (imgUri != null && !TextUtils.isEmpty(txtImageName.getText().toString().trim())) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading image");
            dialog.show();

            //Get the storage reference
            StorageReference ref = mStorageRef.child(FB_STORE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

            //Add file to reference
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Dimiss dialog when success
                    dialog.dismiss();
                    //Display success toast msg
                    //Save image info in to firebase database
                    FirebaseUser user =firebaseAuth.getCurrentUser();
                    userID = user.getUid();
                    //Log.d(TAG,"show message"+ userID);
                    DatabaseReference dRef= mDatabaseRef.child(userID).child("image");
                    String uploadId = mDatabaseRef.push().getKey();
                    ImageUpload imageUpload = new ImageUpload(uploadId,txtImageName.getText().toString(), taskSnapshot.getDownloadUrl().toString());
                    dRef.child(uploadId).setValue(imageUpload);
                    txtImageName.setText("");
                    imgUri=null;
                    imageView.setImageResource(android.R.color.transparent);
                    Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();



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
            Toast.makeText(getApplicationContext(), "Image or Image Name missing", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnShowListImage_Click(View v) {
        Intent i = new Intent(AddDress.this, MyWardrobe.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
