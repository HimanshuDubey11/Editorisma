package project.horcrux.com.editorisma.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;
import project.horcrux.com.editorisma.R;
import yuku.ambilwarna.AmbilWarnaDialog;

public class MyImageEditor extends AppCompatActivity {

    private PhotoEditor photoEditor;
    private EditText editText;
    private ImageView textImageView, picsicon;
    private ImageView colorImageView, brushicon;
    private ImageView undoImageView, saveImageView;
    private Button doneButton;
    String shareImagePath = null;
    String imageName = null;
    static Bitmap myImageBitmap = null;
    private int color;
    private String text;
    private int RC_PICK = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_image_editor);

        color = Color.WHITE;

        undoImageView = findViewById(R.id.undoicon);
        saveImageView = findViewById(R.id.saveicon);

        final PhotoEditorView photoEditorView = findViewById(R.id.editorimageview);
        photoEditorView.getSource().setImageBitmap(HomeActivity.myBitmap);

        photoEditor = new PhotoEditor.Builder(MyImageEditor.this, photoEditorView).setPinchTextScalable(true).build();

        textImageView = findViewById(R.id.editortexticon);
        brushicon = findViewById(R.id.brushicon);
        textImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog1(view);

            }
        });

        colorImageView = findViewById(R.id.colorpickericon);
        colorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openColorPickerDialog(view);

            }
        });

        brushicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photoEditor.setBrushDrawingMode(true);
                photoEditor.setBrushSize(25);
                photoEditor.setOpacity(100);
                photoEditor.setBrushColor(color);

            }
        });

        picsicon = findViewById(R.id.picsaddicon);
        picsicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent,RC_PICK);

            }
        });

        undoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photoEditor.undo();

            }
        });

        saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveImage();

            }
        });


    }

    private void saveImage() {

        final File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                , "Editorisma");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        final String mImageName = "img_" + timeStamp + ".jpg";
        imageName = mImageName;

        SaveSettings saveSettings = new SaveSettings.Builder()
                .setClearViewsEnabled(false)
                .setTransparencyEnabled(false)
                .build();


        if (ActivityCompat.checkSelfPermission(MyImageEditor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        photoEditor.saveAsFile(mediaStorageDir.getPath() + File.separator + mImageName,saveSettings, new PhotoEditor.OnSaveListener() {
            @Override
            public void onSuccess(@NonNull String imagePath) {

                MediaScannerConnection.scanFile(MyImageEditor.this, new String[] { imagePath }, new String[] { "image/jpeg" }, null);
                Log.e("PhotoEditor", "Image Saved Successfully");
                shareImagePath = imagePath;
                Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
                myImageBitmap = myBitmap;
                startActivity(new Intent(MyImageEditor.this,SharingImage.class));
                finish();

            }

            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("PhotoEditor", "Failed to save Image");
                Toast.makeText(MyImageEditor.this, "error", Toast.LENGTH_SHORT).show();
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

                photoEditor.addImage(selectedBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void openColorPickerDialog(final View view) {

        AmbilWarnaDialog dialog1 = new AmbilWarnaDialog(this, color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int colorhere) {
                color = colorhere;
                photoEditor.setBrushColor(color);
                photoEditor.editText(view,text,color);

            }
        });
        dialog1.show();

    }

    public void openDialog1(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_texteditor);
        dialog.show();
        dialog.setCancelable(false);

        editText = dialog.findViewById(R.id.dialogedittext);
        doneButton = dialog.findViewById(R.id.dialogebutton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text = editText.getText().toString();
                dialog.dismiss();

                if(text.trim().equals("") || text == null) {

                } else {
                    photoEditor.addText("" + text, color);
                }

            }
        });

    }

}
