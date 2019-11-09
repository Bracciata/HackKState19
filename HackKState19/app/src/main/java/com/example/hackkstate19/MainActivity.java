package com.example.hackkstate19;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionCloudDocumentRecognizerOptions;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final int GALLERY_REQUEST_CODE = 404;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE = 12;
    private Menu menu;
    private Camera mCamera;
    private CameraPreview mPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCamera();



    }
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    System.out.println(imgDecodableString);
                    // Set the Image in ImageView after decoding the String
                    openConfirmationScreen(BitmapFactory.decodeFile(imgDecodableString));
                    break;

            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        try{
        if(this.findViewById(android.R.id.content).getRootView()==findViewById(R.id.rl_root_one).getRootView()) {
            this.menu=menu;
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }} catch(Exception e){}
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // Open Settings
                return true;
            case R.id.menu_upload:
// Open Upload
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE);
                    }
                }else{
                pickFromGallery();}
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    pickFromGallery();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_full:
                // Open Full text read from image.
                openFull();
                break;
            case R.id.navigation_summary:
                openSummary();
                break;
        }
        return true;
    }

    private void openFull() {

        TextView outputText = findViewById(R.id.output_text);
        outputText.setText(fullText);
    }
    String summary  = "";
    String fullText="";
    private void openSummary() {
        TextView outputText = findViewById(R.id.output_text);
        outputText.setText(summary);

    }

    @Override
    protected void onDestroy() {
        if (mCamera != null) {
            mCamera.release();
        }
        super.onDestroy();
    }



    // https://developer.android.com/guide/topics/media/camera

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            Log.e(TAG, "HereTommy");
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            Log.e(TAG, e + "HERE TOMMTY");

        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

            if (bmp == null) {
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }
            openConfirmationScreen(bmp);
        }
    };

    void openConfirmationScreen(Bitmap bmp) {
        invalidateOptionsMenu();


        setContentView(R.layout.activity_confirmation);
        ImageView image = findViewById(R.id.imageview_confirm);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), true);
        final Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        image.setImageBitmap(rotatedBitmap);
        Button retakeButton = (Button) findViewById(R.id.button_retake);
        retakeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadCamera();
                    }
                }
        );
        Button confirmButton = (Button) findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Opens analysis output
                        openOutput(rotatedBitmap);
                    }
                }
        );
    }

    void openOutput(Bitmap bmp) {
        // Process image
        invalidateOptionsMenu();
        processImage(bmp);
        setContentView(R.layout.activity_output);
        BottomNavigationView btv = findViewById(R.id.navigation);
        btv.setOnNavigationItemSelectedListener(MainActivity.this);
        openSummary();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCamera();
            }
        });

    }

    void loadCamera() {
        try {
            mCamera.release();
        } catch (Exception e) {

        }
        setContentView(R.layout.activity_main);
        invalidateOptionsMenu();

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        // Add a listener to the Capture button
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );
    }

    // After selecting image call the following method.
    void processImage(Bitmap image) {
        FirebaseVisionDocumentTextRecognizer detector = getCloudDocumentRecognizer();
        VisionImage vImg = new VisionImage();
        FirebaseVisionImage myImage = vImg.imageFromBitmap(image);
        detector.processImage(myImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionDocumentText>() {
                    @Override
                    public void onSuccess(FirebaseVisionDocumentText result) {
                        // Task completed successfully
                        fullText = pullText(result);
                        //Send the above to Danny's summary code.
                        summary = Summary.request("", fullText, 20);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                    }
                });
    }

    // Code from: https://firebase.google.com/docs/ml-kit/android/recognize-text
    String pullText(FirebaseVisionDocumentText result) {
        String resultText = result.getText();
        // The following can be used for logging confidence.
        Float minConfidenceOfBlock = 1.0f;
        for (FirebaseVisionDocumentText.Block block : result.getBlocks()) {
            String blockText = block.getText();
            Float blockConfidence = block.getConfidence();
            minConfidenceOfBlock = Math.min(blockConfidence, minConfidenceOfBlock);
            List<RecognizedLanguage> blockRecognizedLanguages = block.getRecognizedLanguages();
            Rect blockFrame = block.getBoundingBox();
            for (FirebaseVisionDocumentText.Paragraph paragraph : block.getParagraphs()) {
                String paragraphText = paragraph.getText();
                Float paragraphConfidence = paragraph.getConfidence();
                List<RecognizedLanguage> paragraphRecognizedLanguages = paragraph.getRecognizedLanguages();
                Rect paragraphFrame = paragraph.getBoundingBox();
                for (FirebaseVisionDocumentText.Word word : paragraph.getWords()) {
                    String wordText = word.getText();
                    Float wordConfidence = word.getConfidence();
                    List<RecognizedLanguage> wordRecognizedLanguages = word.getRecognizedLanguages();
                    Rect wordFrame = word.getBoundingBox();
                    for (FirebaseVisionDocumentText.Symbol symbol : word.getSymbols()) {
                        String symbolText = symbol.getText();
                        Float symbolConfidence = symbol.getConfidence();
                        List<RecognizedLanguage> symbolRecognizedLanguages = symbol.getRecognizedLanguages();
                        Rect symbolFrame = symbol.getBoundingBox();
                    }
                }
            }
        }
        double outputConfidence = Math.round(minConfidenceOfBlock * 1000.0) / 1000.0;
        Toast.makeText(getApplicationContext(), ("Confidence: " + outputConfidence),
                Toast.LENGTH_LONG).show();
        return resultText;
    }

    private FirebaseVisionDocumentTextRecognizer getCloudDocumentRecognizer() {
        // [START mlkit_cloud_doc_recognizer]
        // Or, to provide language hints to assist with language detection:
        // See https://cloud.google.com/vision/docs/languages for supported languages
        FirebaseVisionCloudDocumentRecognizerOptions options =
                new FirebaseVisionCloudDocumentRecognizerOptions.Builder()
                        .setLanguageHints(Arrays.asList("en", "hi"))
                        .build();
        FirebaseVisionDocumentTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudDocumentTextRecognizer(options);
        // [END mlkit_cloud_doc_recognizer]

        return detector;
    }
}

