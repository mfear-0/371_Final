package com.example.sqlfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TodoDBHelper<setDone> extends SQLiteOpenHelper {

    public static final String DB_NAME = "TodoList";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "Todo";
    public static final String ID_COL = "_id";
    public static final String TEXT_COL = "text";
    public static final String DATE_COL = "date";
    public static final String DONE_COL = "done";

    public TodoDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TEXT_COL + " TEXT," +
                DATE_COL + " TEXT," +
                DONE_COL + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void setDone(int id, boolean done) {
        ContentValues contentv = new ContentValues();
        contentv.put(DONE_COL, done);
        getWritableDatabase().update(TABLE_NAME, contentv, "_id=?", new String[]{Integer.toString(id)});
    }

    public int insertItem(String text, String date){
        ContentValues contentv = new ContentValues();
        contentv.put(TEXT_COL, text);
        contentv.put(DATE_COL, date);
        contentv.put(DONE_COL, false);
        return (int) getWritableDatabase().insert(TABLE_NAME, null, contentv);
    }

    public ArrayList<TodoItem> getItems() {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{ID_COL, TEXT_COL, DATE_COL, DONE_COL},
                null,
                null,
                null, null, null);
        ArrayList<TodoItem> items = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                TodoItem item = new TodoItem(
                        cursor.getInt(cursor.getColumnIndex(ID_COL)),
                        cursor.getString(cursor.getColumnIndex(TEXT_COL)),
                        cursor.getString(cursor.getColumnIndex(DATE_COL)),
                        cursor.getInt(cursor.getColumnIndex(DONE_COL))
                );
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }
}
