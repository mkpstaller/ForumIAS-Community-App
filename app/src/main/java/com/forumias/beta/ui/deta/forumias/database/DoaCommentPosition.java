package com.forumias.beta.ui.deta.forumias.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DoaCommentPosition {
    @Insert
    void addCommentPosition(PostTable postTable);

    @Query("SELECT * FROM " + CommentListDatabase.TABLE_NAME+ " WHERE postId = :id")
    PostTable getPostPosition(int id);

    @Update
    int updateQuestionTable(PostTable quetionsTable);

    @Query("UPDATE "+CommentListDatabase.TABLE_NAME+ " SET commentPos = :commentPos WHERE postId = :postID")
    int updateData(int postID , int commentPos);

  /*  @Query("SELECT * FROM " + CommentListDatabase.TABLE_NAME)
    List<QuetionsTable> fetchAllTest();

    @Delete
    void deleteAllTest(List<QuetionsTable> testPaperTables);
*/

}

