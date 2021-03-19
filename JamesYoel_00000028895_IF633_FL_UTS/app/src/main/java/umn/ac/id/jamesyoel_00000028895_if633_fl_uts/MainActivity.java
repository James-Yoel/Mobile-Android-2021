package umn.ac.id.jamesyoel_00000028895_if633_fl_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnProfil, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        btnProfil =findViewById(R.id.btnProfil);
        btnLogin = findViewById(R.id.btnLogin);

        btnProfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intProfile = new Intent(MainActivity.this, ProfilActivity.class);
                intProfile.putExtra("Parent", "main");
                startActivity(intProfile);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intLogin);
            }
        });
    }
}