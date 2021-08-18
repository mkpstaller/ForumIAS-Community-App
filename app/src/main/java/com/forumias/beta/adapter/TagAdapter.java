package com.forumias.beta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.forumias.beta.R;
import com.google.android.material.chip.Chip;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private List<String> tagList;
    private Context context;

    public TagAdapter(List<String> tagList, Context context) {
        this.tagList = tagList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chipTag.setText(tagList.get(position));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private Chip chipTag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chipTag = itemView.findViewById(R.id.chipTag);
        }
    }
}
