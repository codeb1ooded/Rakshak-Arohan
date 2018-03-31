package com.aarushi.crime_mappingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aarushi.crime_mappingapp.safest_route.SafestRouteActivity;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CardView btn_reportFiling, btn_safestRoute, btn_crimeNeighbourhood, btn_trackCrime, btn_registerComplain;
    DrawerLayout drawer;
    Toolbar toolbar;
    FloatingActionButton fab;
    NavigationView navigationView;
    LinearLayout layer1, layer2, layer3;
    final Context context = this;
    private boolean isPublicUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("is_public_user")) {
            isPublicUser = extras.getBoolean("is_public_user");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        btn_reportFiling = (CardView) findViewById(R.id.btn_reportFiling);
        btn_safestRoute = (CardView) findViewById(R.id.btn_safestRoute);
        btn_crimeNeighbourhood = (CardView) findViewById(R.id.btn_crimeNeighbourhood);
        btn_trackCrime = (CardView) findViewById(R.id.btn_trackCrime);
        btn_registerComplain = (CardView) findViewById(R.id.btn_complainRegister);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        layer1 = (LinearLayout) findViewById(R.id.layer1);
        layer2 = (LinearLayout) findViewById(R.id.layer2);
        layer3 = (LinearLayout) findViewById(R.id.layer3);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (!isPublicUser) {
            btn_reportFiling.setVisibility(View.GONE);
            btn_safestRoute.setVisibility(View.GONE);
            btn_crimeNeighbourhood.setVisibility(View.GONE);
            btn_trackCrime.setVisibility(View.GONE);
            btn_registerComplain.setVisibility(View.VISIBLE);
            layer1.setVisibility(View.GONE);
            layer2.setVisibility(View.GONE);
        } else {
            btn_registerComplain.setVisibility(View.GONE);
            layer3.setVisibility(View.GONE);
        }


        btn_crimeNeighbourhood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, NeighbourhoodCrime.class);
                startActivity(i);
            }
        });
        btn_reportFiling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ReportFileActivity.class);
                startActivity(i);
            }
        });
        btn_safestRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SafestRouteActivity.class);
                startActivity(i);
            }
        });

        btn_trackCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DashboardActivity.this, TrackCrimeActivity.class);
                startActivity(i);
            }
        });

        btn_registerComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ComplaintActivity.class);
                startActivity(i);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
