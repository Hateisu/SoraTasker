package com.cephid.soratasker.CustomViewToLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cephid.soratasker.R;
import com.cephid.soratasker.classes.Tasker;

import java.util.List;

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    //Adapting My TaskViewHolder to Tasker objects
    private Context context;
    public List<Tasker> tasks;
    public TaskViewAdapter(Context context, List<Tasker> tasks) {
        //Pushing data from MainClass
        this.tasks = tasks;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Setting view
        return new TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.show_card_of_task,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.header.setText(tasks.get(position).title);
        holder.textViewrow1.setText(tasks.get(position).description);
        holder.checkBox.setChecked(tasks.get(position).isDone);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
