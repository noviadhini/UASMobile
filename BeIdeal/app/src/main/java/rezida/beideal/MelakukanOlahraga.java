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
import rezida.beideal.Adapter.BerolahragaList;
import rezida.beideal.Model.Asupan;
import rezida.beideal.Model.Berolahraga;
import rezida.beideal.Model.Makanan;
import rezida.beideal.Model.Olahraga;

public class MelakukanOlahraga extends AppCompatActivity {
    private Spinner listOlahragaPilihan;
    private EditText txtJumlah;
    private Button btnTambah;
    private ListView listOlahraga;
    private TextView totalK;

    DatabaseReference databaseTracks, databaseBerolahraga, databaseKalori, databaseTotalK;

    ArrayAdapter<String> adapter2;
    List<Berolahraga> berolahragas;

    private String username;
    private int kalori, t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melakukan_olahraga);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listOlahragaPilihan = (Spinner) findViewById(R.id.listOlahragaInput);
        txtJumlah = (EditText) findViewById(R.id.txtJumlahOlahraga);
        btnTambah = (Button) findViewById(R.id.btnTambahOlahraga);
        listOlahraga = (ListView) findViewById(R.id.listolahraga);
        totalK = (TextView) findViewById(R.id.totalKalori);

        final Intent i = getIntent();

        username = i.getExtras().getString("username");

        berolahragas = new ArrayList<>();

        databaseTracks = FirebaseDatabase.getInstance().getReference("olahraga");
        databaseBerolahraga = FirebaseDatabase.getInstance().getReference("berolahraga").child(username);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveBerolahraga();
            }
        });

        listOlahraga.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Berolahraga berolahraga = berolahragas.get(i);

                showDeleteDialog(berolahraga.getBerolahragaId(), berolahraga.getTgl(), berolahraga.getNama(), berolahraga.getJumlah());
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

    private void showDeleteDialog(final String berolahragaId, String tgl, String nama, String jumlah)
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
                deleteBerolahraga(berolahragaId);
            }
        });
    }

    private void deleteBerolahraga(String berolahragaId) {
        DatabaseReference drProgress = FirebaseDatabase.getInstance().getReference("berolahraga").child(username).child(berolahragaId);
        drProgress.removeValue();

        Toast.makeText(this, "Aktivitas Olahraga Dihapus", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final List<String> list2;
        listOlahragaPilihan.setPrompt("Olahraga");

        list2 = new ArrayList<String>();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list2.clear();

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                {
                    Olahraga olahraga = trackSnapshot.getValue(Olahraga.class);
                    list2.add(olahraga.getUser_name());
                }

                adapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, list2);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listOlahragaPilihan.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseBerolahraga.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                berolahragas.clear();

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                {
                    Berolahraga berolahraga = trackSnapshot.getValue(Berolahraga.class);
                    berolahragas.add(berolahraga);
                }

                BerolahragaList berolahragaListAdapter = new BerolahragaList(MelakukanOlahraga.this, berolahragas);
                listOlahraga.setAdapter(berolahragaListAdapter);
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
        Query query = databaseTotalK.child("berolahraga").child(username).orderByChild("tgl").equalTo(tgl);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                    {
                        Berolahraga berolahraga = trackSnapshot.getValue(Berolahraga.class);
                        t += Integer.parseInt(berolahraga.getTotal());
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

    private void SaveBerolahraga()
    {
        String nama = listOlahragaPilihan.getSelectedItem().toString();
        databaseKalori = FirebaseDatabase.getInstance().getReference();
        Query query = databaseKalori.child("olahraga").orderByChild("user_name").equalTo(nama);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = databaseBerolahraga.push().getKey();

                String nama = listOlahragaPilihan.getSelectedItem().toString();
                String jumlah = txtJumlah.getText().toString().trim();

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
                        Olahraga olahraga = issue.getValue(Olahraga.class);

                        kalori = Integer.parseInt(olahraga.getKalori_id());
                    }

                    int total = Integer.parseInt(jumlah)*kalori;
                    String totals = String.valueOf(total);

                    Berolahraga berolahraga = new Berolahraga(id, nama, jumlah, totals, tgl);

                    databaseBerolahraga.child(id).setValue(berolahraga);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(this, "Riwayat olahraga berhasil ditambahkan", Toast.LENGTH_LONG).show();
    }
}
