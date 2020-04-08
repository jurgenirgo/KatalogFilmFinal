package com.jurgen.moviedts.Fragments.TabLayout;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jurgen.moviedts.Adapter.MovieListAdapter;
import com.jurgen.moviedts.Database.DatabaseHelper;
import com.jurgen.moviedts.Model.MovieModel;
import com.jurgen.moviedts.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.jurgen.moviedts.Model.MovieModel.COL_BACKDROP_PATH;
import static com.jurgen.moviedts.Model.MovieModel.COL_KATEGORI;
import static com.jurgen.moviedts.Model.MovieModel.COL_MOVIE_ID;
import static com.jurgen.moviedts.Model.MovieModel.COL_OVERVIEW;
import static com.jurgen.moviedts.Model.MovieModel.COL_POSTER_PATH;
import static com.jurgen.moviedts.Model.MovieModel.COL_RELEASE_DATE;
import static com.jurgen.moviedts.Model.MovieModel.COL_TITLE;
import static com.jurgen.moviedts.Model.MovieModel.COL_VOTE_AVERAGE;
import static com.jurgen.moviedts.Model.MovieModel.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FTVShowFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;

    public FTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ftvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(new MovieListAdapter(getContext(), dataList(), 1));
    }

    private ArrayList<MovieModel> dataList() {
        ArrayList<MovieModel> data = new ArrayList<>();
        Uri uri = Uri.parse(CONTENT_URI + "");
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex(COL_KATEGORI)) == 1) {
                    MovieModel model = new MovieModel();
                    model.setMovie_id(cursor.getString(cursor.getColumnIndex(COL_MOVIE_ID)));
                    model.setPosterPath(cursor.getString(cursor.getColumnIndex(COL_POSTER_PATH)));
                    model.setBackdropPath(cursor.getString(cursor.getColumnIndex(COL_BACKDROP_PATH)));
                    model.setOverview(cursor.getString(cursor.getColumnIndex(COL_OVERVIEW)));
                    model.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
                    model.setReleaseDate(cursor.getString(cursor.getColumnIndex(COL_RELEASE_DATE)));
                    model.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(COL_VOTE_AVERAGE)));

                    data.add(model);
                }
            } while (cursor.moveToNext());
        }

        return data;
    }
}
