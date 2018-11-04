package project.horcrux.com.editorisma.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.InputStream;

import project.horcrux.com.editorisma.R;

public class HomeActivity extends AppCompatActivity {

    private static final int RC_PICK = 20;
    private static final int Rc_Camera = 8;
    Button templateButton, galleryButton, cameraButton;
    public static Bitmap myBitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        templateButton = findViewById(R.id.buttontemplate);
        galleryButton = findViewById(R.id.buttongallery);
        cameraButton = findViewById(R.id.buttoncamera);

        templateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            startActivity(new Intent(HomeActivity.this,TemplateActivity.class));

            }
        });


        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent,RC_PICK);

            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,Rc_Camera);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_PICK && resultCode == Activity.RESULT_OK && null!= data) {


            InputStream imageStream = null;
            try {
                Uri imageUri = data.getData();
                imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedBitmap = BitmapFactory.decodeStream(imageStream);

                HomeActivity.myBitmap = selectedBitmap;

                startActivity(new Intent(HomeActivity.this,MyImageEditor.class));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(requestCode == Rc_Camera) {
            Bundle b = data.getExtras();
            myBitmap = (Bitmap) b.get("data");               //data is key for image

            startActivity(new Intent(HomeActivity.this, MyImageEditor.class));

        }

    }
}
