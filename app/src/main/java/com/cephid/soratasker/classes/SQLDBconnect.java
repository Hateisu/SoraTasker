package com.cephid.soratasker.classes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.cephid.soratasker.MainActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public class SQLDBconnect {
    public SQLiteDatabase db;
    private String default_table = "main";

    //Initialize db file as global variable for ability to call from multiple classes
    public SQLDBconnect(String database_name, Context context) {
        //Making db
        /*
        DB connector opened in MainActivity
        */
        this.db = context.openOrCreateDatabase(database_name, MODE_PRIVATE, null);
        //Automatically Generating table by enum SQLFIELDS
        db.execSQL("CREATE TABLE IF NOT EXISTS " + default_table + " " + SQLFields.GetTableGenerator() + ";");
    }

    //================================================ADD FUNCTIONS==================================================

    public void addTask(Tasker task) {
        try {
            db.execSQL("INSERT INTO " + default_table + " " + SQLFields.GetInsertGenerator(task) + ";");
        }catch (NoSuchFieldException nsfe){
            System.out.println("No Such Field");
        }catch (IllegalAccessException iae){
            System.out.println("No permission");
        }catch (SQLiteException e){
            System.out.println("No columns DB old");
            reinstall_table();
            addTask(task);
        }
    }

    public void addTasks(List<Tasker> tasks) {
        //Avoid multiple INSERT
        for (Tasker task : tasks) {
            addTask(task);
        }
    }

    public void clearDataBeforeReSaving() {
        db.execSQL("delete from "+default_table+";");
    }

    //================================================GET FUNCTIONS==================================================
    public Tasker getTaskFromRow_AUTO(List<String> field_names,List<String> field_data){
        //As the data is id accurate then
        Tasker task = new Tasker();
        try {
            for (int i=0;i<field_names.size();i++){
                Object obj;
                switch (TaskerAbstract.class.getDeclaredField(field_names.get(i)).getGenericType().toString()){
                    case "int":
                        obj = Integer.parseInt(field_data.get(i));
                        break;
                    case "boolean":
                        obj = (field_data.get(i).equals("true")?true:false);
                        break;
                    case "class java.time.LocalDateTime":
                        obj = LocalDateTime.parse(field_data.get(i));
                        break;
                    default:
                        obj = field_data.get(i);
                        break;
                }
                TaskerAbstract.class.getDeclaredField(field_names.get(i)).set(task,obj);
            }
            return task;
        }catch (NoSuchFieldException nsfe){
            if (MainActivity.debug_able) System.out.println(nsfe.fillInStackTrace().toString());
            System.out.println("No such field"+(MainActivity.debug_able? " In SQLconnect AUTO":""));
            return null;
        }catch (IllegalAccessException iae){
            System.out.println("No Permission");
            return null;
        }
    }

    public Tasker getTaskById(int id) {
        /*
           When you taking obj from database there is always unique id of any column.
           So everytime when we taking string from 1 in cursor we are taking id.
           But if we drop database and create again, then we need to parse it to task,
           because there is a chance that id column won`t be first.
         */
        //id is unique so we dont need to lookup here for a special id just get it from response
        Cursor q = db.rawQuery("SELECT * from "+default_table+" where id=" + id + ";", null);
        q.move(1);
        //Getting row_data
        List<String> row_data = getFullRow(q);
        q.close();
        List<String> column_name = new ArrayList<>();
        Collections.addAll(column_name, q.getColumnNames());
        return getTaskFromRow_AUTO(column_name,row_data);
    }

    public List<Tasker> getAllTasks() {
        Cursor q = db.rawQuery("SELECT * from "+default_table+";", null);
        return getTaskerListFromCursor(q);
    }

    public List<Tasker> getTasksBy(List<String> filters) {
        Cursor q;
        String condition = "";
        for (String filter : filters) {
            switch (filter) {
                case "active":
                    condition = condition + ", isDone = \"true\"";
                    break;
                case "deactive":
                    condition = condition + ", isDone = \"false\"";
                    break;
                /*case "type":
                    break;*/
            }
        }
        condition = condition.replaceFirst(",", "");
        q = db.rawQuery("SELECT * FROM main WHERE " + condition + ";", null);
        System.out.println("Trying filters +"+condition);
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
            tasks.add(getTaskFromRow_AUTO(column_name,row_data));
        }
        try {

            for (Tasker task:tasks){
                System.out.println(task.title+" in Cursor");
            }
        }catch (NullPointerException e){
            System.out.println("No data in tasks");
            return null;
        }
        return tasks;
    }

    //================================================PUT FUNCTIONS==================================================
    /*public void UpdateFullyTaskById(int id, Tasker task) {
        db.execSQL("UPDATE main SET " +
                "title=" + "\"" + task.title + "\"" + " , " +
                "description=" + "\"" + task.description + "\"" + "," +
                "isDone=" + task.isDone + "," +
                "madeDate=" + "\"" + task.madeDate.toString() + "\"" + "," +
                "deadlineDate=" + "\"" + task.deadlineDate.toString() + "\"" +
                "where id=" + id + ";");
    }*/

    public void UpdateTaskDoneById(int id, boolean isDone) {
        System.out.println("Updating on " + id + " with " + isDone);
        db.execSQL("UPDATE main SET " +
                "isDone=\"" + isDone + "\" " +
                "where id=" + id + ";");
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
    public void reinstall_table(){
        db.execSQL("DROP TABLE "+default_table+";");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + default_table + " " + SQLFields.GetTableGenerator() + ";");
    }
}
