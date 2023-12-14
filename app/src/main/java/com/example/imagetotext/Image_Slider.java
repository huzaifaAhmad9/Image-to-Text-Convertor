package com.example.imagetotext;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class Image_Slider extends AppCompatActivity {
    private ImageSlider imageSlider;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        imageSlider = findViewById(R.id.imgslider);

        // adding image to show
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.img1,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img2,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img3,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img4,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img5,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img6,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img7,ScaleTypes.FIT));


        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


    }
}