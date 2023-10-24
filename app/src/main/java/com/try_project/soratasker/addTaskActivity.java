package com.try_project.soratasker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

public class addTaskActivity extends AppCompatActivity {
    boolean is_redacting = false;
    TextView setting;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String background_color;
    int background_color_checkbox_id;
    String type_of;

    MyTasker task;
    MaterialCalendarView materialCalendarView;
    LinearLayout new_layout;
    CheckBox[] checkBoxes = new CheckBox[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setting = findViewById(R.id.type_input_text);
        new_layout = findViewById(R.id.additional);


        TextInputEditText desc = findViewById(R.id.textinputdescriptionaddcard);
        EditText titl = findViewById(R.id.titlefieldaddcard);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                is_redacting = false;

            } else {
                is_redacting = extras.getBoolean("is_redacting");
                for (int i = 0; i < MainActivity.tasks.length; i++) {
                    if (MainActivity.tasks[i].task_id == extras.getInt("task_id")) {
                        task = MainActivity.tasks[i];
                        break;
                    }
                }
                titl.setText(task.name);
                desc.setText(task.description);

            }
        } else {
            is_redacting = (boolean) savedInstanceState.getSerializable("is_redacting");
            for (int i = 0; i < MainActivity.tasks.length; i++) {
                if (MainActivity.tasks[i].task_id == (int) savedInstanceState.getSerializable("task_id")) {
                    task = MainActivity.tasks[i];
                    break;
                }
            }
            titl.setText((String) savedInstanceState.getSerializable("title"));
            desc.setText((String) savedInstanceState.getSerializable("description"));

        }

        if (is_redacting) {
            Spinner spiner = findViewById(R.id.spinner2);
            type_of = "checkbox_single";
            spiner.setClickable(false);
            spiner.setSelection(0);
            ScrollView view = findViewById(R.id.scroll_checked);
            view.setVisibility(View.VISIBLE);
            ImageButton btn = findViewById(R.id.trash_can_delete);
            btn.setVisibility(View.VISIBLE);
            TextView textView = findViewById(R.id.bottom_showed);
            switch (task.type) {
                case "checkbox_single":
                    textView.setText(task.map.get("is_checked").toString());
                    break;
                case "checkbox_single_occure":
                    String temp = "";
                    String[] arr = MyTasker.get_arr(task.map.get("occure_in_checked"));
                    for (String s : arr) {
                        temp = temp + s;
                    }
                    textView.setText(temp);
                    break;
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!is_redacting) {
            listen_to_spinner();
        }
        add_and_listen_to_color_change();


    }

    public void listen_to_spinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        Context context = this;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (materialCalendarView!=null){
                    new_layout.removeAllViews();
                    materialCalendarView = null;
                }
                String temp = spinner.getItemAtPosition(i).toString();
                switch (temp) {
                    case "CheckBox Single":
                        type_of = "checkbox_single";
                        break;
                    case "CheckBox Everyday":
                        setting.setText(getApplicationContext().getFilesDir().getAbsolutePath().toString()+"\n"
                        +getApplicationContext().getCacheDir().toString());
                        break;
                    case "CheckBox Single Occure":
                        type_of = "checkbox_single_occure";
                        materialCalendarView = (MaterialCalendarView) LayoutInflater.from(context).inflate(R.layout.add_everyday_task_layout, null);
                        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
                        materialCalendarView.state().edit().setMinimumDate(CalendarDay.today()).commit();
                        new_layout.addView(materialCalendarView);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void end_adding(View view) {
        TextInputEditText inp = findViewById(R.id.textinputdescriptionaddcard);
        EditText title_added = findViewById(R.id.titlefieldaddcard);
        if (!is_redacting) {
            MyTasker[] taskes = new MyTasker[MainActivity.tasks.length + 1];
            for (int in = 0; in < MainActivity.tasks.length; in++) {
                taskes[in] = MainActivity.tasks[in];
            }
            switch (type_of) {
                case "checkbox_single":
                    try {
                        MyTasker task_temp = MyTasker.generate_checkbox_single(title_added.getText().toString(), inp.getText().toString(), dtf.format(LocalDateTime.now()).toString());
                        task_temp.background = background_color;
                        task_temp.task_id = taskes.length - 1;
                        taskes[MainActivity.tasks.length] = task_temp;
                        MainActivity.tasks = taskes;
                    } catch (Exception e) {
                        System.out.println(

                        );
                        Toast.makeText(getBaseContext(), e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case "checkbox_single_occure":
                    try {

                        String dates[] = new String[materialCalendarView.getSelectedDates().size()];
                        for (int i = 0; i < materialCalendarView.getSelectedDates().size(); i++) {
                            dates[i] = dtf.format(materialCalendarView.getSelectedDates().get(i).getDate());
                        }


                        MyTasker task_temp = MyTasker.generate_checkbox_single_occure(title_added.getText().toString(), inp.getText().toString(), dtf.format(LocalDateTime.now()).toString(), dates);
                        task_temp.background = background_color;
                        task_temp.task_id = taskes.length - 1;
                        taskes[MainActivity.tasks.length] = task_temp;
                        MainActivity.tasks = taskes;
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), e.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;


            }
        } else {
            for (int i = 0; i < MainActivity.tasks.length; i++) {
                if (task.task_id == MainActivity.tasks[i].task_id) {
                    task.name = title_added.getText().toString();
                    task.background = background_color;
                    task.description = inp.getText().toString();
                    MainActivity.tasks[i] = task;
                    break;
                }
            }
        }
        finish();
    }

    public void add_and_listen_to_color_change() {

        checkBoxes[0] = findViewById(R.id.checkBox_gradient_aqua);
        checkBoxes[1] = findViewById(R.id.checkBox_gradient_blue);
        checkBoxes[2] = findViewById(R.id.checkBox_gradient_gray);
        checkBoxes[3] = findViewById(R.id.checkBox_gradient_green);
        checkBoxes[4] = findViewById(R.id.checkBox_gradient_peach);
        checkBoxes[5] = findViewById(R.id.checkBox_gradient_purple);
        checkBoxes[6] = findViewById(R.id.checkBox_gradient_red);
        checkBoxes[7] = findViewById(R.id.checkBox_gradient_sky);
        checkBoxes[8] = findViewById(R.id.checkBox_gradient_yellow);
        TextView textView = findViewById(R.id.textView19);
        background_color = "red";
        int choosen_type = 0;
        for (int i = 0; i < checkBoxes.length; i++) {
            int to_on_click = i;
            checkBoxes[to_on_click].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBoxes[to_on_click].isChecked()) {
                        background_color = "red";

                        if (to_on_click == 0) {
                            background_color = "aqua";
                            background_color_checkbox_id = 0;
                        }
                        if (to_on_click == 1) {
                            background_color = "blue";
                            background_color_checkbox_id = 1;
                        }
                        if (to_on_click == 2) {
                            background_color = "gray";
                            background_color_checkbox_id = 2;
                        }
                        if (to_on_click == 3) {
                            background_color = "green";
                            background_color_checkbox_id = 3;
                        }
                        if (to_on_click == 4) {
                            background_color = "peach";
                            background_color_checkbox_id = 4;
                        }
                        if (to_on_click == 5) {
                            background_color = "purple";
                            background_color_checkbox_id = 5;
                        }
                        if (to_on_click == 6) {
                            background_color = "red";
                            background_color_checkbox_id = 6;
                        }
                        if (to_on_click == 7) {
                            background_color = "sky";
                            background_color_checkbox_id = 7;
                        }
                        if (to_on_click == 8) {
                            background_color = "yellow";
                            background_color_checkbox_id = 8;
                        }
                        textView.setText("Choose background: " + background_color);


                        for (int z = 0; z < checkBoxes.length; z++) {
                            if (z != to_on_click) {
                                checkBoxes[z].setChecked(false);
                            }
                        }
                    }
                }
            });
        }
    }

    public void exit_return(View view) {
        finish();
    }

    public void delete_task(View view) {
        if (is_redacting) {
            MainActivity.tasks = MyTasker.remove_at_pos(MainActivity.tasks, task.task_id);
            finish();
        }
    }
}