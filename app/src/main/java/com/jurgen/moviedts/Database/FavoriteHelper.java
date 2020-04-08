package com.jurgen.moviedts.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jurgen.moviedts.Model.MovieModel;

import static com.jurgen.moviedts.Model.MovieModel.COL_ID;

public class FavoriteHelper {
    private static FavoriteHelper instance;
    private final DatabaseHelper db;
    private SQLiteDatabase sqLiteDatabase;

    public FavoriteHelper(Context context) {
        db = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (instance == null) {
                    instance = new FavoriteHelper(context);
                }
            }
        }
        return instance;
    }

    public void open() throws SQLException {
        sqLiteDatabase = db.getWritableDatabase();
    }

    public void close() {
        db.close();

        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public Cursor queryByIdProvider(String id) {

        return sqLiteDatabase.query(MovieModel.TABLE_NAME, null
                , COL_ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return sqLiteDatabase.query(MovieModel.TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , COL_ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return sqLiteDatabase.insert(MovieModel.TABLE_NAME, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return sqLiteDatabase.update(MovieModel.TABLE_NAME, values, COL_ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase.delete(MovieModel.TABLE_NAME, COL_ID + " = ?", new String[]{id});
    }

}
