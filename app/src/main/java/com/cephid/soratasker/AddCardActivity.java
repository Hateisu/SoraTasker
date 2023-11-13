package com.cephid.soratasker;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cephid.soratasker.classes.TaskTypes;
import com.cephid.soratasker.classes.Tasker;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;


public class AddCardActivity extends AppCompatActivity {
    public static Spinner mySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mySpinner = findViewById(R.id.spinner2);
        ArrayAdapter list = new ArrayAdapter<TaskTypes>(this, R.layout.spinner_item, TaskTypes.values());
        mySpinner.setAdapter(list);
    }
   /* public void end_adding(View view){
        Tasker task = new Tasker(((EditText)findViewById(R.id.titlefieldaddcard)).getText().toString(),
                ((TextInputEditText)findViewById(R.id.textinputdescriptionaddcard)).getText().toString(),
                LocalDateTime.now(),
                false,

        );
    }*/
}
