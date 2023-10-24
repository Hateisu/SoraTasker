package com.try_project.soratasker;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class CardScrollerAdapter extends RecyclerView.Adapter<ScrollerViewHolder> {

    MyTasker[] tasks;
    Context context;
    ScrollerViewHolder holder;

    public CardScrollerAdapter(Context context, MyTasker[] tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public ScrollerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.show_card_of_task, parent, false);
        //view.setBackground(this.context.getDrawable(R.drawable.gradient_color_background_red));
        return new ScrollerViewHolder(view).linkAdapter(this);
    }


    @Override
    public void onBindViewHolder(@NonNull ScrollerViewHolder holder, int position) {

        //Setting text
        holder.header.setText(tasks[position].name);
        holder.textViewrow1.setText(tasks[position].description);
        if (MainActivity.show_desc) {
            String out = "";
            switch (tasks[position].type) {
                case "checkbox_single":
                    out = context.getString(R.string.checkbox_single);
                    break;
                case "checkbox_single_occure":
                    out = context.getString(R.string.checkbox_single_occure);
                    break;
            }
            holder.textViewrow2.setText(out + ",  Made in:" + tasks[position].madein);
        } else {
            holder.textViewrow2.setText("");
        }

        //This is background setter
        int show_background_int_drawable = R.drawable.gradient_color_background_aqua;
        String background = tasks[position].background;
        if (background == null) {
            Random rnd = new Random();
            int[] backs = {R.drawable.gradient_color_background_aqua,
                    R.drawable.gradient_color_background_sky, R.drawable.gradient_color_background_red,
                    R.drawable.gradient_color_background_purple, R.drawable.gradient_color_background_peach,
                    R.drawable.gradient_color_background_yellow, R.drawable.gradient_color_background_blue,
                    R.drawable.gradient_color_background_gray, R.drawable.gradient_color_background_green};

            int back = rnd.nextInt(backs.length);

            show_background_int_drawable = backs[back];
        } else switch (background) {
            case "red":
                show_background_int_drawable = R.drawable.gradient_color_background_red;
                break;
            case "aqua":
                show_background_int_drawable = R.drawable.gradient_color_background_aqua;
                break;
            case "sky":
                show_background_int_drawable = R.drawable.gradient_color_background_sky;
                break;
            case "purple":
                show_background_int_drawable = R.drawable.gradient_color_background_purple;
                break;
            case "peach":
                show_background_int_drawable = R.drawable.gradient_color_background_peach;
                break;
            case "yellow":
                show_background_int_drawable = R.drawable.gradient_color_background_yellow;
                break;
            case "blue":
                show_background_int_drawable = R.drawable.gradient_color_background_blue;
                break;
            case "gray":
                show_background_int_drawable = R.drawable.gradient_color_background_gray;
                break;
            case "green":
                show_background_int_drawable = R.drawable.gradient_color_background_green;
                break;
        }
        holder.linearLayout.setBackground(AppCompatResources.getDrawable(context, show_background_int_drawable));

        //This is checkBox onClick listener
        int pos = position;
        boolean is_checked_temp = false;

        switch (tasks[pos].type) {
            case "checkbox_single":
                is_checked_temp = (boolean) tasks[position].map.get("is_checked");
                break;
            case "checkbox_single_occure":
                String[] arr = MyTasker.get_arr(tasks[pos].map.get("occure_in_checked"));
                if (arr != null) {
                    for (int i = 0; i < arr.length; i++) {
                        System.out.println(arr[i] + " === " + MyTasker.get_current_date());
                        if (arr[i].equals(MyTasker.get_current_date())) is_checked_temp = true;
                    }
                }
                break;
        }

        holder.checkBox.setChecked(is_checked_temp);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tasks[pos].type) {
                    case "checkbox_single":
                        MainActivity.tasks[tasks[pos].task_id].map.put("is_checked", holder.checkBox.isChecked());
                        removeAt(pos, tasks[pos].task_id);
                        break;
                    case "checkbox_single_occure":
                        //Getting array and parsing it
                        String[] array = MyTasker.get_arr(tasks[pos].map.get("occure_in_checked"));
                        array = add_or_remove_element_in_massive(MyTasker.get_current_date(), array);

                        String out = "[";
                        for (int i = 0; i < array.length; i++) {
                            if (!array[i].equals("")) out = out + "\"" + array[i] + "\",";
                        }
                        String temp = "";
                        for (int i = 0; i < out.length() - 1; i++) {
                            temp = temp + out.charAt(i);
                        }
                        System.out.println("==-=-=-=-=-" + MainActivity.tasks[pos].name);
                        MainActivity.tasks[tasks[pos].task_id].map.remove("occure_in_checked");
                        MainActivity.tasks[tasks[pos].task_id].map.put("occure_in_checked", temp + "]");
                        removeAt(pos, tasks[pos].task_id);
                        break;
                }
            }
        });
        //This is ImageButton onClick listener
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, addTaskActivity.class);
                myIntent.putExtra("is_redacting", true); //Optional parameters
                myIntent.putExtra("title", tasks[pos].name);
                myIntent.putExtra("task_id", tasks[pos].task_id);
                myIntent.putExtra("description", tasks[pos].description);
                context.startActivity(myIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return tasks.length;
    }

    private void removeAt(int position, int task_id) {
        tasks = MyTasker.remove_at_pos(tasks, task_id);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasks.length);
    }


    private String[] add_or_remove_element_in_massive(String str, String[] mass) {
        //I need to add or remove an element from massive
        //So if the current date is there i need to remove it else add to end of massive
        //Checking for an element
        String[] temp;
        boolean is_there = false;
        int index = -1;
        int mass_leng = 0;

        if (mass != null) {
            mass_leng = mass.length;
            for (int i = 0; i < mass.length; i++) {
                if (mass[i].equals(str)) {
                    is_there = true;
                    index = i;
                }
            }
        }
        //adding / removing
        if (is_there) {
            //removing
            temp = new String[mass.length - 1];
            for (int i = 0; i < mass.length; i++) {
                if (i < index) {
                    temp[i] = mass[i];
                }
                if (i > index) {
                    temp[i - 1] = mass[i];
                }
            }
        } else {
            temp = new String[mass_leng + 1];
            for (int i = 0; i < mass_leng; i++) {
                if (mass!=null)temp[i] = mass[i];
            }
            temp[mass_leng] = str;
        }
        return temp;
    }
}



