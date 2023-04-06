package com.example.testorangapp.main;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.testorangapp.R;
import com.example.testorangapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;
    //
    //firebase 로직 분리하기
    //파이어베이스 어뎁터를 이용해서 클릭 가능하게 구현하기
    //파이어베이스 그리드 뷰형태로 구현.. 이미지 크기 조절까지
    //파이어베이스 본 게시글구현하기
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFab();
            }
        });
        binding.appBarMain.fabTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.appBarMain.scrollView.smoothScrollTo(0,0,500);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_testshow, R.id.nav_post_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    private void toggleFab() {
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.appBarMain.fabPost, "translationY", 0f).start();
            ObjectAnimator.ofFloat(binding.appBarMain.fabAlarm, "translationY", 0f).start();
            binding.appBarMain.fabPost.setVisibility(View.GONE);
            binding.appBarMain.fabAlarm.setVisibility(View.GONE);
            binding.appBarMain.fabMain.setImageResource(R.drawable.ic_fab_add);
        } else {
            binding.appBarMain.fabPost.setVisibility(View.VISIBLE);
            binding.appBarMain.fabAlarm.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(binding.appBarMain.fabPost, "translationY", -210f).start();
            ObjectAnimator.ofFloat(binding.appBarMain.fabAlarm, "translationY", -210f).start();
            binding.appBarMain.fabMain.setImageResource(R.drawable.ic_fab_close);
        }
        isFabOpen = !isFabOpen;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}