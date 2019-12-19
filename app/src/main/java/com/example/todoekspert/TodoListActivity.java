package com.example.todoekspert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todoekspert.db.TaskContract;
import com.example.todoekspert.db.TaskDbHelper;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    private int REQUEST_CODE;
    private TaskDbHelper mHelper;
    Bundle bundle;
    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        try {
            bundle = getIntent().getExtras();
            todo = (Todo) bundle.get("TODO");

            updateUI();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("EROR");

        }
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mHelper = new TaskDbHelper(this);
        mDatabase = mHelper.getReadableDatabase();
        updateUI();
       // mDatabase.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_new) {
            Intent intent = new Intent(getApplicationContext(), AddTodoActivity.class);
            REQUEST_CODE = 123;
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                this.todo = (Todo) data.getSerializableExtra(AddTodoActivity.TODO);

              addItem();
              updateUI();
                Toast.makeText(getApplicationContext(), "Result OK " + todo.content, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Result canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void addItem() {

        ContentValues cv = new ContentValues();

        cv.put(TaskContract.TaskEntry.COL_TASK_TITLE, this.todo.content);
       // cv.put(String.valueOf(TaskContract.TaskEntry.DONE), this.todo.done);

        mDatabase.insert(TaskContract.TaskEntry.TABLE, null, cv);
        //mAdapter.swapCursor(getAllItems());

       // mDatabase.close();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
       mDatabase = mHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                String.valueOf(TaskContract.TaskEntry.DONE),
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.list_item,
                    R.id.itemContentTextView,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
       // mDatabase.close();
    }
}
