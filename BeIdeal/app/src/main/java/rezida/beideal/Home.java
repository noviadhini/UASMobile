package rezida.beideal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import rezida.beideal.Model.User;

public class Home extends AppCompatActivity {
    private Button  menuChat, menuResto, menuSport, menuProfil, menuProgress, menuAsupan, menuOlahragaDilakukan, menuTipsOlahraga, menuMakananDihindari;

    DatabaseReference databaseUser;

    private String jk_user, tinggi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();

        final String username = i.getExtras().getString("username");

        menuSport = (Button) findViewById(R.id.sport_button);
        menuProfil = (Button) findViewById(R.id.btnProfil);
        menuProgress = (Button) findViewById(R.id.btnProgress);
        menuAsupan = (Button) findViewById(R.id.btnMakan);
        menuOlahragaDilakukan = (Button) findViewById(R.id.btnMelakukanOlahraga);
        menuTipsOlahraga = (Button) findViewById(R.id.btnTipsOlahraga);
        menuMakananDihindari = (Button) findViewById(R.id.btnMakananDilarang);
        menuResto = (Button) findViewById(R.id.resto_button);
        menuChat = (Button) findViewById(R.id.menuChat);



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

        menuProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Profil.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        menuProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProgressDiet.class);
                i.putExtra("username", username);
                i.putExtra("tinggi", tinggi);
                startActivity(i);
            }
        });

        menuAsupan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AsupanKalori.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        menuOlahragaDilakukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MelakukanOlahraga.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        menuTipsOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TipsOlahraga.class);
                i.putExtra("username", username);
                i.putExtra("jk", jk_user);
                startActivity(i);
            }
        });

        menuMakananDihindari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MakananDihindari.class);
                i.putExtra("username", username);
                i.putExtra("jk", jk_user);
                startActivity(i);
            }
        });

        menuSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Sport.class);
                i.putExtra("username", username);
                i.putExtra("jk", jk_user);
                startActivity(i);
            }
        });

        menuResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Resto.class);
                i.putExtra("username", username);
                i.putExtra("jk", jk_user);
                startActivity(i);
            }
        });

        menuChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainGambar.class);
                i.putExtra("username", username);
                i.putExtra("jk", jk_user);
                startActivity(i);
            }
        });

    }
}
