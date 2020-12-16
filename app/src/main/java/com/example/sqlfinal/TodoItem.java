package com.example.sqlfinal;

public class TodoItem {
    private int id;
    private String text;
    private String date;
    private int done;

    public TodoItem(int id, String text, String date, int done) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.done = done;
    }

    public String getText() { return text; }
    public String getDate() { return date; }
    public int isDone() { return done; }
    public int getId() { return id; }
}
