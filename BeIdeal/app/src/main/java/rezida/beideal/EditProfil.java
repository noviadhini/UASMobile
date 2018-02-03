package rezida.beideal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import rezida.beideal.Model.User;

public class EditProfil extends AppCompatActivity implements View.OnClickListener, OnCheckedChangeListener {
    private Button btntanggal, buttonAdd;
    private EditText txttanggal, txtnama, txtberat, txttinggi;
    private RadioButton rb1,rb2;
    private RadioGroup rg;
    private TextView dataGoldar, dataJk;

    private int mYear, mMonth, mDay;
    private String a, b, c;

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent i = getIntent();

        final String username = i.getExtras().getString("username");

        databaseUser = FirebaseDatabase.getInstance().getReference();
        Query query = databaseUser.child("user").orderByChild("username").equalTo(username);

        btntanggal = (Button) findViewById(R.id.bTanggal);
        buttonAdd = (Button) findViewById(R.id.btnRegisterEdit);
        txttanggal = (EditText) findViewById(R.id.inputTanggalLahir);
        txtnama = (EditText) findViewById(R.id.inputNama);
        txtberat = (EditText) findViewById(R.id.inputBeratAwal);
        txttinggi = (EditText) findViewById(R.id.inputTinggi);
        rg = (RadioGroup)findViewById(R.id.rgJK);
        rb1 = (RadioButton)findViewById(R.id.radioButton2);
        rb2 = (RadioButton)findViewById(R.id.radioButton3);
        dataGoldar = (TextView) findViewById(R.id.goldarSebelumnya);
        dataJk = (TextView) findViewById(R.id.ketJkEdit);

        btntanggal.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        User user = issue.getValue(User.class);

                        txtnama.setText(user.getNama());
                        txttanggal.setText(user.getTglLahir());
                        txtberat.setText(user.getBerat());
                        txttinggi.setText(user.getTinggi());
                        dataJk.setText(user.getJk());
                        dataGoldar.setText(user.getGoldar());

                        a = user.getUserId();
                        b = username;
                        c = user.getPassword();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = a;
                String nama = txtnama.getText().toString().trim();
                String tgl= txttanggal.getText().toString().trim();
                String berat = txtberat.getText().toString().trim();
                String tinggi = txttinggi.getText().toString().trim();
                String jk = dataJk.getText().toString().trim();
                String goldar = dataGoldar.getText().toString().trim();
                String username = b;
                String password = c;

                updateUser(id, nama, tgl, berat, tinggi, jk, goldar, username, password);
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

    private boolean updateUser(String id, String nama, String tgl, String berat, String tinggi, String jk, String goldar, String username, String password)
    {
        DatabaseReference databaseUpdate = FirebaseDatabase.getInstance().getReference("user").child(id);

        User user = new User(id, nama, tgl, berat, tinggi, jk, goldar, username, password);

        databaseUpdate.setValue(user);

        Toast.makeText(this, "Profil Berhasil Diedit", Toast.LENGTH_LONG).show();

        return true;
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.radioButton2)
        {
//            Toast.makeText(this, "Anda Membeli Baso Tahu", Toast.LENGTH_SHORT).show();
            dataJk.setText("Laki-Laki");

        }
        if(checkedId==R.id.radioButton3)
        {
//            Toast.makeText(this, "Anda Membeli Mie Ayam", Toast.LENGTH_SHORT).show();
            dataJk.setText("Perempuan");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bTanggal:
                // Get Current Date
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                txttanggal.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }
}
