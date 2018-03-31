package edu.csu.surajpokhrel.outfit_diary.utility;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

public class Login extends AppCompatActivity {
    private EditText email,password;
    private Button btLogin,btFBLogin;
    private Button btCategories;
    private TextView txtRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        progressDialog=new ProgressDialog(this);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        btLogin=(Button)findViewById(R.id.blogin);
        btFBLogin=(Button)findViewById(R.id.bFBlogin);
        txtRegister=(TextView)findViewById(R.id.textViewRegister);
        btCategories=(Button)findViewById(R.id.btnListCat);

        /*btCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(Login.this,SecondActivity.class));
            }
        });*/

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
            });


        btFBLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            facebookLogin();
        }

 });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });



    }

    private void facebookLogin() {
    }

    public void  userLogin() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Enter your email address, Please", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Enter your password, Please", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please wait..Login... ");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                     Toast.makeText(Login.this,"Login Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(Login.this,"Wrong Email or Password",Toast.LENGTH_SHORT).show();
                }




            }
        });
    }
}
