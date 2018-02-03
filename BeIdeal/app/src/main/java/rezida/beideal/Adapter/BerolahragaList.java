package rezida.beideal.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rezida.beideal.Model.Asupan;
import rezida.beideal.Model.Berolahraga;
import rezida.beideal.R;

/**
 * Created by rezida on 17/01/2018.
 */

public class BerolahragaList extends ArrayAdapter<Berolahraga> {
    private Activity context;
    List<Berolahraga> berolahragas;

    public BerolahragaList(Activity context, List<Berolahraga> berolahragas) {
        super(context, R.layout.list_berolahraga, berolahragas);
        this.context = context;
        this.berolahragas = berolahragas;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_berolahraga, null, true);

        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.ketNamaBerolahraga);
        TextView textViewTgl= (TextView) listViewItem.findViewById(R.id.ketTglOlahraga);

        Berolahraga berolahraga = berolahragas.get(position);
        textViewNama.setText(berolahraga.getNama());
        textViewTgl.setText(berolahraga.getTgl());

        return listViewItem;
    }
}
