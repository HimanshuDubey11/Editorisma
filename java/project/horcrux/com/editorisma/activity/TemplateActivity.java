package project.horcrux.com.editorisma.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import project.horcrux.com.editorisma.R;
import project.horcrux.com.editorisma.fragments.DownloadedFragment;
import project.horcrux.com.editorisma.fragments.InstalledFragment;

public class TemplateActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.navigation_installed:

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.templatefragment,new InstalledFragment());
                    fragmentTransaction.commit();
                    return true;

                case R.id.navigation_downloaded:

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.templatefragment,new DownloadedFragment());
                    fragmentTransaction.commit();
                    return true;

            }

            return false;
        }
    };


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        bottomNavigationView = findViewById(R.id.templatebottomnavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.templatefragment,new InstalledFragment());
        fragmentTransaction.commit();

    }
}
