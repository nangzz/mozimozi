package com.prograpy.app1.appdev1.lib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;

import com.prograpy.app1.appdev1.R;

import java.util.ArrayList;

public class LibMainActivity extends AppCompatActivity {
    private CarouselViewPager carousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_main);

        carousel = (CarouselViewPager) findViewById(R.id.carousel);
        ArrayList<Entity> entities = buildData();
        CarouselAdapter carouselAdapter = new CarouselAdapter(this, carousel, getSupportFragmentManager(), entities);

        carousel.setAdapter(carouselAdapter);
        carousel.addOnPageChangeListener(carouselAdapter);
        carousel.setOffscreenPageLimit(entities.size());
        carousel.setClipToPadding(false);

        carousel.setScrollDurationFactor(1.5f);
        carousel.setPageWidth(0.55f);
        carousel.settPaddingBetweenItem(16);
        carousel.setAlpha(0.0f);
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            carousel.startAnimation(false, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    carousel.setAlpha(1.0f);
                }

                @Override
                public void onAnimationEnd(Animation animation) { }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        }
    }

    private ArrayList<Entity> buildData() {
        ArrayList<Entity> entities = new ArrayList<>();

        entities.add(new Entity(R.drawable.poster_my_golden, "황금빛내인생", getString(R.string.myGoldenLife)));
        entities.add(new Entity(R.drawable.poster_love_returns, "미워도 사랑해", getString(R.string.loveReturns)));
        entities.add(new Entity(R.drawable.poster_live_together, "같이 살래요", getString(R.string.liveTogether)));
        entities.add(new Entity(R.drawable.poster_misty, "미스티", getString(R.string.misty)));
        entities.add(new Entity(R.drawable.poster_mother, "마더", getString(R.string.mother)));
        entities.add(new Entity(R.drawable.poster_return, "리턴", getString(R.string.rEturn)));
        entities.add(new Entity(R.drawable.poster_to_you, "시를 잊은 그대에게", getString(R.string.toYou)));

        return entities;
    }
}
