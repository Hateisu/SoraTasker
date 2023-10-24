package com.try_project.soratasker;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ScrollerViewHolder extends RecyclerView.ViewHolder {
    //Holder of ALL card atributes
    TextView textViewrow1, header, textViewrow2;
    LinearLayout linearLayout;
    CheckBox checkBox;
    ImageButton button;

    private CardScrollerAdapter adapter;

    public ScrollerViewHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.card_background_color);
        header = itemView.findViewById(R.id.HeaderTopCard);
        textViewrow1 = itemView.findViewById(R.id.HeaderFirstRowCard);
        textViewrow2 = itemView.findViewById(R.id.HeaderSecondRowCard);
        checkBox = itemView.findViewById(R.id.checkBox_raw);
        button = itemView.findViewById(R.id.imageButton_raw);
    }

    public ScrollerViewHolder linkAdapter(CardScrollerAdapter adapter) {
        this.adapter = adapter;
        return this;
    }
}