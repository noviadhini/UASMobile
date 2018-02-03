package rezida.beideal.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rezida.beideal.Model.Dilarang;
import rezida.beideal.Model.Olahraga;
import rezida.beideal.R;

/**
 * Created by rezida on 16/01/2018.
 */

public class DilarangList extends ArrayAdapter<Dilarang> {
    private Activity context;
    List<Dilarang> dilarangs;

    public DilarangList(Activity context, List<Dilarang> dilarangs) {
        super(context, R.layout.list_makanandilarang, dilarangs);
        this.context = context;
        this.dilarangs = dilarangs;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_makanandilarang, null, true);

        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.makananDilarang);

        Dilarang dilarang = dilarangs.get(position);
        textViewNama.setText(dilarang.getUser_name());

        return listViewItem;
    }
}
