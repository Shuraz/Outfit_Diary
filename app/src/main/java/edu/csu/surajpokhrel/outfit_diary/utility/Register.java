package edu.csu.surajpokhrel.outfit_diary.utility;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.csu.surajpokhrel.outfit_diary.R;
import edu.csu.surajpokhrel.outfit_diary.UserInfo;

public class Register extends AppCompatActivity {
    private EditText name,email,password;
    private Button btRegister;
    private TextView backToLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef;

    public static final String FB_DB_PATH="register_user";

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference(FB_DB_PATH);

        name=(EditText)findViewById(R.id.userName);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        btRegister=(Button)findViewById(R.id.bregister);
        backToLogin=(TextView)findViewById(R.id.textViewLogin);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerData();
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void registerData(){
        final String Name= name.getText().toString().trim();
        final String Email=email.getText().toString().trim();
        String Password=password.getText().toString().trim();
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "Enter Your Email Address",Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Enter Your Password",Toast.LENGTH_SHORT).show();
            return;

        }


        progressDialog.setMessage("Please wait! Registration...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user =firebaseAuth.getCurrentUser();
                     String userID = user.getUid();
                    UserInfo userInfo = new UserInfo(Name,Email);
                    mDatabaseRef.child(userID).setValue(userInfo);

                    Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    if (firebaseAuth.getCurrentUser()!=null){
                        finish();
                        startActivity(new Intent(Register.this, MainActivity.class));
                    }
                }
                else{
                    Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




}
