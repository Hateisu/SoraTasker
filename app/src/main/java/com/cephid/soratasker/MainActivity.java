package com.cephid.soratasker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.cephid.soratasker.CustomViewToLayout.TaskViewAdapter;
import com.cephid.soratasker.CustomViewToLayout.TaskViewHolder;
import com.cephid.soratasker.classes.SQLDBconnect;
import com.cephid.soratasker.classes.Tasker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public SQLDBconnect db;
    public List<Tasker> tasks = new ArrayList<>();
    TaskViewAdapter adapter;

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
        db.clearDataBeforeReSaving();
        db.addTasks(tasks);
    }
    //======================================Functions And Logic=========================================

    public void allTasksToView(){
        //Finding Recycler
        RecyclerView recyclerView = findViewById(R.id.itemlist);
        //Getting all data as List to have methods like add and remove to make notifyDataSetChanged functional
        tasks = db.getAllTasksList();
        //Connecting Adapter to recycler
        adapter = new TaskViewAdapter(this, tasks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
    }


    public boolean active = true;
    public void activeOrDeactiveTasks(boolean is_active){
        //If we don`t eed same job again
        if (is_active!=active){
            active = is_active;
            allTasksToView();
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
        tasks.add(new Tasker("Title "+temp,"Description\n\n\n\nDesc Long", LocalDateTime.now(),LocalDateTime.now().plusDays(2)));
        //System.out.println(tasks.get(tasks.size()-1).title+" added in main ");
        adapter.notifyDataSetChanged();
    }
    public void add_card_real(View view){
        tasks = new ArrayList<Tasker>();
        tasks.add(new Tasker("Title With long description","Description\n\n\n\nDesc Long", LocalDateTime.now(),LocalDateTime.now().plusDays(2),true));
        tasks.add(new Tasker("Title With long description","Description\n\n\n\nDesc Long", LocalDateTime.now(),LocalDateTime.now().plusDays(2)));
        tasks.add(new Tasker("Sml Ttl","Sml dscrptn", LocalDateTime.now(),LocalDateTime.now().plusDays(2),true));
        tasks.add(new Tasker("Fuck niggers","Madi is nigga", LocalDateTime.now(),LocalDateTime.now().plusDays(2)));
        //System.out.println(tasks.get(tasks.size()-1).title+" added in main ");
        adapter.notifyDataSetChanged();
    }

}