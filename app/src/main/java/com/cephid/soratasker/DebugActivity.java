package com.cephid.soratasker;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cephid.soratasker.classes.Tasker;

import java.time.LocalDateTime;
import java.util.List;

public class DebugActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_layout);
    }
    public void fillDB(View view){
        for (int i = 5;i>0;i--){
            MainActivity.db.addTask(new Tasker("Title"+i,"Description"+i, LocalDateTime.now()));
        }
        System.out.println("Done, Checking");

        List<Tasker> tasks = MainActivity.db.getAllTasks();
        for (Tasker task:tasks){
            System.out.println(task.title);
        }
    }
    public void force_delete_old_db(View view){
        
    }
}
