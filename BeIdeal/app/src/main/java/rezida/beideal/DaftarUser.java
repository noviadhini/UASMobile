package rezida.beideal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Calendar;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rezida.beideal.Model.Progress;
import rezida.beideal.Model.User;

public class DaftarUser extends AppCompatActivity implements View.OnClickListener, OnCheckedChangeListener {
    private Button btntanggal, buttonAdd;
    private EditText txttanggal, txtnama, txtberat, txttinggi, txtusername, txtpassword;
    private Spinner goldarSpinner;
    private RadioButton rb1,rb2;
    private RadioGroup rg;

    private int mYear, mMonth, mDay;
    private String gender;

    DatabaseReference databaseUser, databaseProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_user);

        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        btntanggal = (Button) findViewById(R.id.bTanggal);
        buttonAdd = (Button) findViewById(R.id.btnRegister);
        txttanggal = (EditText) findViewById(R.id.inputTanggalLahir);
        txtnama = (EditText) findViewById(R.id.inputNama);
        txtberat = (EditText) findViewById(R.id.inputBeratAwal);
        txttinggi = (EditText) findViewById(R.id.inputTinggi);
        txtusername = (EditText) findViewById(R.id.inputUsername);
        txtpassword = (EditText) findViewById(R.id.inputPassword);
        goldarSpinner = (Spinner) findViewById(R.id.spinner);
        rg = (RadioGroup)findViewById(R.id.rgJK);
        rb1 = (RadioButton)findViewById(R.id.radioButton2);
        rb2 = (RadioButton)findViewById(R.id.radioButton3);

        btntanggal.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
            }
        });
    }

    private void AddUser()
    {
        final String nama = txtnama.getText().toString().trim();
        final String tgl = txttanggal.getText().toString().trim();
        final String berat = txtberat.getText().toString().trim();
        final String tinggi = txttinggi.getText().toString().trim();
        final String username = txtusername.getText().toString().trim();
        final String password = txtpassword.getText().toString().trim();
        final String goldar = goldarSpinner.getSelectedItem().toString();
        final String jk = gender;

        String id = databaseUser.push().getKey();

        User user = new User(id, nama, tgl, berat, tinggi, jk, goldar, username, password);

        databaseUser.child(id).setValue(user);

        databaseProgress = FirebaseDatabase.getInstance().getReference("progress").child(username);

        String id_progress = databaseProgress.push().getKey();

        Progress progress = new Progress(id_progress, berat, tgl);

        databaseProgress.child(id).setValue(progress);

        Toast.makeText(this, "Daftar Berhasil", Toast.LENGTH_LONG).show();

        Intent i = new Intent(getApplicationContext(), Home2.class);
        i.putExtra("username", username);
        startActivity(i);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.radioButton2)
        {
//            Toast.makeText(this, "Anda Membeli Baso Tahu", Toast.LENGTH_SHORT).show();
            gender = "Laki-Laki";

        }
        if(checkedId==R.id.radioButton3)
        {
//            Toast.makeText(this, "Anda Membeli Mie Ayam", Toast.LENGTH_SHORT).show();
            gender = "Perempuan";
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
