package com.madonasyombua.growwithgoogleteamproject.ui.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.madonasyombua.growwithgoogleteamproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayo on 2/12/2018.
 */

public class OnBoardingActivity extends AhoyOnboarderActivity {

    private static final String TAG = "OnBoardingActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creating the cards for the intro
        AhoyOnboarderCard card1 = new AhoyOnboarderCard("Developers Portfolio", "Welcome! ",R.drawable.logo);
        card1.setBackgroundColor(R.color.black_transparent);
        card1.setTitleColor(R.color.white);
        card1.setDescriptionColor(R.color.grey_200);



        AhoyOnboarderCard card2 = new AhoyOnboarderCard("About", "We want to help developers get a platform to share their projects and showcase their work for free.", R.drawable.logo);
        card2.setBackgroundColor(R.color.black_transparent);
        card2.setTitleColor(R.color.white);
        card2.setDescriptionColor(R.color.grey_200);

        AhoyOnboarderCard card3 = new AhoyOnboarderCard("Share Your Work", "Thank you for using our platform. Enjoy", R.drawable.logo);
        card3.setBackgroundColor(R.color.black_transparent);
        card3.setTitleColor(R.color.white);
        card3.setDescriptionColor(R.color.grey_200);

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(card1);
        pages.add(card2);
        pages.add(card3);

        setOnboardPages(pages);
        setGradientBackground();
        setFinishButtonTitle("Click To Proceed");
        showNavigationControls(true);

        Log.i(TAG, "onCreate: Setting up the cards");
    }

    @Override
    public void onFinishButtonPressed() {
        Intent finishIntent = new Intent();
        setResult(Activity.RESULT_OK, finishIntent);
        Log.i(TAG, "onFinishButtonPressed: Closing the onboarding activity");
        finish();
    }
}
