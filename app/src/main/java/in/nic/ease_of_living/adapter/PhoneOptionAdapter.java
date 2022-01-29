package in.nic.ease_of_living.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;


/**
 * Created by Neha Jain on 6/21/2017.
 */

public class PhoneOptionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    ArrayList<String> itemName;

    public PhoneOptionAdapter(Context context, ArrayList<String> name) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.itemName = name;
    }
    public int getCount() {
        return itemName.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.layout_options_phone_item, parent, false);
        TextView tvOptionsPhone = (TextView) convertView.findViewById(R.id.tvOptionsPhone);

        tvOptionsPhone.setText(itemName.get(position));
        return convertView;
    }


}