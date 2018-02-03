package rezida.beideal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import rezida.beideal.Model.User;

public class Home2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button menuChat, menuResto, menuSport, menuProfil, menuProgress, menuAsupan, menuOlahragaDilakukan, menuTipsOlahraga, menuMakananDihindari;

    DatabaseReference databaseUser;

    private String jk_user, tinggi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();

        final String username = i.getExtras().getString("username");

//        menuSport = (Button) findViewById(R.id.sport_button);
//        menuProfil = (Button) findViewById(R.id.btnProfil);
//        menuProgress = (Button) findViewById(R.id.btnProgress);
//        menuAsupan = (Button) findViewById(R.id.btnMakan);
//        menuOlahragaDilakukan = (Button) findViewById(R.id.btnMelakukanOlahraga);
//        menuTipsOlahraga = (Button) findViewById(R.id.btnTipsOlahraga);
//        menuMakananDihindari = (Button) findViewById(R.id.btnMakananDilarang);
//        menuResto = (Button) findViewById(R.id.resto_button);
//        menuChat = (Button) findViewById(R.id.menuChat);


        databaseUser = FirebaseDatabase.getInstance().getReference();
        Query query = databaseUser.child("user").orderByChild("username").equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        User user = issue.getValue(User.class);

                        jk_user = user.getJk();
                        tinggi = user.getTinggi();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        menuProfil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),Profil.class);
//                i.putExtra("username", username);
//                startActivity(i);
//            }
//        });
//
//        menuProgress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),ProgressDiet.class);
//                i.putExtra("username", username);
//                i.putExtra("tinggi", tinggi);
//                startActivity(i);
//            }
//        });
//
//        menuAsupan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),AsupanKalori.class);
//                i.putExtra("username", username);
//                startActivity(i);
//            }
//        });
//
//        menuOlahragaDilakukan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),MelakukanOlahraga.class);
//                i.putExtra("username", username);
//                startActivity(i);
//            }
//        });
//
//        menuTipsOlahraga.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),TipsOlahraga.class);
//                i.putExtra("username", username);
//                i.putExtra("jk", jk_user);
//                startActivity(i);
//            }
//        });
//
//        menuMakananDihindari.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),MakananDihindari.class);
//                i.putExtra("username", username);
//                i.putExtra("jk", jk_user);
//                startActivity(i);
//            }
//        });
//
//        menuSport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),Sport.class);
//                i.putExtra("username", username);
//                i.putExtra("jk", jk_user);
//                startActivity(i);
//            }
//        });
//
//        menuResto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),Resto.class);
//                i.putExtra("username", username);
//                i.putExtra("jk", jk_user);
//                startActivity(i);
//            }
//        });
//
//        menuChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),MainGambar.class);
//                i.putExtra("username", username);
//                i.putExtra("jk", jk_user);
//                startActivity(i);
//            }
//        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
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
            //return true;
            Intent a = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(a);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent i = getIntent();
        final String username = i.getExtras().getString("username");

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profil) {
            // Handle the camera action
            Intent a = new Intent(getApplicationContext(),Profil.class);
            a.putExtra("username", username);
            startActivity(a);
        } else if (id == R.id.progress) {
            Intent a = new Intent(getApplicationContext(),ProgressDiet.class);
            a.putExtra("username", username);
            a.putExtra("tinggi", tinggi);
            startActivity(a);

        } else if (id == R.id.memakan) {
            Intent a = new Intent(getApplicationContext(),AsupanKalori.class);
            a.putExtra("username", username);
            startActivity(a);

        } else if (id == R.id.memakandihindari) {
            Intent a = new Intent(getApplicationContext(),MakananDihindari.class);
            a.putExtra("username", username);
            a.putExtra("jk", jk_user);
            startActivity(a);

        } else if (id == R.id.olahraga) {
            Intent a = new Intent(getApplicationContext(),MelakukanOlahraga.class);
            a.putExtra("username", username);
            startActivity(a);

        } else if (id == R.id.tips) {
            Intent a = new Intent(getApplicationContext(),TipsOlahraga.class);
            a.putExtra("username", username);
            a.putExtra("jk", jk_user);
            startActivity(a);

        } else if (id == R.id.SportTerdekat) {
            Intent a = new Intent(getApplicationContext(),Sport.class);
            a.putExtra("username", username);
            a.putExtra("jk", jk_user);
            startActivity(a);

        } else if (id == R.id.resto) {
            Intent a = new Intent(getApplicationContext(),Resto.class);
            a.putExtra("username", username);
            a.putExtra("jk", jk_user);
            startActivity(a);

        } else if (id == R.id.testimoni) {
            Intent a = new Intent(getApplicationContext(),MainGambar.class);
            a.putExtra("username", username);
            a.putExtra("jk", jk_user);
            startActivity(a);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
