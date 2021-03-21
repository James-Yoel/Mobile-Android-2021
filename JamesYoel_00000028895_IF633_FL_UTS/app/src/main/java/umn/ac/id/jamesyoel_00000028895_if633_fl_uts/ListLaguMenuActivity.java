package umn.ac.id.jamesyoel_00000028895_if633_fl_uts;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class ListLaguMenuActivity extends AppCompatActivity {
    private RecyclerView listLagu;
    private ArrayList<File> myMusic = new ArrayList<>();
    private ListLaguAdapter laguAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent returnIntent;
        String check;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lagu_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List Lagu");
        returnIntent = getIntent();
        check = returnIntent.getStringExtra("Result");
        if(check.equals("login")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPopup();
                }
            }, 100);
        }
        runtimePermission();
        listLagu = (RecyclerView) findViewById(R.id.listLagu);
        laguAdapter = new ListLaguAdapter(this, myMusic);
        listLagu.setAdapter(laguAdapter);
        listLagu.setLayoutManager(new LinearLayoutManager(this));
    }

    public void runtimePermission(){
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                myMusic = findMusic(Environment.getExternalStorageDirectory());
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                finish();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findMusic(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        if(files != null && files.length > 0){
            for(File singleFile: files){
                Log.i("DIR", "PATH" +file.getPath());
                if(singleFile.isDirectory() && !singleFile.isHidden()){
                    arrayList.addAll(findMusic(singleFile));
                }
                else {
                    if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                        Log.i("DIR", "SUCCESS" +singleFile.getName());
                        arrayList.add(singleFile);
                    }
                }
            }
        }
        else {
            Log.i("FILE", "EMPTY");
        }
        return arrayList;
    }

    public void showPopup(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null, false);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(findViewById(R.id.listLaguLayout), Gravity.CENTER, 0, 0);
        Button btnView = popupView.findViewById(R.id.btnClose);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intProfile = new Intent(ListLaguMenuActivity.this, ProfilActivity.class);
            intProfile.putExtra("Parent", "listLagu");
            startActivity(intProfile);
            return true;
        }
        else if(id == R.id.logout){
            Intent intMain = new Intent(ListLaguMenuActivity.this, MainActivity.class);
            startActivity(intMain);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}