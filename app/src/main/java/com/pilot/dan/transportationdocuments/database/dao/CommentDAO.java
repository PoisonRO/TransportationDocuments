package com.pilot.dan.transportationdocuments.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.pilot.dan.transportationdocuments.database.Comment;
import com.pilot.dan.transportationdocuments.database.LoadDetail;
import com.pilot.dan.transportationdocuments.database.MySQLiteHelper;

import java.sql.SQLException;

/**
 * Created by dan on 9/22/15.
 */
public class CommentDAO extends TemplateDAO {

    private String[] allCollumns = {
                                        MySQLiteHelper.COLLUMN_COMMENTS_ID,
                                        MySQLiteHelper.COLLUMN_COMMENTS_TEXT
                                    };

    Context context;

    public  CommentDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void updateComment(Comment comment) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLLUMN_COMMENTS_ID,comment.getComment_id());
        values.put(MySQLiteHelper.COLLUMN_COMMENTS_TEXT, comment.getComment());

        String whereClause = MySQLiteHelper.COLLUMN_COMMENTS_ID+" = '"+comment.getComment_id()+"'";

        database.update(MySQLiteHelper.TABLE_COMMENTS, values, whereClause, null);
    }

    public Comment createNewComment(String szComment) {
        NextNumberDAO nextNumberDAO;

        nextNumberDAO = new NextNumberDAO(context);

        try {
            nextNumberDAO.open();
        } catch (SQLException e) {
            //TODO SQL Ex
        }

        nextNumberDAO.close();

        Comment comment = new Comment();
        try {
            comment.setComment_id(nextNumberDAO.getNextNumber(NextNumberDAO.NEXT_NUMER_COMMENT));
        } catch (SQLException e) {

        }
        comment.setComment(szComment);

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLLUMN_COMMENTS_ID,comment.getComment_id());
        values.put(MySQLiteHelper.COLLUMN_COMMENTS_TEXT, szComment);

        database.insertOrThrow(MySQLiteHelper.TABLE_COMMENTS, null, values);

        return comment;
    }

    public Comment getCommnet(long _id,long _line) {

        LoadDetailDAO loadDetailDAO = new LoadDetailDAO(context);
        try {
            loadDetailDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LoadDetail dtl = loadDetailDAO.getLoadDetail(_id,_line);
        loadDetailDAO.close();

        String whereStatement = MySQLiteHelper.COLLUMN_LOAD_DETAIL_ID+" = '"+dtl.get_comment_id()+"'";

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,allCollumns,whereStatement,null,null,null,null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            return cursorToComment(cursor);
        } else
            return null;

    }

    private Comment cursorToComment(Cursor cursor)
    {
        Comment comment = new Comment();

        comment.setComment_id(cursor.getInt(0));
        comment.setComment(cursor.getString(1));

        return  comment;
    }

}
