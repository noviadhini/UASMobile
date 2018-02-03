package rezida.beideal.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rezida.beideal.Model.User;
import rezida.beideal.R;

/**
 * Created by rezida on 10/01/2018.
 */

public class ProfilList extends ArrayAdapter<User> {
    private Activity context;
    List<User> users;

    public ProfilList(Activity context, List<User> users) {
        super(context, R.layout.list_profil, users);
        this.context = context;
        this.users = users;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_profil, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.namaProfil);

        User user = users.get(position);
        textViewName.setText(user.getNama());

        return listViewItem;
    }
}
