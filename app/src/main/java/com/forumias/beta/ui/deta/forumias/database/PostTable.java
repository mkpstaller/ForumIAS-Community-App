package com.forumias.beta.ui.deta.forumias.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = CommentListDatabase.TABLE_NAME)
public class PostTable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int postId;
    public int commentPos;


}
