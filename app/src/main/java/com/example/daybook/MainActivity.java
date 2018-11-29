package com.example.daybook;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.mikepenz.materialdrawer.*;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewFlipper flipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int images[] = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4};
        flipper = findViewById(R.id.flipperView);


        for(int image : images)
            flipperImages(image);

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.color.md_dark_background)
                .addProfiles(
                        new ProfileDrawerItem().withName("Gut").withEmail("gutfilip@gmail.com").withIcon(R.drawable.avatar)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .withCloseDrawerOnProfileListClick(true)
                .build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Account Settings")
                .withTextColor(Color.GRAY)
                .withSelectedColor(Color.BLUE)
                .withSelectedTextColor(Color.YELLOW)
                .withIcon(R.drawable.settings).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = new Intent(MainActivity.this, AccountSettingsActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    }
                });


        new DrawerBuilder().withActivity(this).withAccountHeader(accountHeader)
                .addDrawerItems(item1).build();


        /*
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Home")
                .withTextColor(Color.LTGRAY)
                .withSelectedColor(Color.LTGRAY)
                .withSelectedTextColor(Color.BLACK)
                .withIcon(R.drawable.home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("My Appointments")
                .withTextColor(Color.LTGRAY)
                .withSelectedColor(Color.LTGRAY)
                .withSelectedTextColor(Color.BLACK)
                .withIcon(R.drawable.calendar);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Settings")
                .withTextColor(Color.LTGRAY)
                .withSelectedColor(Color.LTGRAY)
                .withSelectedTextColor(Color.BLACK)
                .withIcon(R.drawable.black_settings_button);

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return false;
                    }
                })
                .withSliderBackgroundColor(Color.DKGRAY)
                .withCloseOnClick(true)
                .build();*/
    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(5000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setInAnimation(this, android.R.anim.slide_out_right);
    }
}
