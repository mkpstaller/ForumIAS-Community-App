package com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentPaging;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentDetailsActivity;
import com.forumias.beta.ui.deta.forumias.save_comment.SaveDeleteCommentInterface;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentModel;

import java.util.List;


public class SaveCommentAdapter extends RecyclerView.Adapter<SaveCommentAdapter.ViewHolder> {
    private List<SaveCommentModel.InfoData.SaveCommentList> saveCommentLists;
    private Context context;
    private SaveDeleteCommentInterface saveDeleteCommentInterface;
    private int loginUserId , darkModeStatus;


    public SaveCommentAdapter(List<SaveCommentModel.InfoData.SaveCommentList> saveCommentLists,
                              Context context , SaveDeleteCommentInterface saveDeleteCommentInterface) {
        this.saveCommentLists = saveCommentLists;
        this.context = context;
        this.saveDeleteCommentInterface  = saveDeleteCommentInterface;
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_comment_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(darkModeStatus == 1){
            holder.llCommentDetails.setBackgroundResource(R.drawable.dark_post_border);
            holder.tvTitle.setTextColor(ContextCompat.getColor(context , R.color.light_white));
        }else{
            holder.llCommentDetails.setBackgroundResource(R.color.white);
            holder.tvTitle.setTextColor(ContextCompat.getColor(context , R.color.black_light));
        }

        if(saveCommentLists.get(position).getPostInfo() != null){
            holder.tvTitle.setText(saveCommentLists.get(position).getPostInfo().getTitle());
        }

        holder.llDelete.setOnClickListener(view -> {
            saveDeleteCommentInterface.saveDeleteComment(loginUserId, saveCommentLists.get(position).getId()
                    ,0,0,1 , position , saveCommentLists , context);
        });

        holder.llCommentDetails.setOnClickListener(view -> {
            Intent intent = new Intent(context , SaveCommentDetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// |Intent.FLAG_ACTIVITY_CLEAR_TASK  this code use for clear activity
            intent.putExtra(BaseUrl.SLUG , saveCommentLists.get(position).getPostInfo().getSlug());
            intent.putExtra(BaseUrl.ID , saveCommentLists.get(position).getComment_id());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return saveCommentLists.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvTitle;
        public LinearLayoutCompat llCommentDetails ,llDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            llDelete = itemView.findViewById(R.id.llDelete);
            llCommentDetails = itemView.findViewById(R.id.llCommentDetails);
           // llSaveCommentList = itemView.findViewById(R.id.llSaveCommentList);


        }
    }
}
