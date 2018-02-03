package rezida.beideal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rezida.beideal.Adapter.DilarangList;
import rezida.beideal.Adapter.OlahragaList;
import rezida.beideal.Model.Dilarang;
import rezida.beideal.Model.Olahraga;

public class MakananDihindari extends AppCompatActivity {
    private ListView listMakanan;

    DatabaseReference databaseTracks;

    List<Dilarang> dilarangs;

    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan_dihindari);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listMakanan = (ListView) findViewById(R.id.listMakananDilarang);

        final Intent i = getIntent();

        dilarangs = new ArrayList<>();

        final String username = i.getExtras().getString("username");
        final String jk = i.getExtras().getString("jk");

        databaseTracks = FirebaseDatabase.getInstance().getReference();
        query = databaseTracks.child("dilarang").orderByChild("jk_id").equalTo(jk);
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

    @Override
    protected void onStart() {
        super.onStart();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Dilarang dilarang = issue.getValue(Dilarang.class);
                        dilarangs.add(dilarang);
                    }

                    DilarangList dilarangListAdapter = new DilarangList(MakananDihindari.this, dilarangs);
                    listMakanan.setAdapter(dilarangListAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
