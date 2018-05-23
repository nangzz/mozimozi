package com.prograpy.app1.appdev1.lib.mainlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.dramaItem.DramaItemListActivity;

public class CarouselFragment extends Fragment {

    private RelativeLayout descriptionLayout;
    private ImageView backButton;
    private TextView descriptionView;
    private ImageView infoButton;
    private ImageView imageView;

    private CarouselViewPager carousel;

    private static Context mContext;

    public static Fragment newInstance(Context context, MainDramaData mainDramaData, int position, float scale) {

        CarouselFragment fragment = new CarouselFragment();

        Bundle b = new Bundle();
        b.putInt("image", mainDramaData.imageRes);
        b.putInt("position", position);
        b.putFloat("scale", scale);
        b.putString("title", mainDramaData.titleRes);
        b.putString("description", mainDramaData.description);

        fragment.setArguments(b);

        mContext = context;

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        ScaledFrameLayout root = (ScaledFrameLayout) inflater.inflate(R.layout.item_carousel, container, false);
        root.setScaleBoth(getArguments().getFloat("scale"));
        root.setTag("view" + getArguments().getInt("position"));
        computePadding(root);


        carousel = (CarouselViewPager) ((Activity)mContext).findViewById(R.id.carousel);

        imageView = (ImageView) root.findViewById(R.id.image);
        imageView.setImageResource(getArguments().getInt("image"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carousel.setCurrentItem(getArguments().getInt("position"), true);

                Intent i = new Intent(getActivity(), DramaItemListActivity.class);
                i.putExtra("title", getArguments().getString("title"));
                startActivity(i);

            }
        });


        descriptionLayout = (RelativeLayout) root.findViewById(R.id.description_layout);
        backButton = (ImageView) root.findViewById(R.id.back_button);
        descriptionView = (TextView) root.findViewById(R.id.description_view);

        infoButton = (ImageView) root.findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View infoView) {
                opacityAnimation(descriptionLayout, 0.0f, 1.0f, 750, true, new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        descriptionView.setText(getArguments().getString("description"));
                        backButton.setEnabled(true);
                        infoView.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        backButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View backView) {
                                opacityAnimation(descriptionLayout, 1.0f, 0.0f, 750, true, new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {}

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        backView.setEnabled(false);
                                        infoView.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {}
                                });
                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        });

        return root;
    }

    private void computePadding(final ViewGroup rootLayout) {
        rootLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = rootLayout.getWidth();
                int paddingWidth = (int) (width * (1-carousel.getPageWidth())/4);
                rootLayout.setPadding(paddingWidth, 0, paddingWidth, 0);
                carousel.setPageMargin(-(paddingWidth - carousel.getPaddingBetweenItem()) * 2);
            }
        });
    }

    private void opacityAnimation(final View view, float fromAlpha, float toAlpha, int duration, boolean keepResult, Animation.AnimationListener listener){
        Animation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        if(keepResult) alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setAnimationListener(listener);
        view.startAnimation(alphaAnimation);
    }
}