package com.forumias.beta.ui.deta.forumias.comment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.R;
import com.forumias.beta.myinterface.pollClickEvent;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.PollModel;

import java.util.Arrays;
import java.util.List;

import retrofit2.Callback;

public  class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {
    Context context;
    List<PollModel.PollInfo> pollInfo;
    int totalVote;
    boolean status = false;
    pollClickEvent pollClickEvent;

    public PollAdapter(Context context, List<PollModel.PollInfo> pollInfo , int totalVote , pollClickEvent clickEvent) {
        this.context = context;
        this.pollInfo = pollInfo;
        this.totalVote = totalVote;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_list_items , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvPollText.setText(pollInfo.get(position).name);


           /* if(pollInfo.get(position).isSelectAt){
                holder.oneSeekBar.setProgress(50);
            }*/

           /* holder.tvPollText.setOnClickListener(view -> {
                List<String> items = Arrays.asList(pollInfo.get(position).voted_by.split("\\s*,\\s*"));
                int totalPerOne  = (items.size()*100)/totalVote;
                Log.e("total poll coun =332=> ",totalPerOne+"");
                holder.oneSeekBar.setProgress(totalPerOne);
                notifyDataSetChanged();
                status = true;
               // updateData(status , pollInfo.get(position).voted_by,  holder.oneSeekBar , pollInfo.size());
            });*/

          /*  if(status) {
                holder.oneSeekBar.setVisibility(View.VISIBLE);
                List<String> items = Arrays.asList(pollInfo.get(position).voted_by.split("\\s*,\\s*"));
                int totalPerOne = (items.size() * 100) / totalVote;
                Log.e("total poll coun =332=> ", totalPerOne + "");
                holder.oneSeekBar.setProgress(totalPerOne);
            }else{
                holder.oneSeekBar.setVisibility(View.GONE);
            }*/

        /*    holder.pollSectionOne.setOnClickListener(view -> {
                 List<String> items = Arrays.asList(pollInfo.get(position).voted_by.split("\\s*,\\s*"));
                 int totalPerOne  = (items.size()*100)/totalVote;
                Log.e("total poll count ==> ",totalPerOne+"");
            });

        holder.oneSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //listener
            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                //add your event here
            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
               // updateControls(progress, false);
             for(int i = 0 ; i< pollInfo.size();i++){
                 List<String> items = Arrays.asList(pollInfo.get(position).voted_by.split("\\s*,\\s*"));
                 int totalPerOne  = (items.size()*100)/totalVote;
                 Log.e("total poll count =12=> ",totalPerOne+"");
                 holder.oneSeekBar.setProgress(totalPerOne);
             }
            }
        });*/
    }



    @Override
    public int getItemCount() {
        return pollInfo.size();
    }

    protected  class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvPollText;
        RelativeLayout pollSectionOne;
        AppCompatSeekBar oneSeekBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPollText = itemView.findViewById(R.id.tvPollText);
            pollSectionOne = itemView.findViewById(R.id.pollSectionOne);
            oneSeekBar = itemView.findViewById(R.id.oneSeekBar);
        }
    }
}
