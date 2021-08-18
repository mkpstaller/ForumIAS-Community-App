package com.forumias.beta.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.myinterface.TagInterface;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.ReadingModel;// santanu.kar@hdfcbank.com
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.google.android.material.checkbox.MaterialCheckBox;
// 20-04-may
import java.util.Arrays;
import java.util.List;

public class ReadingListAdapter extends RecyclerView.Adapter<ReadingListAdapter.ViewHolder> {
    private List<ReadingModel.ReadingList> readingLists;
    private Context context;
    private boolean starStatus=true;
    private boolean selected , readStatus;
    private TagInterface tagInterface;
    private int darkModeStatus, loginUserID;
    public ReadingListAdapter(List<ReadingModel.ReadingList> readingLists, Context context, boolean selected,
                              boolean readStatus ,TagInterface tagInterface) {
        this.readingLists = readingLists;
        this.context = context;
        this.selected = selected;
        this.readStatus = readStatus;
        this.tagInterface = tagInterface;
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        loginUserID = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(darkModeStatus !=0){
            holder.llReadingListSection.setBackgroundResource(R.drawable.dark_post_border);
            holder.tvTitile.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.view.setBackgroundResource(R.color.border_color);

        }else{
            holder.llReadingListSection.setBackgroundResource(R.color.white);
            holder.tvTitile.setTextColor(ContextCompat.getColor(context , R.color.black_light));
            holder.view.setBackgroundResource(R.color.low_gray);
        }


        holder.tvTitile.setText(readingLists.get(position).getTitle());
      //  holder.ivStar.setBackgroundResource(R.drawable.ic_star);
      //  if(selected) {
            /*if (readStatus) {
                holder.llReadingListSection.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            } else {
                holder.llReadingListSection.setBackgroundColor(ContextCompat.getColor(context, R.color.low_blue)); // todo
            }*/
        //}else{
            //Toast.makeText(context,"Check List After then Read and Unread.",Toast.LENGTH_LONG).show();
      //  }

        if(readingLists.get(position).getMarkRead() == 1){
           // holder.llReadingListSection.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
          //  holder.tvMarkRead.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_read, 0, 0, 0);
        }else{
           // holder.llReadingListSection.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        if(readingLists.get(position).getStarred() == 1){
            holder.ivStar.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_yellow_star));
        }else{
            holder.ivStar.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star_black));

        }

        holder.chSelected.setChecked(selected);
        if(!new Utility().isNullOrEmpty(readingLists.get(position).getTag())) {
            String tag = readingLists.get(position).getTag();
            String[] myTag = tag.split(",");
            List<String> tagList = Arrays.asList(myTag);
            TagAdapter tagAdapter = new TagAdapter(tagList,context);
            holder.recyclerView.setAdapter(tagAdapter);
        }

        holder.tvTitile.setOnClickListener(view -> {
            Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
            MyPreferenceData myPref = new MyPreferenceData(context);
            myPref.saveData(BaseUrl.PREF_SLUG , readingLists.get(position).getSlug());
            context.startActivity(intent);
        });

        holder.ivStar.setOnClickListener(view -> {
            if(starStatus){
                holder.ivStar.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_yellow_star));
                starStatus = false;
            }else {
                holder.ivStar.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_star_black));

                starStatus = true;
            }
        });

        holder.tvAddTag.setOnClickListener(view -> {
            //tagInterface.addMyTag(position);
            Toast.makeText(context , "Under Development..!" , Toast.LENGTH_LONG).show();
        });
        holder.llReadingListSection.setOnClickListener(view -> {
            Toast.makeText(context , "Under Development..!" , Toast.LENGTH_LONG).show();

        });
        holder.llAddTagSection.setOnClickListener(view -> {
            Toast.makeText(context , "Under Development..!" , Toast.LENGTH_LONG).show();

        });

        holder.llDelete.setOnClickListener(view -> {
            tagInterface.deleteReading(loginUserID , readingLists.get(position).getId() , 5 ,
                    position , readingLists);
        });
    }

    @Override
    public int getItemCount() {
        return readingLists.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvTitile ,tvDelete ,tvMarkRead , tvAddTag;
        private AppCompatImageView ivStar;
        private MaterialCheckBox chSelected;
        private RecyclerView recyclerView;
        private View view;
        private LinearLayoutCompat llReadingListSection , llDelete , llMarkReadSection , llAddTagSection;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitile = itemView.findViewById(R.id.tvTitle);
            ivStar = itemView.findViewById(R.id.ivStar);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvMarkRead = itemView.findViewById(R.id.tvMarkRead);
            tvAddTag = itemView.findViewById(R.id.tvAddTag);
            chSelected = itemView.findViewById(R.id.chSelected);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            llReadingListSection = itemView.findViewById(R.id.llReadingListSection);
            view = itemView.findViewById(R.id.view);
            llDelete = itemView.findViewById(R.id.llDelete);
            llMarkReadSection = itemView.findViewById(R.id.llMarkReadSection);
            llAddTagSection = itemView.findViewById(R.id.llAddTagSection);
        }
    }
}
