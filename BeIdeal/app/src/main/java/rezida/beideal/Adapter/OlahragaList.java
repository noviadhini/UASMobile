package rezida.beideal.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rezida.beideal.Model.Olahraga;
import rezida.beideal.R;

/**
 * Created by rezida on 16/01/2018.
 */

public class OlahragaList extends ArrayAdapter<Olahraga> {
    private Activity context;
    List<Olahraga> olahragas;

    public OlahragaList(Activity context, List<Olahraga> olahragas) {
        super(context, R.layout.list_tipsolahraga, olahragas);
        this.context = context;
        this.olahragas = olahragas;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_tipsolahraga, null, true);

        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.TnamaOlahraga);
        TextView textViewKalori = (TextView) listViewItem.findViewById(R.id.TkaloriOlahraga);

        Olahraga olahraga = olahragas.get(position);
        textViewNama.setText(olahraga.getUser_name());
        textViewKalori.setText(olahraga.getKalori_id() + " kalori, selama " + olahraga.getWaktu_id());

        return listViewItem;
    }
}
