package com.example.imagetotext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import es.dmoral.toasty.Toasty;

public class NewActivity extends AppCompatActivity {

    ImageView img;
    TextView textTV;
    Button take_photo, detect;
    private Bitmap imageBitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PERMISSION_CODE = 200;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        img = findViewById(R.id.img);
        textTV = findViewById(R.id.textTV);
        take_photo = findViewById(R.id.take_photo);
        detect = findViewById(R.id.detect);

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetectText();
            }
        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermission()) {
                    CaptureImage();
                } else {
                    RequestPermission();
                }
            }

        });
    }

    // permission methods
    private Boolean CheckPermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA
        }, PERMISSION_CODE);
    }

    // capture image
    public void CaptureImage() {
        Intent take_pic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (take_pic.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(take_pic, REQUEST_IMAGE_CAPTURE);
        }
    }

    // on successful Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (cameraPermission) {
                Toasty.success(this, "Permission Granted!!!", Toast.LENGTH_SHORT, true).show();
                CaptureImage();
            } else {
                Toasty.error(this, "Permission Denied!!!!", Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    // on activity result  ---> setting image which capture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }

    // detect text
    public void DetectText() {
        if (imageBitmap != null) {
            InputImage image = InputImage.fromBitmap(imageBitmap, 0);
            TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
            Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(Text text) {
                    StringBuilder resultText = new StringBuilder();
                    for (Text.TextBlock block : text.getTextBlocks()) {
                        String blockText = block.getText();
                        Point[] blockCornerPoint = block.getCornerPoints();
                        Rect blockFrame = block.getBoundingBox();
                        for (Text.Line line : block.getLines()){
                            String lineText = line.getText();
                            Point[] lineCornerPoints = line.getCornerPoints();
                            Rect lineRect = line.getBoundingBox();
                            for (Text.Element element : line.getElements()){
                                String elementText = element.getText();
                                resultText.append(elementText).append(" ");
                            }
                        }
                    }
                    // displaying results outside of the inner loops
                    textTV.setText(resultText.toString().trim());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toasty.error(NewActivity.this,"Failed to Detect Text!!!!", Toast.LENGTH_SHORT, true).show();

                }
            });
        }
    }
}
