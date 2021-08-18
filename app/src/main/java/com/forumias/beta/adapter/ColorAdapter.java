package com.forumias.beta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.R;
import com.forumias.beta.pojo.DefaultPojo;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private List<DefaultPojo> colorList;
    private Context context;
    private int selectedPosition = -1;

    public ColorAdapter(List<DefaultPojo> colorList, Context context) {
        this.colorList = colorList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_layout_list , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DefaultPojo checkModel = colorList.get(position);
        holder.checkBox.setBackgroundResource(colorList.get(position).getColorCode());
        if (checkModel.isSelected()) {
            holder.checkBox.setChecked(true);
            holder.ivSelectColorDone.setVisibility(View.VISIBLE);
            selectedPosition = position;

        } else {
            holder.ivSelectColorDone.setVisibility(View.GONE);
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                holder.ivSelectColorDone.setVisibility(View.VISIBLE);
                checkModel.setSelected(true);
                if (selectedPosition >= 0) {
                    colorList.get(selectedPosition).setSelected(false);
                    holder.ivSelectColorDone.setVisibility(View.VISIBLE);
                    notifyItemChanged(selectedPosition);
                }
                selectedPosition = position;
            } else {
                holder.ivSelectColorDone.setVisibility(View.GONE);
                checkModel.setSelected(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCheckBox checkBox;

        private AppCompatImageView ivSelectColorDone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.rlColorCode);
            ivSelectColorDone = itemView.findViewById(R.id.ivSelectColorDone);
        }
    }
}
