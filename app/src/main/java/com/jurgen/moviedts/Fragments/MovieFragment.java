package com.jurgen.moviedts.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jurgen.moviedts.Adapter.MovieListAdapter;
import com.jurgen.moviedts.HTTP.API;
import com.jurgen.moviedts.HTTP.Service;
import com.jurgen.moviedts.Model.MovieModel;
import com.jurgen.moviedts.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Service service;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getData();
    }

    public void getData() {
        service = API.getClient().create(Service.class);

        Call<JsonObject> getMoviesList = service.getMovies(
                API.API_KEY,
                API.LANG
        );

        getMoviesList.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject jsonObject = response.body();
                JsonArray resultArr = jsonObject.getAsJsonArray("results");

                ArrayList<MovieModel> arrayList = new ArrayList<>();
                for (int i = 0; i < resultArr.size(); i++) {
                    MovieModel model = new MovieModel();
                    JsonObject object = resultArr.get(i).getAsJsonObject();

                    model.setMovie_id(object.get("id").getAsString());
                    model.setBackdropPath(object.get("backdrop_path").getAsString());
                    model.setOverview(object.get("overview").getAsString());
                    model.setPosterPath(object.get("poster_path").getAsString());
                    model.setTitle(object.get("original_title").getAsString());
                    model.setReleaseDate(object.get("release_date").getAsString());
                    model.setVoteAverage(object.get("vote_average").getAsDouble());

                    arrayList.add(model);
                }

                recyclerView.setAdapter(new MovieListAdapter(getContext(), arrayList, 0));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error Get Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
