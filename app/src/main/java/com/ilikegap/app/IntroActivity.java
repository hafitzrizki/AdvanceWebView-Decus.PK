package com.ilikegap.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addSlide(SampleSlide.newInstance(R.layout.slide_1));
        addSlide(SampleSlide.newInstance(R.layout.slide_2));
        addSlide(SampleSlide.newInstance(R.layout.slide_3));
        //addSlide(AppIntroFragment.newInstance("Judul Slide", "Deskripsi Slide", R.drawable.ic_launcher_foreground), getColor(R.color.colorPrimaryDark));

        showSkipButton(false); //disable tombol skip
        setFadeAnimation();//animasi tiap slide
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
