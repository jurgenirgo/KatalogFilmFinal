package com.jurgen.moviedts.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchText)
    TextView searchText;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        String keywordPencarian = getIntent().getStringExtra("KEYWORDPENCARIAN");
        int kategoriPencarian = getIntent().getIntExtra("KATEGORIPENCARIAN", 0);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        searchText.setText("Keyword: " + keywordPencarian);

        getData(keywordPencarian, kategoriPencarian);
    }

    private void getData(String keywordPencarian, int kategoriPencarian) {
        service = API.getClient().create(Service.class);

        if (kategoriPencarian == 0) {
            Call<JsonObject> pencarian = service.searchMovie(API.API_KEY, API.LANG, keywordPencarian);

            pencarian.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject jsonObject = response.body();
                    JsonArray resultArr = jsonObject.getAsJsonArray("results");

                    ArrayList<MovieModel> arrayList = new ArrayList<>();
                    if (resultArr.size() > 0) {
                        for (int i = 0; i < resultArr.size(); i++) {
                            MovieModel model = new MovieModel();
                            JsonObject object = resultArr.get(i).getAsJsonObject();

                            model.setPosterPath((object.get("poster_path").isJsonNull() ? "" : object.get("poster_path").getAsString()));
                            model.setBackdropPath((object.get("backdrop_path").isJsonNull() ? "" : object.get("backdrop_path").getAsString()));
                            model.setMovie_id(object.get("id").getAsString());
                            model.setOverview(object.get("overview").getAsString());
                            model.setTitle(object.get("original_title").getAsString());
                            model.setReleaseDate(object.get("release_date").getAsString());
                            model.setVoteAverage(object.get("vote_average").getAsDouble());

                            arrayList.add(model);
                        }
                    }

                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(new MovieListAdapter(SearchActivity.this, arrayList, 0));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (kategoriPencarian == 1) {
            Call<JsonObject> pencarian = service.searchTv(API.API_KEY, API.LANG, keywordPencarian);

            pencarian.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject jsonObject = response.body();
                    JsonArray resultArr = jsonObject.getAsJsonArray("results");

                    ArrayList<MovieModel> arrayList = new ArrayList<>();
                    for (int i = 0; i < resultArr.size(); i++) {
                        MovieModel model = new MovieModel();
                        JsonObject object = resultArr.get(i).getAsJsonObject();

                        model.setMovie_id(object.get("id").getAsString());
                        model.setPosterPath((object.get("poster_path").isJsonNull() ? "" : object.get("poster_path").getAsString()));
                        model.setBackdropPath((object.get("backdrop_path").isJsonNull() ? "" : object.get("backdrop_path").getAsString()));
                        model.setOverview(object.get("overview").getAsString());
                        model.setTitle(object.get("name").getAsString());
                        model.setReleaseDate(object.get("first_air_date").getAsString());
                        model.setVoteAverage(object.get("vote_average").getAsDouble());

                        arrayList.add(model);
                    }

                    recyclerView.setAdapter(new MovieListAdapter(SearchActivity.this, arrayList, 1));
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
