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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prograpy.app1.appdev1.R;
import com.prograpy.app1.appdev1.drama.item.DramaItemListActivity;
import com.prograpy.app1.appdev1.vo.DramaVO;

public class CarouselFragment extends Fragment {

    private RelativeLayout descriptionLayout;
    private ImageView backButton;
    private TextView descriptionView;
    private ImageView infoButton;
    private ImageView imageView;

    private CarouselViewPager carousel;

    private static Context mContext;

    public static Fragment newInstance(Context context, DramaVO mainDramaData, int position, float scale) {

        CarouselFragment fragment = new CarouselFragment();

        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putFloat("scale", scale);

        if(mainDramaData != null){
            b.putString("image", mainDramaData.getD_img());
            b.putString("title", mainDramaData.getD_name());
            b.putInt("dramaId", mainDramaData.getD_id());
            b.putString("description", mainDramaData.getD_name());
        }

        fragment.setArguments(b);

        mContext = context;

        return Fragment.instantiate(context, CarouselFragment.class.getName(), b);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }


        final int position = getArguments().getInt("position");
        final String title = getArguments().getString("title");
        final int dramaId = getArguments().getInt("dramaId");

        ScaledFrameLayout root = (ScaledFrameLayout) inflater.inflate(R.layout.view_main_drama_carousel, container, false);
        root.setScaleBoth(getArguments().getFloat("scale"));
        root.setTag("view" + position);
        computePadding(root);


        carousel = (CarouselViewPager) ((Activity)mContext).findViewById(R.id.carousel);

        imageView = (ImageView) root.findViewById(R.id.image);

        Glide.with(mContext).load(getArguments().getString("image")).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DramaItemListActivity.class);
                i.putExtra("title", title);
                i.putExtra("dramaId", dramaId);
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
                int paddingWidth = (int) (width * (1-carousel.getPageWidth())/2);
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