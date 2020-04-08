package com.jurgen.moviedts.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jurgen.moviedts.Fragments.SettingsFragment;
import com.jurgen.moviedts.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frag, new SettingsFragment()).commit();
    }
}
