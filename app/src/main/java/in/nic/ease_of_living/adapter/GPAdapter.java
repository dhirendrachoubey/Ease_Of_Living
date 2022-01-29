package in.nic.ease_of_living.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.GP;

/**
 * Created by Neha Jain on 7/21/2017.
 */
public class GPAdapter extends ArrayAdapter<GP> {

    // Your sent context
    private LayoutInflater inflater;
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<GP> values;

    public GPAdapter(Context context, int textViewResourceId,ArrayList<GP> values) {
        super(context, textViewResourceId, values);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public GP getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);

        label.setText(values.get(position).getGp_name());
        return label;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.spinner_list_item, parent, false);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        tvTitle.setText(values.get(position).getGp_name());
        return convertView;
    }

}
