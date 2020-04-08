package com.jurgen.moviedts.Model;

import android.database.Cursor;
import android.net.Uri;

public class MovieModel {

    public static final String TABLE_NAME = "favorite_table";

    public static final String COL_KATEGORI = "kategori";
    public static final String COL_ID = "id";
    public static final String COL_MOVIE_ID = "movie_id";
    public static final String COL_POSTER_PATH = "poster_path";
    public static final String COL_BACKDROP_PATH = "backdrop_path";
    public static final String COL_TITLE = "title";
    public static final String COL_OVERVIEW = "overview";
    public static final String COL_VOTE_AVERAGE = "vote_average";
    public static final String COL_RELEASE_DATE = "release_date";

    public static final String AUTHORITY = "com.jurgen.moviedts";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_MOVIE_ID + " TEXT, "
            + COL_KATEGORI + " INTEGER,"
            + COL_POSTER_PATH + " TEXT, "
            + COL_BACKDROP_PATH + " TEXT, "
            + COL_TITLE + " TEXT,"
            + COL_OVERVIEW + " TEXT,"
            + COL_VOTE_AVERAGE + " REAL,"
            + COL_RELEASE_DATE + " TEXT" + ")";

    private Integer mKategori;
    private Integer id;
    private String movie_id;
    private String posterPath;
    private String backdropPath;
    private String title;
    private String overview;
    private Double voteAverage;
    private String releaseDate;

    public MovieModel() {
    }

    public MovieModel(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        this.movie_id = cursor.getString(cursor.getColumnIndex(COL_MOVIE_ID));
        this.mKategori = cursor.getInt(cursor.getColumnIndex(COL_KATEGORI));
        this.posterPath = cursor.getString(cursor.getColumnIndex(COL_POSTER_PATH));
        this.backdropPath = cursor.getString(cursor.getColumnIndex(COL_BACKDROP_PATH));
        this.title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
        this.overview = cursor.getString(cursor.getColumnIndex(COL_OVERVIEW));
        this.voteAverage = cursor.getDouble(cursor.getColumnIndex(COL_VOTE_AVERAGE));
        this.releaseDate = cursor.getString(cursor.getColumnIndex(COL_RELEASE_DATE));
    }

    public Integer getmKategori() {
        return mKategori;
    }

    public void setmKategori(Integer mKategori) {
        this.mKategori = mKategori;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
}
