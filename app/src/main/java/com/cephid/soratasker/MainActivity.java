package com.cephid.soratasker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cephid.soratasker.CustomViewToLayout.TaskViewAdapter;
import com.cephid.soratasker.CustomViewToLayout.TaskViewHolder;
import com.cephid.soratasker.classes.SQLDBconnect;
import com.cephid.soratasker.classes.Tasker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static SQLDBconnect db;
    public static List<Tasker> tasks = new ArrayList<>();
    public static TaskViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Connecting to database
        db = new SQLDBconnect("main.db",getBaseContext());
        //Putting all tasks to RecyclerView
        allTasksToView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //db.clearDataBeforeReSaving();
        //db.addTasks(tasks);
    }
    //======================================Functions And Logic=========================================

    public static boolean debug_able = true;

    public static void MakePopUpLog(String string, Context context){
        //Just SOUT for debugging and developing
        if (debug_able)Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public void allTasksToView(){
        //Finding Recycler
        RecyclerView recyclerView = findViewById(R.id.itemlist);
        //Getting all data as List to have methods like add and remove to make notifyDataSetChanged functional
        List<String> filters  = new ArrayList<>();
        filters.add("deactive");
        tasks = db.getTasksBy(filters);
        //Connecting Adapter to recycler
        adapter = new TaskViewAdapter(this, tasks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
    }

    public static void ForceUpdateRecycleView(){

        adapter.notifyDataSetChanged();
    }

    public boolean active = true;
    public void activeOrDeactiveTasks(boolean is_active){
        //If we don`t eed same job again
        if (is_active!=active){
            active = is_active;
            List<String> filter = new ArrayList<>();
            filter.add((is_active?"deactive":"active"));
            tasks.clear();
            tasks = db.getTasksBy(filter);
            MakePopUpLog(adapter.getItemCount()+"  Is active "+tasks.size(),this);
            adapter.tasks = tasks;
            adapter.notifyDataSetChanged();
        }
    }


    //======================================On Clickers=================================================
    public void active_enable(View view){
        activeOrDeactiveTasks(true);
    }
    public void active_disable(View view){
        activeOrDeactiveTasks(false);
    }
    int temp = 0;
    public void add_card(View view){
        //db.addTask(new Tasker("Title "+temp,"Description", LocalDateTime.now(),LocalDateTime.now().plusDays(2)));
        temp = temp +1;
        //tasks = db.getAllTasksList();
        tasks.add(new Tasker("Title "+temp,"Description\n\n\n\nDesc Long", LocalDateTime.now()));
        //System.out.println(tasks.get(tasks.size()-1).title+" added in main ");
        adapter.notifyDataSetChanged();
    }
    public void add_card_real(View view){
        Intent intent = new Intent(MainActivity.this,AddCardActivity.class);
        startActivity(intent);
    }
    public void go_to_settings_button(View view){
        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(intent);
    }

}