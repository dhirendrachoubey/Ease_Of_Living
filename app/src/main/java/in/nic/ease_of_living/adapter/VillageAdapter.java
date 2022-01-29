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
import in.nic.ease_of_living.models.Village;


/**
 * Created by Neha Jain on 17/Aug/2017.
 */
public class VillageAdapter extends ArrayAdapter<Village> {

    // Your sent context
    private LayoutInflater inflater;
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<Village> values;

    public VillageAdapter(Context context, int textViewResourceId,
                          ArrayList<Village> values) {
        super(context, textViewResourceId, values);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.size();
    }

    public Village getItem(int position){
        return values.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public int getPosition(Village item) {

        int pos = 0;
        for(; pos< values.size(); pos++)
        {
            if(item.getVillage_code() == values.get(pos).getVillage_code())
                return pos;
        }
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);

        if(position == 0)
            label.setText(values.get(position).getVillage_name());
        else
            label.setText(values.get(position).getVillage_name() + " (" + values.get(position).getVillage_code() + ")");
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.spinner_list_item, parent, false);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        if(position == 0)
            tvTitle.setText(values.get(position).getVillage_name());
        else
            tvTitle.setText(values.get(position).getVillage_name() + " (" + values.get(position).getVillage_code() + ")");
        return convertView;
    }
}
