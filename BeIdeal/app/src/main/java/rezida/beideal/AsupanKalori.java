package rezida.beideal;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rezida.beideal.Adapter.AsupanList;
import rezida.beideal.Adapter.ProgressList;
import rezida.beideal.Model.Asupan;
import rezida.beideal.Model.Dilarang;
import rezida.beideal.Model.Makanan;
import rezida.beideal.Model.Olahraga;
import rezida.beideal.Model.Progress;

public class AsupanKalori extends AppCompatActivity {
    private Spinner listMakanan;
    private EditText txtJumlah;
    private Button btnTambah;
    private ListView listAsupan;
    private TextView totalK;

    DatabaseReference databaseTracks, databaseAsupan, databaseKalori, databaseTotalK;

    ArrayAdapter<String> adapter2;
    List<Asupan> asupans;

    private String username;
    private int kalori, t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asupan_kalori);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listMakanan = (Spinner) findViewById(R.id.listMakananInput);
        txtJumlah = (EditText) findViewById(R.id.txtJumlahMakan);
        btnTambah = (Button) findViewById(R.id.btnTambahMakan);
        listAsupan = (ListView) findViewById(R.id.listasupan);
        totalK = (TextView) findViewById(R.id.totalKalori);

        final Intent i = getIntent();

        username = i.getExtras().getString("username");

        asupans = new ArrayList<>();

        databaseTracks = FirebaseDatabase.getInstance().getReference("makanan");
        databaseAsupan = FirebaseDatabase.getInstance().getReference("asupan").child(username);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAsupan();
            }
        });

        listAsupan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Asupan asupan = asupans.get(i);

                showDeleteDialog(asupan.getAsupanId(), asupan.getTgl(), asupan.getNama(), asupan.getJumlah());
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

    private void showDeleteDialog(final String asupanId, String tgl, String nama, String jumlah)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final  View dialogView = inflater.inflate(R.layout.delete_dialogaktivitas, null);

        dialogBuilder.setView(dialogView);

        final TextView txtnama = (TextView) dialogView.findViewById(R.id.dialog_satu);
        final TextView txtjumlah = (TextView) dialogView.findViewById(R.id.dialog_tiga);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btn_delete);

        txtnama.setText(nama);
        txtjumlah.setText(jumlah);

        dialogBuilder.setTitle("Tanggal: " + tgl);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAsupan(asupanId);
            }
        });
    }

    private void deleteAsupan(String asupanId) {
        DatabaseReference drProgress = FirebaseDatabase.getInstance().getReference("asupan").child(username).child(asupanId);
        drProgress.removeValue();

        Toast.makeText(this, "Asupan Kalori Dihapus", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final List<String> list2;
        listMakanan.setPrompt("Makanan");

        list2 = new ArrayList<String>();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list2.clear();

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                {
                    Makanan makanan = trackSnapshot.getValue(Makanan.class);
                    list2.add(makanan.getUser_name());
                }

                adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, list2);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listMakanan.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseAsupan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                asupans.clear();

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                {
                    Asupan asupan = trackSnapshot.getValue(Asupan.class);
                    asupans.add(asupan);
                }

                AsupanList asupanListAdapter = new AsupanList(AsupanKalori.this, asupans);
                listAsupan.setAdapter(asupanListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);

        String tgl = year + "-" + month + "-" + day;

        databaseTotalK = FirebaseDatabase.getInstance().getReference();
        Query query = databaseTotalK.child("asupan").child(username).orderByChild("tgl").equalTo(tgl);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                    {
                        Asupan asupan = trackSnapshot.getValue(Asupan.class);
                        t += Integer.parseInt(asupan.getTotal());
                    }
                }

                String tk = String.valueOf(t);

                totalK.setText(tk);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SaveAsupan()
    {
        String nama = listMakanan.getSelectedItem().toString();
        databaseKalori = FirebaseDatabase.getInstance().getReference();
        Query query = databaseKalori.child("makanan").orderByChild("user_name").equalTo(nama);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = databaseAsupan.push().getKey();

                String nama = listMakanan.getSelectedItem().toString();
                final String jumlah = txtJumlah.getText().toString().trim();

                final Calendar c = Calendar.getInstance();
                int year, month, day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
                day = c.get(Calendar.DATE);

                String tgl = year + "-" + month + "-" + day;

                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Makanan makanan = issue.getValue(Makanan.class);

                        kalori = Integer.parseInt(makanan.getKalori_id());
                    }

                    int total = Integer.parseInt(jumlah)*kalori;
                    String totals = String.valueOf(total);

                    Asupan asupan = new Asupan(id, nama, jumlah, totals, tgl);

                    databaseAsupan.child(id).setValue(asupan);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, "Asupan kalori berhasil ditambahkan", Toast.LENGTH_LONG).show();
    }
}
