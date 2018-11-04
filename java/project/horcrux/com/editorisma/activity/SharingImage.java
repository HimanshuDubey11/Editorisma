package project.horcrux.com.editorisma.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.OutputStream;

import project.horcrux.com.editorisma.R;
import project.horcrux.com.editorisma.activity.MyImageEditor;

public class SharingImage extends AppCompatActivity {

    private ImageView imageView, shareImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_image);

        imageView = findViewById(R.id.sharingimageview);

        imageView.setImageBitmap(MyImageEditor.myImageBitmap);

        shareImageView = findViewById(R.id.shareicon);

        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });


    }

    private void shareImage() {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);

        OutputStream outstream;
        try {
            outstream = getContentResolver().openOutputStream(uri);
            MyImageEditor.myImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));


    }
}
