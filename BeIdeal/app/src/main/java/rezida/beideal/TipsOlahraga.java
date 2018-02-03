package rezida.beideal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

import rezida.beideal.Adapter.OlahragaList;
import rezida.beideal.Adapter.ProgressList;
import rezida.beideal.Model.Olahraga;
import rezida.beideal.Model.Progress;
import rezida.beideal.Model.User;

public class TipsOlahraga extends AppCompatActivity {
    private TextView coba;
    private ListView listOlahraga;

    DatabaseReference databaseTracks;

    List<Olahraga> olahragas;

    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_olahraga);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listOlahraga = (ListView) findViewById(R.id.listTipsOlahraga);
//        coba = (TextView) findViewById(R.id.textView14);

        final Intent i = getIntent();

        olahragas = new ArrayList<>();

        final String username = i.getExtras().getString("username");
        final String jk = i.getExtras().getString("jk");

        databaseTracks = FirebaseDatabase.getInstance().getReference();
        query = databaseTracks.child("olahraga").orderByChild("jk_id").equalTo(jk);
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
                        Olahraga olahraga = issue.getValue(Olahraga.class);
                        olahragas.add(olahraga);
                    }

                    OlahragaList olahragaListAdapter = new OlahragaList(TipsOlahraga.this, olahragas);
                    listOlahraga.setAdapter(olahragaListAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
