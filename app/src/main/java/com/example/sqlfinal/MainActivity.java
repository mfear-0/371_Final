package com.example.sqlfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CreateDialog.TodoItemListener {

    RecyclerView todoList;
    TextView emptyText;
    TodoDBHelper dbHelper;
    TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoList = findViewById(R.id.todo_list);
        todoList.setLayoutManager(new LinearLayoutManager(this));
        emptyText = findViewById(R.id.empty_text);

        dbHelper = new TodoDBHelper(getApplicationContext(),
                TodoDBHelper.DB_NAME,
                null,
                TodoDBHelper.VERSION);

        adapter = new TodoListAdapter(
                dbHelper.getItems(),
                getSupportFragmentManager(),
                emptyText, dbHelper);

        if (adapter.todoItems.size() > 0) {
            emptyText.setVisibility(View.INVISIBLE);
        }
        todoList.setAdapter(adapter);

        CreateDialog cDia = new CreateDialog();

        Button btn = findViewById(R.id.add_item_btn); //no fab covering event entries anymore
        btn.setOnClickListener((v -> cDia.show(getSupportFragmentManager(), "Create new item")));
    }

    @Override
    public void setItem(String text, String date) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Fields empty, try again", Toast.LENGTH_SHORT).show();
        }
        else{
            int id = dbHelper.insertItem(text, date);
            adapter.todoItems.add(new TodoItem(id, text, date, 0));
            adapter.notifyItemInserted(adapter.todoItems.size());
            emptyText.setVisibility(View.INVISIBLE);
        }
    }
}