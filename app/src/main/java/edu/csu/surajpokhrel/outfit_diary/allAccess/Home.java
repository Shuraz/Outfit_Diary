package edu.csu.surajpokhrel.outfit_diary.allAccess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import edu.csu.surajpokhrel.outfit_diary.utility.Login;
import edu.csu.surajpokhrel.outfit_diary.utility.MainActivity;
import edu.csu.surajpokhrel.outfit_diary.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        //return super.onCreateOptionsMenu(R.menu.menu_main);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i= new Intent(Home.this, Login.class);
                startActivity(i);
                return true;
            case R.id.item2:
               Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                //Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(Home.this, AboutActivity.class);
                startActivity(i2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void btnLogin_Click(View view){
        Intent i= new Intent(Home.this, MainActivity.class);
        startActivity(i);
    }
    public void btnSearch_Click(View view){
        /*Intent i= new Intent(Home.this, ImageListActivity.class);
        startActivity(i);*/
    }

    public void btnInterview_Click(View view){
        Intent i= new Intent(Home.this, ImageListHome.class);
        i.putExtra("ACTIVITY_CHECK", "interview1");
        startActivity(i);
    }
    public void btnParty_Click(View view){
        Intent i= new Intent(Home.this, ImageListHome.class);
        i.putExtra("ACTIVITY_CHECK", "party1");
        startActivity(i);
    }
    public void btnMaternity_Click(View view){
        Intent i= new Intent(Home.this, ImageListHome.class);
        i.putExtra("ACTIVITY_CHECK", "celebration1");
        startActivity(i);
    }
    public void btnBeach_Click(View view){
        Intent i= new Intent(Home.this, ImageListHome.class);
        i.putExtra("ACTIVITY_CHECK", "beach1");
        startActivity(i);
    }
    public void btnSummer_Click(View view){
        Intent i= new Intent(Home.this, ImageListHome.class);
        i.putExtra("ACTIVITY_CHECK", "summer1");
        startActivity(i);
    }
    public void btnWinter_Click(View view){
        Intent i= new Intent(Home.this, ImageListHome.class);
        i.putExtra("ACTIVITY_CHECK", "winter1");
        startActivity(i);
    }
    public void btnFormal_Click(View view){
        Intent i= new Intent(Home.this, ImageListHome.class);
        i.putExtra("ACTIVITY_CHECK", "formal1");
        startActivity(i);
    }
    public void btnTours_Click(View view){
       // Intent i= new Intent(Home.this, MapsVisitActivity.class);
        Intent i= new Intent(Home.this,LocationActivity.class);

        startActivity(i);
    }
}
