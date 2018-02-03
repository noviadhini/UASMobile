package rezida.beideal.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rezida.beideal.AsupanKalori;
import rezida.beideal.Model.Asupan;
import rezida.beideal.Model.Progress;
import rezida.beideal.R;

/**
 * Created by rezida on 17/01/2018.
 */

public class AsupanList extends ArrayAdapter<Asupan>
{
    private Activity context;
    List<Asupan> asupans;

    public AsupanList(Activity context, List<Asupan> asupans) {
        super(context, R.layout.list_asupankalori, asupans);
        this.context = context;
        this.asupans = asupans;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_asupankalori, null, true);

        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.ketNamaAsupan);
        TextView textViewTgl= (TextView) listViewItem.findViewById(R.id.ketTglAsupan);

        Asupan asupan = asupans.get(position);
        textViewNama.setText(asupan.getNama());
        textViewTgl.setText(asupan.getTgl());

        return listViewItem;
    }
}
