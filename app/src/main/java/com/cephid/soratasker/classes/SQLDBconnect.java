package com.cephid.soratasker.classes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class SQLDBconnect {
    public SQLiteDatabase db;

    //Initialize db file as global variable for ability to call from multiple classes
    public SQLDBconnect(String database_name, Context context) {
        //Making db
        /*
        DB connector opened in MainActivity

        */
        this.db = context.openOrCreateDatabase(database_name, MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS main (id INTEGER PRIMARY KEY AUTOINCREMENT ,title Text, description Text, isDone Boolean, madeDate Text, deadlineDate Text)");


    }

    //================================================ADD FUNCTIONS==================================================

    public void addTask(Tasker task) {
        if (task.id != -1) {
            db.execSQL("INSERT INTO main (id,title,description,isDone,madeDate,deadlineDate) VALUES (" +
                    task.id + ","  +
                    "\"" + task.title + "\"" + "," +
                    "\"" + task.description + "\"" + "," +
                    task.isDone + "," +
                    "\"" + task.madeDate.toString() + "\"" + "," +
                    "\"" + task.deadlineDate.toString() + "\""
                    + ");");
        }else{
            db.execSQL("INSERT INTO main (title,description,isDone,madeDate,deadlineDate) VALUES (" +
                    "\"" + task.title + "\"" + "," +
                    "\"" + task.description + "\"" + "," +
                    task.isDone + "," +
                    "\"" + task.madeDate.toString() + "\"" + "," +
                    "\"" + task.deadlineDate.toString() + "\""
                    + ");");
        }
    }

    public void addTasks(List<Tasker> tasks) {
        //Avoid multiple INSERT
        for (Tasker task : tasks) {
            addTask(task);
        }
    }

    public void clearDataBeforeReSaving() {
        db.execSQL("delete from main;");
    }

    //================================================GET FUNCTIONS==================================================

    public Tasker getTaskById(int id) {
        /*
           When you taking obj from database there is always unique id of any column.
           So everytime when we taking string from 1 in cursor we are taking id.
           But if we drop database and create again, then we need to parse it to task,
           because there is a chance that id column won`t be first.
         */
        //id is unique so we dont need to lookup here for a special id just get it from response
        Cursor q = db.rawQuery("SELECT * from main where id=" + id + ";", null);
        q.move(1);
        //Getting row_data
        List<String> row_data = getFullRow(q);
        List<String> column_name = new ArrayList<>();
        Collections.addAll(column_name, q.getColumnNames());
        return new Tasker(
                row_data.get(column_name.indexOf("title")),
                row_data.get(column_name.indexOf("description")),
                LocalDateTime.parse(row_data.get(column_name.indexOf("madeDate"))),
                LocalDateTime.parse(row_data.get(column_name.indexOf("deadlineDate"))),
                row_data.get(column_name.indexOf("isDone")).equals("1")
        );
    }

    public List<Tasker> getAllTasks() {
        Cursor q = db.rawQuery("SELECT * from main;", null);
        return getTaskerListFromCursor(q);
    }

    public List<Tasker> get_ActiveOrDeactiveTasksList(boolean is_active) {
        Cursor q = db.rawQuery("SELECT * from main where isDone=" + (is_active ? "TRUE" : "FALSE") + ";", null);
        return getTaskerListFromCursor(q);
    }

    public List<Tasker> getTasksBy(List<String> filters) {
        Cursor q;
        String condition = "";
        for (String filter : filters) {
            switch (filter) {
                case "active":
                    condition = condition + ", isDone = TRUE";
                    break;
                case "deactive":
                    condition = condition + ", isDone = FALSE";
                    break;
                /*case "type":
                    break;*/
            }
        }
        condition = condition.replaceFirst(",", "");
        q = db.rawQuery("SELECT * FROM main WHERE " + condition + ";", null);

        return getTaskerListFromCursor(q);
    }

    private List<String> getFullRow(Cursor q) {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < q.getColumnCount(); i++) {
            ret.add(q.getString(i));
        }
        return ret;
    }

    private List<Tasker> getTaskerListFromCursor(Cursor q) {
        q.moveToPosition(-1);
        List<Tasker> tasks = new ArrayList<>();
        int rows = q.getCount();
        List<String> column_name = new ArrayList<>();
        Collections.addAll(column_name, q.getColumnNames());
        for (int i = 0; i < rows; i++) {
            q.move(1);
            List<String> row_data = getFullRow(q);
            tasks.add(new Tasker(
                    row_data.get(column_name.indexOf("title")),
                    row_data.get(column_name.indexOf("description")),
                    LocalDateTime.parse(row_data.get(column_name.indexOf("madeDate"))),
                    LocalDateTime.parse(row_data.get(column_name.indexOf("deadlineDate"))),
                    row_data.get(column_name.indexOf("isDone")).equals("1"),
                    Integer.parseInt(row_data.get(column_name.indexOf("id")))
            ));
        }
        return tasks;
    }


    //================================================DEBUG FUNCTIONS==================================================


    public static String debuq_cursor(Cursor q) {
        String ret = "";
        int columns = q.getColumnCount();
        int rows = q.getCount();
        for (int j = 0; j < columns; j++) {
            ret = ret + q.getColumnName(j) + " == ";
        }
        ret = ret + "\n";
        for (int i = 0; i < rows; i++) {
            q.moveToPosition(i);
            for (int j = 0; j < columns; j++) {
                ret = ret + q.getString(j) + " == ";
            }
            ret = ret + "\n";
        }
        return ret;
    }

}
