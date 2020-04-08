package com.jurgen.moviedts.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jurgen.moviedts.Database.FavoriteHelper;
import com.jurgen.moviedts.HTTP.API;
import com.jurgen.moviedts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jurgen.moviedts.Model.MovieModel.COL_BACKDROP_PATH;
import static com.jurgen.moviedts.Model.MovieModel.COL_KATEGORI;
import static com.jurgen.moviedts.Model.MovieModel.COL_MOVIE_ID;
import static com.jurgen.moviedts.Model.MovieModel.COL_OVERVIEW;
import static com.jurgen.moviedts.Model.MovieModel.COL_POSTER_PATH;
import static com.jurgen.moviedts.Model.MovieModel.COL_RELEASE_DATE;
import static com.jurgen.moviedts.Model.MovieModel.COL_TITLE;
import static com.jurgen.moviedts.Model.MovieModel.COL_VOTE_AVERAGE;
import static com.jurgen.moviedts.Model.MovieModel.CONTENT_URI;
import static com.jurgen.moviedts.Widget.FavoriteWidget.sendRefreshBroadcast;

public class Details extends AppCompatActivity {

    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.backdropImage)
    ImageView backdropImage;
    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.release_date)
    TextView release_dateView;
    @BindView(R.id.vote_average)
    TextView vote_averageView;
    @BindView(R.id.overview_text)
    TextView overview_textView;
    @BindView(R.id.btnFavorite)
    ImageView btnFavorite;
    @BindView(R.id.toolbardetail)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBarBackdrop)
    ProgressBar progressBarBackdrop;

    boolean isFavorited = false;
    Integer mKategori, Ids;
    String poster_url, backdrop_url, title, release_date, overview_text, movie_id;
    Double vote_average;

    private FavoriteHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        helper = new FavoriteHelper(getApplicationContext());
        helper.open();

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFavorited) {
                    ContentValues content = new ContentValues();
                    content.put(COL_MOVIE_ID, movie_id);
                    content.put(COL_TITLE, title);
                    content.put(COL_POSTER_PATH, poster_url);
                    content.put(COL_BACKDROP_PATH, backdrop_url);
                    content.put(COL_VOTE_AVERAGE, vote_average);
                    content.put(COL_RELEASE_DATE, release_date);
                    content.put(COL_OVERVIEW, overview_text);
                    content.put(COL_KATEGORI, mKategori);

                    if (getContentResolver().insert(CONTENT_URI, content) != null) {
                        Toast.makeText(Details.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        btnFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_filled, null));
                    } else {
                        Toast.makeText(Details.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Uri uri = Uri.parse(CONTENT_URI + "/" + Ids);
                    int i = getContentResolver().delete(uri, null, null);
                    btnFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_unfilled, null));
                    Toast.makeText(Details.this, "Unfavorited", Toast.LENGTH_SHORT).show();
                }

                sendRefreshBroadcast(getApplicationContext());
            }
        });
        getDataFromIntent();
    }


    private void getDataFromIntent() {
        mKategori = getIntent().getIntExtra("KATEGORI", 0);
        movie_id = getIntent().getStringExtra("MOVIE_ID");
        poster_url = getIntent().getStringExtra("POSTER_URL");
        backdrop_url = getIntent().getStringExtra("BACKDROP_URL");
        title = getIntent().getStringExtra("TITLE");
        release_date = getIntent().getStringExtra("RELEASE_DATE");
        vote_average = getIntent().getDoubleExtra("VOTE_AVERAGE", 0);
        overview_text = getIntent().getStringExtra("OVERVIEW");

        if (isMovieFavorited()) {
            btnFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_filled, null));
            isFavorited = true;
        } else {
            isFavorited = false;
        }

        Glide.with(this).load(API.IMAGE_URL + poster_url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(Details.this, "Image failed load", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(poster);

        Glide.with(this).load(API.IMAGE_URL + backdrop_url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBarBackdrop.setVisibility(View.GONE);
                return false;
            }
        }).into(backdropImage);

        titleView.setText(title);
        release_dateView.setText(release_date);
        vote_averageView.setText(String.valueOf(vote_average));
        overview_textView.setText(overview_text);
    }

    private boolean isMovieFavorited() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int movieId;
        if (cursor.moveToFirst()) {
            do {
                movieId = cursor.getInt(1);
                if (movieId == Integer.parseInt(movie_id)) {
                    Ids = cursor.getInt(0);
                    return true;
                }
            }
            while (cursor.moveToNext());
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}