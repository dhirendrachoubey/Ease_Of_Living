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
import in.nic.ease_of_living.models.State;


/**
 * Created by Neha Jain on 6/23/2017.
 */
public class StateAdapter extends ArrayAdapter<State> {

    // Your sent context
    private LayoutInflater inflater;
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<State> values;

    public StateAdapter(Context context, int textViewResourceId,
                        ArrayList<State> values) {
        super(context, textViewResourceId, values);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public State getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);

        label.setText(values.get(position).getState_name());
        return label;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.spinner_list_item, parent, false);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        tvTitle.setText(values.get(position).getState_name());

        return convertView;
    }
}