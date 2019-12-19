package com.example.todoekspert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private boolean Boolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.login_btn);
        button.setOnClickListener(this);
        usernameEditText = (EditText) findViewById(R.id.username_et);
        passwordEditText = (EditText) findViewById(R.id.password_et);

    }

    @Override
    public void onClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean isError = false;

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError(getString(R.string.this_field_is_required));
            isError = true;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.this_field_is_required));
            isError = true;
        }
        if (!isError) {
            login(username, password);
        }

    }

    private void login(String username, String password) {

        if (username.equals("test") && password.equals("test")) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Login OK", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
