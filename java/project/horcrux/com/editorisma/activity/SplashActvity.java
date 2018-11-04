package project.horcrux.com.editorisma.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.horcrux.com.editorisma.R;

public class SplashActvity extends AppCompatActivity {

    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};

    int permRequestCode = 11;

    boolean permGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_actvity);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(perms,permRequestCode);
                }

            }
        },1000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 11:
                permGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                startActivity(new Intent(SplashActvity.this,HomeActivity.class));
                finish();
            break;

        }

    }
}
