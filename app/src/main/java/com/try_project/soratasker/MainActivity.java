package com.try_project.soratasker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    //=========================================================
    public static MyTasker[] tasks = new MyTasker[0];

    public static boolean debug = true;
    //=========================================================
    public boolean active_ = true;

    public static boolean show_desc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Getting data from file
        tasks = read_saved_data("example_task.json");
        //Adding them to view
        //add_card_to_view(tasks);


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            add_card_to_view(tasks);
            for (int i = 0; i < tasks.length; i++) {
                System.out.println(tasks[i].task_id);
            }
            adapter.notifyDataSetChanged();
            if (debug) System.out.println("Called OnResume");
        } catch (Exception e) {
            if (debug) System.out.println(e.toString());
        }
    }

    public void go_to_settings_button(View view) {
        Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
        // myIntent.putExtra("key", "Extra key that transfered to next activity"); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    public void add_card_real(View view) {
        Intent myIntent = new Intent(MainActivity.this, addTaskActivity.class);
        // myIntent.putExtra("key", "Extra key that transfered to next activity"); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    int c = 0;

    public void add_card(View view) {
 /*       MyTasker[] ts = new MyTasker[tasks.length + 1];
        for (int i = 0; i < tasks.length; i++) {
            ts[i] = tasks[i];
        }
        try {
            MyTasker tass = MyTasker.generate_checkbox_single("ЛОл", "Новый", "11/211/1");
            tass.task_id = tasks.length;
            System.out.println(tass.type);
            ts[tasks.length] = tass;
        } catch (Exception e) {
        }
        System.out.println(ts[tasks.length].type);
        tasks=ts;
        System.out.println(tasks[tasks.length - 1].type);
        add_card_to_view();
        adapter.notifyDataSetChanged();*/
    }

    CardScrollerAdapter adapter;
    RecyclerView scroller_array;

    public void add_card_to_view(MyTasker[] taskes) {
        scroller_array = findViewById(R.id.recycler_view_scroller);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        scroller_array.setLayoutManager(llm);

        int len = 0, counter = 0;
        for (int i = 0; i < taskes.length; i++) {
            //System.out.println(taskes[i].map);
            switch (taskes[i].type) {
                case "checkbox_single":
                    if (((boolean) taskes[i].map.get("is_checked")) != active_) {
                        len = len + 1;
                    }
                    break;
                case "checkbox_single_occure":
                    boolean is_showing = false,is_today = false;
                    System.out.println("Curent_date "+MyTasker.get_current_date());
                    for (String s : MyTasker.get_arr(taskes[i].map.get("occure_in"))) {
                        System.out.print("DAtes to occure in "+s);
                        if (MyTasker.get_current_date().equals(s)) {
                            is_showing = true;
                            is_today = true;
                        }
                    }
                    System.out.println("");
                    if (MyTasker.get_arr(taskes[i].map.get("occure_in_checked")) != null) {
                        for (String s : MyTasker.get_arr(taskes[i].map.get("occure_in_checked"))) {
                            if (MyTasker.get_current_date().equals(s)) {
                                is_showing = false;
                            }
                        }
                    }
                    if (is_showing == active_ & is_today) len = len + 1;
                    System.out.println(is_showing+" "+active_);
                    break;
                default:
                    len = len + 1;
                    break;

            }
        }
        MyTasker[] temp_arr = new MyTasker[len];
        for (int i = 0; i < taskes.length; i++) {
            switch (taskes[i].type) {
                case "checkbox_single":
                    if ((boolean) taskes[i].map.get("is_checked") != active_) {
                        temp_arr[counter] = taskes[i];
                        counter = counter + 1;
                    }
                    break;
                case "checkbox_single_occure":
                    boolean is_showing = false,is_today = false;

                    for (String s : MyTasker.get_arr(taskes[i].map.get("occure_in"))) {
                        if (MyTasker.get_current_date().equals(s)) {
                            is_showing = true;
                            is_today = true;
                        }
                    }
                    if (MyTasker.get_arr(taskes[i].map.get("occure_in_checked")) != null) {
                        for (String s : MyTasker.get_arr(taskes[i].map.get("occure_in_checked"))) {
                            if (MyTasker.get_current_date().equals(s)) {
                                is_showing = false;
                            }
                        }
                    }
                    if (is_showing == active_& is_today) {
                        temp_arr[counter] = taskes[i];
                        counter = counter + 1;
                    }
                    break;
                default:
                    temp_arr[counter] = taskes[i];
                    counter = counter + 1;
                    break;

            }
        }
        adapter = new CardScrollerAdapter(this, temp_arr);
        scroller_array.setAdapter(adapter);
        scroller_array.setAnimation(null);

    }

    public MyTasker[] read_saved_data(String filename) {
        //Making tasks array
        MyTasker[] tasks_array = new MyTasker[0];
        try {
            //Reading data from file like string
            String json_data = FileManager.readFromFile(filename, this);
            //Getting data Using JSON format
            final JSONObject obj = new JSONObject(json_data);
            final JSONArray array = obj.getJSONArray("tasks");
            final int n = array.length();
            tasks_array = new MyTasker[n];
            for (int i = 0; i < n; ++i) {
                final JSONObject task = array.getJSONObject(i);
                //Reading data to array
                tasks_array[i] = new MyTasker(task.getString("name"),
                        task.getString("description"),
                        task.getString("type"),
                        task.get("other").toString(),
                        task.getString("made_in"));

                //This is not in constructor so this sh*t
                tasks_array[i].background = task.getString("background_color");
                tasks_array[i].task_id = i;
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            if (debug)
                Toast.makeText(getBaseContext(), "File cannot be found", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            System.out.println(e.toString());
            if (debug) {
                Toast.makeText(getBaseContext(), "It is not an JSON file", Toast.LENGTH_LONG).show();

            }
        }
        return tasks_array;
    }

    public static MyTasker[] read_saved_data_static(String filename, Context context) {
        //Making tasks array
        MyTasker[] tasks_array = new MyTasker[0];
        try {
            //Reading data from file like string
            String json_data = FileManager.readFromFile(filename, context);
            //Getting data Using JSON format
            final JSONObject obj = new JSONObject(json_data);
            final JSONArray array = obj.getJSONArray("tasks");
            final int n = array.length();
            tasks_array = new MyTasker[n];
            for (int i = 0; i < n; ++i) {
                final JSONObject task = array.getJSONObject(i);
                //Reading data to array
                tasks_array[i] = new MyTasker(task.getString("name"),
                        task.getString("description"),
                        task.getString("type"),
                        task.get("other").toString(),
                        task.getString("made_in"));

                //This is not in constructor so this sh*t
                tasks_array[i].background = task.getString("background_color");
                tasks_array[i].task_id = i;
            }
        } catch (IOException e) {
            System.out.println(e.toString());

        } catch (JSONException e) {
            System.out.println(e.toString());
        }
        return tasks_array;
    }

    public void active_enable(View view) {
        active_ = true;
        Button button1 = findViewById(R.id.active_button);
        Button button2 = findViewById(R.id.completed_button);
        button1.setTextColor(getColor(R.color.red));
        button2.setTextColor(getColor(R.color.white));
        add_card_to_view(tasks);
    }

    public void active_disable(View view) {
        active_ = false;
        Button button1 = findViewById(R.id.active_button);
        Button button2 = findViewById(R.id.completed_button);
        button1.setTextColor(getColor(R.color.white));
        button2.setTextColor(getColor(R.color.red));
        add_card_to_view(tasks);
    }
}
