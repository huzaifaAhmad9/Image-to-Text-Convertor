package com.example.imagetotext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    CardView cardView;
    TextView textView;

    Button imageButton;
    private Handler handler = new Handler();
    private Queue<YoYo.YoYoString> animationQueue = new LinkedList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView = findViewById(R.id.cardView);
        textView = findViewById(R.id.textView);
        imageButton = findViewById(R.id.btn);



        // Load the animation
        Animation slideUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        // Start the animation on the button
        imageButton.startAnimation(slideUpAnimation);



        // opening new activity
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
                finish();
//                Toasty.success(MainActivity.this, "Success!", Toast.LENGTH_SHORT, true).show();
            }
        });

        startAnimations();
    }

    private void startAnimations() {
        // Start the first animation on cardView with a slower speed
        YoYo.YoYoString cardViewAnimation = YoYo.with(Techniques.Pulse)
                .duration(2000)  // Adjust the duration for a slower animation
                .repeat(YoYo.INFINITE)
                .playOn(cardView);

        // Add the cardView animation to the queue
        animationQueue.offer(cardViewAnimation);

        // Start the first animation on textView with a delay and a slower speed
        handler.postDelayed(() -> startTextViewAnimation(), 500);
    }

    private void startTextViewAnimation() {
        // Retrieve the cardView animation from the queue
        YoYo.YoYoString cardViewAnimation = animationQueue.poll();

        if (cardViewAnimation != null) {
            // Start the next animation on textView with a slower speed
            YoYo.with(Techniques.Pulse)
                    .duration(2000)  // Adjust the duration for a slower animation
                    .onEnd(animator -> {
                        // Add the cardView animation back to the queue
                        animationQueue.offer(cardViewAnimation);

                        // Start the next animation on cardView with a delay and a slower speed
                        handler.postDelayed(() -> startAnimations(), 500);
                    })
                    .repeat(YoYo.INFINITE)
                    .playOn(textView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
    }








}
