package umn.ac.id.jamesyoel_00000028895_if633_fl_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

public class ProfilActivity extends AppCompatActivity {
    String parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().setTitle("Profile Saya");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent parentInt = getIntent();
        parent = parentInt.getStringExtra("Parent");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(parent.equals("main")){
            Intent myIntent = new Intent(ProfilActivity.this, MainActivity.class);
            startActivity(myIntent);
        }
        else if(parent.equals("listLagu")){
            Intent myIntent = new Intent(ProfilActivity.this, ListLaguMenuActivity.class);
            myIntent.putExtra("Result", "profil");
            startActivity(myIntent);
        }
        return true;
    }
}