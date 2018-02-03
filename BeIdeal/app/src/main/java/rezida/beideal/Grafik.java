package rezida.beideal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import rezida.beideal.Adapter.ProgressList;
import rezida.beideal.Model.Progress;
import rezida.beideal.Model.User;

public class Grafik extends AppCompatActivity {
    private BarChart chart;
    ArrayList<String> xAxis;
    ArrayList<IBarDataSet> dataSets;
    ArrayList<BarEntry> valueSet;

    DatabaseReference databaseTracks, databaseUser;

    private String username, tinggi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafik);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chart = (BarChart) findViewById(R.id.chart);
        xAxis = new ArrayList<>();
        dataSets = null;
        valueSet = new ArrayList<>();

        final Intent intent = getIntent();
        username = intent.getExtras().getString("username");
        tinggi = intent.getExtras().getString("tinggi");

        databaseTracks = FirebaseDatabase.getInstance().getReference("progress").child(username);
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

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                int ke = 0;
                int hitung = 0;
                double b = Double.parseDouble(tinggi) / 100;

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren())
                {
                    Progress progress = trackSnapshot.getValue(Progress.class);
                    int berat = Integer.parseInt(progress.getBerat());
                    String tgl = progress.getTgl();

                    double a = Double.parseDouble(progress.getBerat()) / (b*b);
                    int bmi = (int) a;
                    xAxis.add(tgl);
                    valueSet.add(new BarEntry(berat, hitung));
                    hitung++;
                }

                BarDataSet barDataSet = new BarDataSet(valueSet, "BMI");

                dataSets = new ArrayList<>();
                dataSets.add(barDataSet);

                YAxis yAxisRight = chart.getAxisRight();
                yAxisRight.setEnabled(false);

                BarData data = new BarData(xAxis, dataSets);
                chart.setExtraOffsets(0, 0, 0, 20);
                chart.setData(data);
                chart.animateXY(2000, 2000);
                chart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        Toast.makeText(this, "Tinggi " + tinggi, Toast.LENGTH_LONG).show();
    }
}
