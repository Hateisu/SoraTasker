package com.cephid.soratasker.CustomViewToLayout;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cephid.soratasker.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    //I need to write every object here that will be shown on the layout   These objects are in show_card_of_task.xml
    TextView textViewrow1, header, textViewrow2;
    LinearLayout linearLayout;
    CheckBox checkBox;
    ImageButton button;
    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.card_background_color);
        header = itemView.findViewById(R.id.HeaderTopCard);
        textViewrow1 = itemView.findViewById(R.id.HeaderFirstRowCard);
        textViewrow2 = itemView.findViewById(R.id.HeaderSecondRowCard);
        checkBox = itemView.findViewById(R.id.checkBox_raw);
        button = itemView.findViewById(R.id.imageButton_raw);
    }
}
