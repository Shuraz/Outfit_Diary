package edu.csu.surajpokhrel.outfit_diary.utility;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.csu.surajpokhrel.outfit_diary.admin.AdminMenu;
import edu.csu.surajpokhrel.outfit_diary.allAccess.AboutActivity;
import edu.csu.surajpokhrel.outfit_diary.allAccess.Home;
import edu.csu.surajpokhrel.outfit_diary.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Something";
        private TextView txtemail,txtName;
        private Button btlogout,btwardrobe,btaddnew, bthome, btabout;
        Register register=new Register();
        private FirebaseAuth firebaseAuth;
        private DatabaseReference mDatabaseRef;
        private String userID;
        //FirebaseDatabase mFBDatabase;
    public static final String FB_DB_PATH="register_user";
        @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            firebaseAuth=FirebaseAuth.getInstance();
            FirebaseUser user =firebaseAuth.getCurrentUser();
            userID = user.getUid();
            mDatabaseRef= FirebaseDatabase.getInstance().getReference(FB_DB_PATH);

            if (firebaseAuth.getCurrentUser()==null){
                finish();
                startActivity(new Intent(MainActivity.this,Login.class));
            }

            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataSnapshot ds=   dataSnapshot.child(userID);
                    String nm=ds.child("name").getValue(String.class);
                    txtName.setText(nm);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            txtemail=(TextView)findViewById(R.id.userEmail);
            txtName=(TextView)findViewById(R.id.userName);
            btlogout=(Button)findViewById(R.id.blogout);
            btwardrobe=(Button)findViewById(R.id.bwardrobe);
            btaddnew=(Button)findViewById(R.id.btnAddNew);
            bthome=(Button)findViewById(R.id.bhome);
            btabout=(Button)findViewById(R.id.babout);

            txtemail.setText(user.getEmail());
           /* if(!user.getEmail().equals("mrsurajpokhrel@gmail.com")){

            (Button)findViewById(R.id.adminAdd).setVisibility(View.GONE);
            }*/

            Toast.makeText(getApplicationContext(), "Please select image", Toast.LENGTH_SHORT).show();


            btlogout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
            });

            btwardrobe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, MyWardrobe.class);
                    startActivity(i);

                }
            });
            btaddnew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,  AddDress.class));
                }
            });

            bthome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, Home.class));

                }
            });

            btabout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                    finish();
                }
            });



        }
        public void btnAdminAccess(View view){
            Intent i = new Intent(MainActivity.this,AdminMenu.class);
            startActivity(i);
        }

}
