package umn.ac.id.jamesyoel_00000028895_if633_fl_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Halaman Login");
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setEnabled(false);
        etPassword.addTextChangedListener(watcher);
        etUsername.addTextChangedListener(watcher);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.getText().toString().trim().equals("uasmobile") && etPassword.getText().toString().trim().equals("uasmobilegenap")){
                    Intent intentListLagu = new Intent(LoginActivity.this, ListLaguMenuActivity.class);
                    intentListLagu.putExtra("Result", "login");
                    startActivity(intentListLagu);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,"Username atau Password tidak sesuai. Mohon coba lagi !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = etUsername.getText().toString().trim();
            String passwordInput = etPassword.getText().toString().trim();
            btnLogin.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}