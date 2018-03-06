package com.example.gili.jdvtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        final ImageView imgAnim = (ImageView) findViewById(R.id.imgAnim);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim2 = AnimationUtils.loadAnimation(AnimActivity.this,R.anim.move);
                anim2.setFillAfter(true);
                imgAnim.startAnimation(anim2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imgAnim.startAnimation(anim);
    }
}
