package com.try_project.soratasker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialCalendar;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_main);
    }

    public void show_button(View view) {
        try {
            show_dat(FileManager.readFromFile("example_task.json", this));
            MainActivity.tasks = MainActivity.read_saved_data_static("example_task.json",this);
        } catch (Exception e) {
            show_dat(e.toString());
        }
    }

    public void save_button(View view) {
        String data = "{\n" +
                "  \"tasks\": [\n" +
                "    {\n" +
                "      \"name\": \"Одиночный checkbox\",\n" +
                "      \"description\": \"Повторяется в особые дни, которые вы выбрали.\",\n" +
                "      \"type\": \"checkbox_single_occure\",\n" +
                "      \"background_color\": \"" + "aqua"+ "\",\n" +
                "      \"other\": {\n" +
                "        \"occure_in\": [\n" +
                "          \"01.04.2023\",\n" +
                "          \"25.03.2023\"\n" +
                "        ],\n" +
                "        \"occure_in_checked\": [\n" +
                "          \"\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"made_in\": \"30.01.2023\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Ежедневный checkbox\",\n" +
                "      \"description\": \"Повторяется в каждый божий день от дня начала до дня конца.\",\n" +
                "      \"type\": \"checkbox_everyday\",\n" +
                "      \"background_color\": \"" + "sky"+ "\",\n" +
                "      \"other\": {\n" +
                "        \"occure_in_checked\": [\"11.01.12\",\"12.01.12\",\"13.01.12\"],\n" +
                "        \"expire_in\": \"31.01.2023\"\n" +
                "      },\n" +
                "      \"made_in\": \"30.01.2023\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Задание на неограниченый период checkbox\",\n" +
                "      \"description\": \"Типа квеста в игре\",\n" +
                "      \"type\": \"checkbox_single\",\n" +
                "      \"background_color\": \"" + "purple"+ "\",\n" +
                "      \"other\": {\n" +
                "        \"is_checked\": false\n" +
                "      },\n" +
                "      \"made_in\": \"30.01.2023\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Задание на неограниченый период checkbox hidden\",\n" +
                "      \"description\": \"Типа квеста в игре njg\",\n" +
                "      \"type\": \"checkbox_single\",\n" +
                "      \"background_color\": \"" + "purple"+ "\",\n" +
                "      \"other\": {\n" +
                "        \"is_checked\": true\n" +
                "      },\n" +
                "      \"made_in\": \"30.01.2023\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            FileManager.writeToFile(data, "example_task.json", this);

        } catch (Exception e) {
            show_dat(e.toString());
        }
    }

    public void save_all_data(View view){
        String data = MyTasker.generate_json(MainActivity.tasks);
        try {
            FileManager.writeToFile(data, "example_task.json", this);
        } catch (Exception e) {
            show_dat(e.toString());
        }
    }
    public void show_dat(String data) {
        TextView textView = findViewById(R.id.json_file_read);
        textView.setText(data);
    }

    public void return_back(View view) {
        finish();
    }
}
