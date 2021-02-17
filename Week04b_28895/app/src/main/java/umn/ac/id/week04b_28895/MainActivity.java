package umn.ac.id.week04b_28895;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText nama;
    Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nama = findViewById(R.id.nama);
        btn1 = findViewById(R.id.main_button_change_1);
        btn2 = findViewById(R.id.main_button_change_2);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intentDua = new Intent(MainActivity.this, SecondActivity.class);
                String namaIsi = nama.getText().toString();
                startActivityForResult(intentDua, 1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intentTiga = new Intent(MainActivity.this, ThirdActivity.class);
                String namaIsi2 = nama.getText().toString();
                startActivityForResult(intentTiga, 1);
            }
        });
    }
}