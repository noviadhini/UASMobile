package rezida.beideal.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rezida.beideal.Model.Progress;
import rezida.beideal.Model.User;
import rezida.beideal.R;

/**
 * Created by rezida on 15/01/2018.
 */

public class ProgressList extends ArrayAdapter<Progress> {
    private Activity context;
    List<Progress> progresss;

    public ProgressList(Activity context, List<Progress> progresss) {
        super(context, R.layout.list_progress, progresss);
        this.context = context;
        this.progresss = progresss;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_progress, null, true);

        TextView textViewTgl = (TextView) listViewItem.findViewById(R.id.ketTglProgress);
        TextView textViewBerat = (TextView) listViewItem.findViewById(R.id.ketBeratProgress);

        Progress progress = progresss.get(position);
        textViewTgl.setText(progress.getTgl());
        textViewBerat.setText(progress.getBerat());

        return listViewItem;
    }
}
