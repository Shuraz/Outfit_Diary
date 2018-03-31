package edu.csu.surajpokhrel.outfit_diary.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.csu.surajpokhrel.outfit_diary.R;

public class AdminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    public void Beach_Click(View view){
        Intent i = new Intent(AdminMenu.this, BeachAdmin.class);
        i.putExtra("ACTIVITY_CHECK", "beachA");
        startActivity(i);
        startActivity(i);
    }
    public void Interview_Click(View view){
        Intent i = new Intent(AdminMenu.this, BeachAdmin.class);
        i.putExtra("ACTIVITY_CHECK", "interviewA");
        startActivity(i);
        startActivity(i);
    }
    public void Celebration_Click(View view){
        Intent i = new Intent(AdminMenu.this, BeachAdmin.class);
        i.putExtra("ACTIVITY_CHECK", "celebrationA");
        startActivity(i);
        startActivity(i);
    }
    public void Pregnancy_Click(View view){
        Intent i = new Intent(AdminMenu.this, BeachAdmin.class);
        i.putExtra("ACTIVITY_CHECK", "pregnancyA");
        startActivity(i);
        startActivity(i);
    }
    public void Summer_Click(View view){
        Intent i = new Intent(AdminMenu.this, SummerAdmin.class);
        i.putExtra("ACTIVITY_CHECK", "summerA");
        startActivity(i);
        startActivity(i);
    }
    public void Winter_Click(View view){
        Intent i = new Intent(AdminMenu.this, BeachAdmin.class);
        i.putExtra("ACTIVITY_CHECK", "winterA");
        startActivity(i);
        startActivity(i);
    }
    public void Formal_Click(View view){
        Intent i = new Intent(AdminMenu.this, BeachAdmin.class);
        i.putExtra("ACTIVITY_CHECK", "formalA");
        startActivity(i);
        startActivity(i);
    }
}
