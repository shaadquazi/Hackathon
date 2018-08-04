package com.example.shaad.snpppapp.Instruction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import com.example.shaad.snpppapp.Login.LoginActivity;
import com.example.shaad.snpppapp.R;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

/**
 * Created by Shaad on 16-03-2018.
 */

public class InstructionOnBoarding extends TutorialActivity {

    private static final String TAG = "InstructionOnBoarding";

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if(!prefManager.isFirstTimeLaunch()){
            finishTutorial();
        }

        addFragment(new Step.Builder().setTitle(this.getString(R.string.instruction_firebase_title))
                .setContent(this.getString(R.string.instruction_firebase))
                .setBackgroundColor(Color.parseColor("#000000")) // int background color
                .setDrawable(R.drawable.instruction_firebase) // int top drawable
                .setSummary("")
                .build());
        addFragment(new Step.Builder().setTitle(this.getString(R.string.instruction_data_collection_title))
                .setContent(this.getString(R.string.instruction_data_collection))
                .setBackgroundColor(Color.parseColor("#000000")) // int background color
                .setDrawable(R.drawable.instruction_data_collection) // int top drawable
                .setSummary("")
                .build());
        addFragment(new Step.Builder().setTitle(this.getString(R.string.instruction_map_title))
                .setContent(this.getString(R.string.instruction_map))
                .setBackgroundColor(Color.parseColor("#000000")) // int background color
                .setDrawable(R.drawable.instruction_map) // int top drawable
                .setSummary("")
                .build());


    }




    @Override
    public void finishTutorial() {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
