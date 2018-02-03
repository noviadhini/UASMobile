package rezida.beideal;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rezida.beideal.Adapter.ProgressList;
import rezida.beideal.Model.Progress;
import rezida.beideal.Model.User;

public class ProgressDiet extends AppCompatActivity {
    private EditText txtBeratSekarang;
    private Button btnTambah, btnGraf;
    private ListView listProgress;

    DatabaseReference databaseTracks, databaseUser;

    List<Progress> progresses;

    private String username, tinggi, a, b, d, e, f, g, h, i, j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_diet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtBeratSekarang = (EditText) findViewById(R.id.txtBeratSekarang);
        btnTambah = (Button) findViewById(R.id.btnTambahProgress);
        btnGraf = (Button) findViewById(R.id.btnGrafik);
        listProgress = (ListView) findViewById(R.id.listProgress);

        final Intent intent = getIntent();

        progresses = new ArrayList<>();

        username = intent.getExtras().getString("username");
        tinggi = intent.getExtras().getString("tinggi");

        databaseTracks = FirebaseDatabase.getInstance().getReference("progress").child(username);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveProgress();
            }
        });

        btnGraf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Grafik.class);
                i.putExtra("username", username);
                i.putExtra("tinggi", tinggi);
                startActivity(i);
            }
        });

        listProgress.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Progress progress = progresses.get(i);

                showDeleteDialog(progress.getProgressId(), progress.getTgl(), progress.getBerat());
                return false;
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

    private void showDeleteDialog(final String progressId, String tgl, String berat)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final  View dialogView = inflater.inflate(R.layout.delete_dialog, null);

        dialogBuilder.setView(dialogView);

        final TextView txtberat = (TextView) dialogView.findViewById(R.id.dialog_dua);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btn_delete);

        txtberat.setText(berat);

        dialogBuilder.setTitle("Tanggal: " + tgl);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProgress(progressId);
            }
        });
    }

    private void deleteProgress(String progressId) {
        DatabaseReference drProgress = FirebaseDatabase.getInstance().getReference("progress").child(username).child(progressId);
        drProgress.removeValue();

        Toast.makeText(this, "Progress Diet Dihapus", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progresses.clear();

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                {
                    Progress progress = trackSnapshot.getValue(Progress.class);
                    progresses.add(progress);
                }

                ProgressList progressListAdapter = new ProgressList(ProgressDiet.this, progresses);
                listProgress.setAdapter(progressListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SaveProgress()
    {
        final String berat = txtBeratSekarang.getText().toString().trim();

        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);

        String tgl = year + "-" + month + "-" + day;

        String id = databaseTracks.push().getKey();

        Progress progress = new Progress(id, berat, tgl);

        databaseTracks.child(id).setValue(progress);

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

                        a = user.getUserId();
                        b = user.getNama();
                        d = user.getTglLahir();
                        e = user.getBerat();
                        f = tinggi;
                        g = user.getJk();
                        h = user.getGoldar();
                        i = username;
                        j = user.getPassword();
                    }

                    updateUser(a, b, d, berat, f, g, h, i, j);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, "Progress Diet Berhasil Ditambahkan", Toast.LENGTH_LONG).show();
    }

    private boolean updateUser(String id, String nama, String tgl, String berat, String tinggi, String jk, String goldar, String username, String password)
    {
        DatabaseReference databaseUpdate = FirebaseDatabase.getInstance().getReference("user").child(id);

        User user = new User(id, nama, tgl, berat, tinggi, jk, goldar, username, password);

        databaseUpdate.setValue(user);

        return true;
    }
}
