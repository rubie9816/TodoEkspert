package com.example.todoekspert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddTodoActivity extends AppCompatActivity {

    private EditText contentEditText;
    private CheckBox doneCheckBox;
    private Button saveButton;
    public static final String CONTENT = "content";
    public static final String DONE = "done";
    public static final String TODO = "todo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        contentEditText = (EditText)findViewById(R.id.content_et);
        doneCheckBox = (CheckBox)findViewById(R.id.donte_cb);
        saveButton = (Button) findViewById(R.id.save_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = contentEditText.getText().toString();
                Todo todo = new Todo();
                if (doneCheckBox.isChecked())
                {
                    todo.done = 1;
                }
                else
                {
                    todo.done = 0;
                }
                //boolean isDone = doneCheckBox.isChecked();
                todo.content = content;

                Intent intent = new Intent();
                intent.putExtra(TODO,todo);
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }
}
