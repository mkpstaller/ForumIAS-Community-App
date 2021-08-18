package com.forumias.beta.ui.deta.forumias.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {PostTable.class}, version = 1,exportSchema = false)
public abstract class CommentListDatabase extends RoomDatabase {
    public static final String DB_NAME = "ForumIAs";
    public static final String TABLE_NAME = "PostPosition";
    public abstract DoaCommentPosition doaCommment();

}
