package rezida.beideal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rezida.beideal.Adapter.ProfilList;
import rezida.beideal.Model.User;

public class Profil extends AppCompatActivity {
    private TextView cobaTampil, tgl, berat, tinggi, jk, goldar, bmi, ket;
    private Button btnEdit;

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent i = getIntent();

        final String username = i.getExtras().getString("username");

        databaseUser = FirebaseDatabase.getInstance().getReference();
        Query query = databaseUser.child("user").orderByChild("username").equalTo(username);

        cobaTampil = (TextView) findViewById(R.id.coba);
        tgl = (TextView) findViewById(R.id.tglProfil);
        berat = (TextView) findViewById(R.id.beratProfil);
        tinggi = (TextView) findViewById(R.id.tinggiProfil);
        jk = (TextView) findViewById(R.id.jkProfil);
        goldar = (TextView) findViewById(R.id.goldarProfil);
        bmi = (TextView) findViewById(R.id.bmiProfil);
        ket = (TextView) findViewById(R.id.ketProfil);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EditProfil.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        User user = issue.getValue(User.class);

                        cobaTampil.setText(user.getNama());
                        tgl.setText(user.getTglLahir() +" | "+ user.getBerat() +" | "+ user.getTinggi());
                        berat.setText(user.getBerat());
                        tinggi.setText(user.getTinggi());
                        jk.setText(user.getJk() +" | "+ user.getGoldar());
                        goldar.setText(user.getGoldar());

                        int a = Integer.parseInt(user.getBerat());
                        double b = Double.parseDouble(user.getTinggi());
                        double c = b/100;

                        double d = a / (c*c);

                        String e = String.valueOf(d);

                        bmi.setText(e);

                        if(d<17.0)
                        {
                            ket.setText("Kurus, Kekurangan berat badan berat");
                        }
                        else if (d>=17.0 & d<=18.4)
                        {
                            ket.setText("Kurus, Kekurangan berat badan ringan");
                        }
                        else if (d>=18.5 & d<=25.0)
                        {
                            ket.setText("Normal");
                        }
                        else if (d>=25.1 & d<=27.0)
                        {
                            ket.setText("Gemuk, Kelebihan berat badan tingkat ringan");
                        }
                        else if (d>=27.0)
                        {
                            ket.setText("Gemuk, Kelebihan berat badan tingkat berat");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
