package com.example.sqlfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> {

    ArrayList<TodoItem> todoItems;
    FragmentManager fragMan;
    TodoDBHelper dbHelper;
    TextView emptyTextView;

    public TodoListAdapter(ArrayList<TodoItem> todoItems,
                           FragmentManager fragMan,
                           TextView emptyTextView,
                           TodoDBHelper dbHelper) {
        this.todoItems = todoItems;
        this.fragMan = fragMan;
        this.dbHelper = dbHelper;
        this.emptyTextView = emptyTextView;
    }


    @NonNull
    @Override
    public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_layout, null);
        return new TodoListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
        TodoItem item = todoItems.get(position);
        boolean a;
        if(item.isDone() == 0){ a = false; } else { a = true;} //I thought i was gonna be smart with the "you can use ints instead" but it turns out I'm an idiot
        holder.doneCheckBox.setChecked(a);
        holder.itemTextView.setText(item.getText());
        holder.dateTextView.setText(item.getDate());

        holder.doneCheckBox.setOnCheckedChangeListener((v, checked) -> {
            dbHelper.setDone(todoItems.get(holder.getAdapterPosition()).getId(), checked);
        });
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    public class TodoListViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;
        CheckBox doneCheckBox;
        TextView dateTextView;

        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.item_text);
            doneCheckBox = itemView.findViewById(R.id.item_checkbox);
            dateTextView = itemView.findViewById(R.id.item_date);
        }
    }
}
